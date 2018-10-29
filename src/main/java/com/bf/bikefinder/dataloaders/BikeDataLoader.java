package com.bf.bikefinder.dataloaders;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bf.bikefinder.model.Bike;
import com.bf.bikefinder.model.Maker;
import com.bf.bikefinder.repositories.BikeRepository;
import com.bf.bikefinder.repositories.MakerRepository;
@Component
public class BikeDataLoader {
	//public class BikeDataLoader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BikeDataLoader.class);
   

    @Autowired
    BikeRepository bikeRepository;
    @Autowired
    MakerRepository makerRepository;
    
    public BikeDataLoader() {
       
    }

    @Scheduled(cron="0 0 0 * * *") // Ejecutamos la recoleccion de datos todos los dias a las 00:00
    public void collectData() {
        LOGGER.debug("collecting data...");
        loadBikesBH() ;
    }
    
//    @Override
//    public void run(String... strings) throws Exception {
////        this.repository.save(new Bike("T10", 1000f));
////        this.repository.save(new Bike("T11", 1000f));
//
//    	loadBikesBH() ;
//    }
    
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
					bike.setMakerId(maker.getId());
					bike.setCategory(div.attr("data-seccion_web"));
					//				    bike.setModel(model);
					String priceStr=div.attr("data-precio").replace(".", ",");
					NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
					double priceDbl = nf.parse(priceStr).doubleValue();
					bike.setPrice(priceDbl);
					String url_details=link.attr("abs:href");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

}
