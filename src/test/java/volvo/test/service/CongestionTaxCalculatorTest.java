package volvo.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import volvo.test.model.Car;
import volvo.test.model.Motorbike;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {CongestionTaxCalculator.class})
@TestPropertySource(properties = {"tax.rate.lowest = 8", "tax.rate.medium = 13", "ax.rate.highest = 18"})
class CongestionTaxCalculatorTest {

    private final CongestionTaxCalculator congestionTaxCalculator = new CongestionTaxCalculator();

   @BeforeAll
    public static void setup() {
       System.setProperty("tax.rate.lowest", "8");
       System.setProperty("tax.rate.medium", "13");
       System.setProperty("tax.rate.highest", "18");
    }

    @Value("${tax.rate.lowest}")
    private int lowest;

    @Test
    void testProperty() {


        assertEquals(8, lowest);

    }

    @Test
    void getTax_Free_Weekends() {

        LocalDateTime date = LocalDateTime.of(2013, Month.OCTOBER, 6, 12, 34);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.OCTOBER, 6, 16, 02);
        Car car = new Car();

       int cal =  congestionTaxCalculator.getTax(car, List.of(date, dateTime2));
        assertEquals(0, cal);

    }

    @Test
    void getTax_On_Weekday_multiple_times() {

        Car car = new Car();
        int cal =  congestionTaxCalculator.getTax(car, car.getDates());
        assertEquals(81, cal);

    }

    @Test
    void getTax_On_Weekday_multiple_times_Within_One_Hour() {
        LocalDateTime date = LocalDateTime.of(2013, Month.OCTOBER, 5, 6, 15);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.OCTOBER, 5, 6, 49);
        Car car = new Car();
        int cal =  congestionTaxCalculator.getTax(car, List.of(date, dateTime2));
        assertEquals(13, cal);

    }

    @Test
    void getTax_Free_Vehicle() {

        LocalDateTime date = LocalDateTime.of(2013, Month.OCTOBER, 3, 12, 34);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.OCTOBER, 3, 16, 02);
        Motorbike motorbike = new Motorbike();

        int cal =  congestionTaxCalculator.getTax(motorbike, List.of(date, dateTime2));
        assertEquals(0, cal);

    }

    @Test
    void getTollFee() {
        LocalDateTime date = LocalDateTime.of(2013, Month.FEBRUARY, 7, 6, 23, 27);
        Car car = new Car();
        int cal =  congestionTaxCalculator.getTollFee(date, car);
        assertEquals(8, cal);
    }

}