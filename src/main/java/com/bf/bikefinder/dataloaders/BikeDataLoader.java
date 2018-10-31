package com.bf.bikefinder.dataloaders;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bf.bikefinder.model.Bike;
import com.bf.bikefinder.model.BikeComponent;
import com.bf.bikefinder.model.Maker;
import com.bf.bikefinder.repositories.BikeComponentRepository;
import com.bf.bikefinder.repositories.BikeRepository;
import com.bf.bikefinder.repositories.MakerRepository;
@Component
//public class BikeDataLoader {
	public class BikeDataLoader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BikeDataLoader.class);
   

    @Autowired
    BikeRepository bikeRepository;
    @Autowired
    MakerRepository makerRepository;
    @Autowired
    BikeComponentRepository componentRepository;

    public BikeDataLoader() {
       
    }

//    @Scheduled(cron="0 0 0 * * *") // Ejecutamos la recoleccion de datos todos los dias a las 00:00
//    public void collectData() {
//        LOGGER.debug("collecting data...");
//        loadBikesBH() ;
//    }
    
    @Override
    public void run(String... strings) throws Exception {
//        this.repository.save(new Bike("T10", 1000f));
//        this.repository.save(new Bike("T11", 1000f));

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

	@SuppressWarnings("unchecked")
	private static <T> Consumer<Tuple2<Bike, Element>> setChildAttr(BiConsumer<Bike, T> setBikeValue, String childSelector, String attrSelector) {
		return tuple -> setBikeValue.accept(tuple._1, (T) tuple._2.select(childSelector).attr(attrSelector));
	}

	@SuppressWarnings("unchecked")
	private static <T> Consumer<Tuple2<Bike, Element>> setAttr(BiConsumer<Bike, T> setBikeValue, String attrSelector) {
    	return tuple -> setBikeValue.accept(tuple._1, (T) tuple._2.attr(attrSelector));
	}

	@SuppressWarnings("unchecked")
	private static <T> Consumer<Tuple2<Bike, Element>> setValue(BiConsumer<Bike, T> setBikeValue, T value) {
		return tuple -> setBikeValue.accept(tuple._1, value);
	}

	private static Double parseDouble(String num) throws ParseException {
		NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
		return nf.parse(num).doubleValue();
	}

	private void loadBikesBHVavr() {
		String url_en = "https://www.bhbikes.com/en_GB/bikes/";
		String url = "https://www.bhbikes.com/es_ES/bicicletas";

		Maker maker = Optional.ofNullable(makerRepository.findByName("BH"))
			.orElse(makerRepository.save(new Maker("BH")));


		Try.of(() -> Jsoup.connect(url).get())
		.getOrElse(() -> Document.createShell(""))
		.select("div.product")
		.stream()
		.filter(div -> !"".equals(div.attr("title"))) // if(!model.equals(""))
		.map(div -> {
			String model = div.attr("title");
			Bike bike = Optional.ofNullable(bikeRepository.findByName(model))
					.orElseGet(() -> new Bike(model));
			return Tuple.of(bike, div);
		})
		.peek(setAttr(Bike::setCategory, "data-seccion_web"))
		.peek(setValue(Bike::setMaker, maker))
		.peek(setChildAttr(Bike::setUrlDetails, "a.image_container", "abs:href"))
		.peek(tuple -> {
			String priceStr = tuple._2.attr("data-precio").replace(".", ",");
			Try.of(() -> parseDouble(priceStr))
					.onSuccess(tuple._1::setPrice);
		})
//		.peek(tuple -> {
//			//div.image > a > img
//			Element link = tuple._2.select("a.image_container").first();
//
//			Optional.of(link.select("img").first())
//				.ifPresent(element -> tuple._1.setUrlImage(element.attr("src")));
//		})
		.peek(setChildAttr(Bike::setUrlImage, "a.image_container", "src"))
		.peek(setValue(Bike::setYear, 2018))
		.forEach(tuple -> bikeRepository.save(tuple._1));
	}

	private static void print(String msg, Object... args) {
		LOGGER.debug(String.format(msg, args));
	}

}
