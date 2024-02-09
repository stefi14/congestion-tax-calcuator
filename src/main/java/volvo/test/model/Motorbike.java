package volvo.test.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MOTORCYCLE")
public class Motorbike extends Vehicle {
    @Override
    public String getVehicleType() {
        return "MOTORCYCLE";
    }

}
