package volvo.test.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.*;
import java.text.*;

public interface Vehicle {
    String getVehicleType();
    String getZone();
    List<LocalDateTime> getDates();
}
