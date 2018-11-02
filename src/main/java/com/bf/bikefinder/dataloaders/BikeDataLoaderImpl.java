package com.bf.bikefinder.dataloaders;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bf.bikefinder.model.Bike;
import com.bf.bikefinder.model.BikeComponent;
import com.bf.bikefinder.model.Maker;
import com.bf.bikefinder.repositories.BikeComponentRepository;
import com.bf.bikefinder.repositories.BikeRepository;
import com.bf.bikefinder.repositories.MakerRepository;
import org.springframework.transaction.annotation.Transactional;

import static com.bf.bikefinder.functions.ParseFunctions.setAttr;
import static com.bf.bikefinder.functions.ParseFunctions.setChildAttr;
import static com.bf.bikefinder.functions.ParseFunctions.setValue;
import static com.bf.bikefinder.functions.ParseFunctions.parseDouble;

@Component
public class BikeDataLoaderImpl implements BikeDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BikeDataLoaderImpl.class);

    @Autowired
    BikeRepository bikeRepository;
    @Autowired
    MakerRepository makerRepository;
    @Autowired
    BikeComponentRepository componentRepository;

    @Override
    @Scheduled(cron="0 0 0 * * *") // Ejecutamos la recoleccion de datos todos los dias a las 00:00
    public void collectData() {
        LOGGER.debug("collecting data...");
        loadBikesBH() ;
    }

    private  List<BikeComponent> loadDetails(String url_data) {
    	  List<BikeComponent> bikeComponents=new ArrayList<BikeComponent>();
		  try {

			Document doc = Jsoup.connect(url_data).get();
				Elements items = doc.select("ul.table_list > li");
				items.forEach(item->{
					BikeComponent component=componentRepository.findByNameAndType(item.select("span").get(1).text(),item.select("span").get(0).text());
					if(component==null) {

					component=new BikeComponent(item.select("span").get(1).text(),item.select("span").get(0).text());
					componentRepository.save(component);
					bikeComponents.add(component);
					}


					System.out.println( item.select("span").first().text()+"="+ item.select("span").get(1).text());
				});



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return bikeComponents;

	}

    private void loadBikesBH() {
		String url_en = "https://www.bhbikes.com/en_GB/bikes/";
		String url = "https://www.bhbikes.com/es_ES/bicicletas";


		try {
			Document doc = Jsoup.connect(url).get();
			Elements divs = doc.select("div.product");
			for (Element div : divs) {			

				Element link = div.select("a.image_container").first();
				print("modelo=%s,precio=%s, %s", div.attr("title"),div.attr("data-precio"),link.attr("abs:href"));	  
				String model=div.attr("title");
				if ( !model.equals("")){
					Bike bike=bikeRepository.findByName(model);
					if (bike==null) {
						bike=new Bike(model);
					}
					Maker maker=makerRepository.findByName("BH");
					if (maker==null){
						maker=new Maker("BH");
						makerRepository.save(maker);
					}
					bike.setMaker(maker);
					bike.setCategory(div.attr("data-seccion_web"));
					//				    bike.setModel(model);
					String priceStr=div.attr("data-precio").replace(".", ",");
					NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
					double priceDbl = nf.parse(priceStr).doubleValue();
					bike.setPrice(priceDbl);
					String url_details=link.attr("abs:href");
					List<BikeComponent> bikeComponents=loadDetails(url_details);
					bike.setComponentList(bikeComponents);
					Element image = link.select("img").first();
					if(image!=null) {
						bike.setUrlImage(image.attr("src"));
						System.out.println(image.attr("src"));
					}
					bike.setUrlDetails(url_details);
					bike.setYear(2018);
					bikeRepository.save(bike);
				}
			}
		} catch (ParseException | IOException e) {
			LOGGER.error("Unable to parse BH", e);
		}
	}

	@Override
    @Transactional
    public void loadBikesBHVavr(String makerName) {
	    LOGGER.debug("loadBikesBHVavr::init");

        Map<String, String> makers = HashMap.of("BH", "https://www.bhbikes.com/es_ES/bicicletas",
                                                "Orbea", "https://www.orbea.com/es-es/bicicletas/montana/cat");
//		final String URL_EN = "https://www.bhbikes.com/en_GB/bikes/";

        // Get webpage url
        final String url = makers.get(makerName).getOrElseThrow(RuntimeException::new);

        // Load all existing bikes for this maker
		List<Bike> existingBikes = bikeRepository.findByMakerNameIgnoreCase(makerName);
		Maker maker = existingBikes.stream().findFirst()
                .map(Bike::getMaker)
                .orElseGet(() -> new Maker(makerName));

		// Download raw data, mount entities from it, after all save the entities in the database
		Try.of(Jsoup.connect(url)::get)
		.map(doc -> doc.select("div.product"))
        .map(Elements::stream)
        .get()
		.filter(div -> !"".equals(div.attr("title")))
		.map(div -> {
			String model = div.attr("title");
			Bike newBike = existingBikes.stream().filter(bike -> model.equalsIgnoreCase(bike.getName()))
                    .findFirst()
                    .orElseGet(() -> new Bike(model));
			return Tuple.of(newBike, div);
		})
		.peek(setAttr(Bike::setCategory, "data-seccion_web"))
		.peek(setValue(Bike::setMaker, maker))
		.peek(setChildAttr(Bike::setUrlDetails, "a.image_container", "abs:href"))
        .peek(setChildAttr(Bike::setUrlImage, "a.image_container > img", "src"))
        .peek(setValue(Bike::setYear, 2018))
		.peek(tuple -> {
			String priceStr = tuple._2.attr("data-precio").replace(".", ",");
			tuple._1.setPrice(parseDouble.apply(priceStr));
		})
        .map(Tuple2::_1)
        .peek(bike -> LOGGER.debug("Saving element: " + bike))
		.forEach(bikeRepository::save);

		// TODOs: pararell?
        //      remove old ones not in webpage
        //      Exceptions (ie: not found)
	}

	private static void print(String msg, Object... args) {
		LOGGER.debug(String.format(msg, args));
	}

}
