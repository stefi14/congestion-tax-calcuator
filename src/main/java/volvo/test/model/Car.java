package volvo.test.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {

    @Override
    public String getVehicleType() {
        return "car";
    }

}
