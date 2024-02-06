package volvo.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import volvo.test.model.Car;
import volvo.test.model.Motorbike;
import volvo.test.model.Vehicle;
import volvo.test.service.CongestionTaxCalculator;


@RestController
@RequestMapping("/congestion")
public class CongestionTaxCalculatorController {

    @Autowired
    private CongestionTaxCalculator congestionTaxCalculator;

    @GetMapping("/calculatetax")
    public ResponseEntity<String> calculateCongestionTax(@RequestParam String vehicleType) {
        // for simplicity just two cases and this is here only for test
        Vehicle vehicle;
        if ("car".equalsIgnoreCase(vehicleType)) {
            vehicle = new Car();
        } else if ("motorcycle".equalsIgnoreCase(vehicleType)) {
            vehicle = new Motorbike();
        } else {
            return ResponseEntity.badRequest().body("The vehicle type is unknown: " + vehicleType);
        }

        int taxFee = congestionTaxCalculator.getTax(vehicle, vehicle.getDates());
        return ResponseEntity.ok("Congestion Tax for Vehicle Type: " + vehicle.getVehicleType() + ", Zone: " + vehicle.getZone() +  " is " + taxFee + " SEK");
    }

  /*  @GetMapping("/calculatetax")
    public ResponseEntity<String> calculateCongestionTax(@RequestBody Vehicle vehicle) {
          int taxFee = congestionTaxCalculator.getTax(vehicle, vehicle.getDates());
        return ResponseEntity.ok("Congestion Tax for Vehicle Type: " + vehicle.getVehicleType() + ", Zone: " + vehicle.getZone() +  " is " + taxFee);
    }*/
}
