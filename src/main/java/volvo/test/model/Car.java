package volvo.test.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Getter
@Setter
public class Car implements Vehicle {

    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public String getZone() {
        return "A";
    }

    /**
     *  This is only for the asignemnt test case so that it is easy to test
     */
    @Override
    public List<LocalDateTime> getDates() {

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

        return List.of(dateTime, dateTime2, dateTime3, dateTime4, dateTime7, dateTime6, dateTime5, dateTime8, dateTime9, dateTime10);

    }
}
