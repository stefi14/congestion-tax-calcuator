package volvo.test.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Data
public class Motorbike implements Vehicle {

    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }

    @Override
    public String getZone() {
        return "A";
    }

    @Override
    public List<LocalDateTime> getDates() {
        LocalDateTime dateTime = LocalDateTime.of(2013, Month.FEBRUARY, 7, 6, 23, 27);
        LocalDateTime dateTime2 = LocalDateTime.of(2013, Month.FEBRUARY, 7, 15, 27, 00);
        LocalDateTime dateTime3 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 6, 27, 00);
        LocalDateTime dateTime4 = LocalDateTime.of(2013, Month.FEBRUARY, 8, 14, 36, 00);
        return List.of(dateTime, dateTime2, dateTime3, dateTime4);
    }


}
