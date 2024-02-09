package volvo.test.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import volvo.test.model.Car;
import volvo.test.model.Motorbike;
import volvo.test.model.TollPrices;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CongestionTaxCalculatorService.class})
class CongestionTaxCalculatorTest {

    @Mock
    EntityManager entityManager;
    @Mock
    private TollService tollService;
    private CongestionTaxCalculatorService congestionTaxCalculatorService;
    @Mock
    private TypedQuery<TollPrices> query; // Mock TypedQuery

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test method

        tollService = new TollService(entityManager);
        congestionTaxCalculatorService = new CongestionTaxCalculatorService(tollService);

    }

    @Test
    void getTax_Free_Weekends() {

        LocalDateTime date = LocalDateTime.of(2013, Month.OCTOBER, 6, 12, 34);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.OCTOBER, 6, 16, 02);
        Car car = new Car();
        car.setDates( List.of(date, dateTime2));
        TollPrices tollPrice1 = new TollPrices(1L, 8, "lowest", 1L, "City1");
        TollPrices tollPrice2= new TollPrices(2L, 13, "medium", 1L, "City1");
        TollPrices tollPrice3 = new TollPrices(3L, 18, "highest", 1L, "City1");

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query); // Ensure setParameter returns non-null
        when(query.getResultList()).thenReturn(List.of(tollPrice1, tollPrice2, tollPrice3));


        int cal =  congestionTaxCalculatorService.getTax(car);
        assertEquals(0, cal);

    }

    @Test
    void getTax_On_Weekday_multiple_times() {

        Car car = new Car();
        LocalDateTime dateTime = LocalDateTime.of(2013, Month.FEBRUARY, 7, 6, 23, 27);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.FEBRUARY, 7, 15, 27, 00);
        LocalDateTime dateTime3 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 6, 27, 00);
        LocalDateTime dateTime4 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 14, 36, 00);
        LocalDateTime dateTime5 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 15, 29, 00);
        LocalDateTime dateTime6 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 15, 47, 00);
        LocalDateTime dateTime7 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 16, 27, 00);
        LocalDateTime dateTime8 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 17, 27, 00);
        LocalDateTime dateTime9 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 18, 27, 00);
        LocalDateTime dateTime10 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 18, 50, 00);
       car.setDates(List.of(dateTime, dateTime2, dateTime3, dateTime4, dateTime7, dateTime6, dateTime5, dateTime8, dateTime9, dateTime10));

        TollPrices tollPrice1 = new TollPrices(1L, 8, "lowest", 1L, "City1");
        TollPrices tollPrice2= new TollPrices(2L, 13, "medium", 1L, "City1");
        TollPrices tollPrice3 = new TollPrices(3L, 18, "highest", 1L, "City1");

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query); // Ensure setParameter returns non-null
        when(query.getResultList()).thenReturn(List.of(tollPrice1, tollPrice2, tollPrice3));

        int cal =  congestionTaxCalculatorService.getTax(car);
        assertEquals(81, cal);

    }

    @Test
    void getTax_On_Weekday_multiple_times_Within_One_Hour() {
        LocalDateTime date = LocalDateTime.of(2013, Month.OCTOBER, 5, 6, 15);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.OCTOBER, 5, 6, 49);
        Car car = new Car();
        car.setDates(List.of(date, dateTime2));

        TollPrices tollPrice1 = new TollPrices(1L, 8, "lowest", 1L, "City1");
        TollPrices tollPrice2= new TollPrices(2L, 13, "medium", 1L, "City1");
        TollPrices tollPrice3 = new TollPrices(3L, 18, "highest", 1L, "City1");

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query); // Ensure setParameter returns non-null
        when(query.getResultList()).thenReturn(List.of(tollPrice1, tollPrice2, tollPrice3));
        int cal =  congestionTaxCalculatorService.getTax(car);
        assertEquals(13, cal);

    }

    @Test
    void getTax_Free_Vehicle() {

        LocalDateTime date = LocalDateTime.of(2013, Month.OCTOBER, 3, 12, 34);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.OCTOBER, 3, 16, 02);
        Motorbike motorbike = new Motorbike();
        motorbike.setDates( List.of(date, dateTime2));
        TollPrices tollPrice1 = new TollPrices(1L, 8, "lowest", 1L, "City1");
        TollPrices tollPrice2= new TollPrices(2L, 13, "medium", 1L, "City1");
        TollPrices tollPrice3 = new TollPrices(3L, 18, "highest", 1L, "City1");

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query); // Ensure setParameter returns non-null
        when(query.getResultList()).thenReturn(List.of(tollPrice1, tollPrice2, tollPrice3));
        int cal =  congestionTaxCalculatorService.getTax(motorbike);
        assertEquals(0, cal);

    }

    @Test
    void getTollFee() {
        LocalDateTime date = LocalDateTime.of(2013, Month.FEBRUARY, 7, 6, 23, 27);
        Car car = new Car();
        TollPrices tollPrice1 = new TollPrices(1L, 8, "lowest", 1L, "City1");
        TollPrices tollPrice2= new TollPrices(2L, 13, "medium", 1L, "City1");
        TollPrices tollPrice3 = new TollPrices(3L, 18, "highest", 1L, "City1");

        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(query);
        when(query.setParameter(any(String.class), any())).thenReturn(query); // Ensure setParameter returns non-null
        when(query.getResultList()).thenReturn(List.of(tollPrice1, tollPrice2, tollPrice3));
        int cal =  tollService.getTollFee(date, car);
        assertEquals(8, cal);
    }

}