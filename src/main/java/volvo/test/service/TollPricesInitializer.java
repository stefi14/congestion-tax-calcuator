package volvo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import volvo.test.model.TollPrices;
import volvo.test.repository.TollRepository;

import java.util.Arrays;

@Component
public class TollPricesInitializer implements CommandLineRunner {
    private final TollRepository tollPricesRepository;

    @Autowired
    public TollPricesInitializer(TollRepository tollPricesRepository) {
        this.tollPricesRepository = tollPricesRepository;
    }

    // This is only for this testcase as it was not
    @Override
    public void run(String... args) throws Exception {
        // Add toll prices for City1
        TollPrices tollPrice1Car = new TollPrices(1L, 8, "lowest", 1L, "City1");
        TollPrices tollPrice1Truck = new TollPrices(2L, 13, "medium", 1L, "City1");
        TollPrices tollPrice1Motorbike = new TollPrices(3L, 18, "highest", 1L, "City1");

        // Add toll prices for City2
        TollPrices tollPrice2Car = new TollPrices(4L, 12, "lowest", 2L, "City2");
        TollPrices tollPrice2Truck = new TollPrices(5L, 16, "medium", 2L, "City2");
        TollPrices tollPrice2Motorbike = new TollPrices(6L, 21, "lowest", 2L, "City2");

        // Add toll prices for City3
        TollPrices tollPrice3Car = new TollPrices(7L, 5, "lowest", 3L, "City3");
        TollPrices tollPrice3Truck = new TollPrices(8L, 10, "medium", 3L, "City3");
        TollPrices tollPrice3Motorbike = new TollPrices(9L, 15, "highest", 3L, "City3");

        // Save toll prices to the database
        tollPricesRepository.saveAll(Arrays.asList(
                tollPrice1Car, tollPrice1Truck, tollPrice1Motorbike,
                tollPrice2Car, tollPrice2Truck, tollPrice2Motorbike,
                tollPrice3Car, tollPrice3Truck, tollPrice3Motorbike
        ));

        System.out.println("Toll prices added successfully!");
    }
}
