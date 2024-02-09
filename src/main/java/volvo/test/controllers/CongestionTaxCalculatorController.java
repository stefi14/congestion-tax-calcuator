package volvo.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import volvo.test.model.Vehicle;
import volvo.test.service.CongestionTaxCalculatorService;


@RestController
@RequestMapping("/congestion")
public class CongestionTaxCalculatorController {

    @Autowired
    private CongestionTaxCalculatorService congestionTaxCalculatorService;

   @PostMapping(value = "/calculatetax", consumes =  MediaType.APPLICATION_JSON_VALUE)
       public ResponseEntity<String> calculateCongestionTax(@RequestBody Vehicle vehicle) {
          int taxFee = congestionTaxCalculatorService.getTax(vehicle);
        return ResponseEntity.ok("Congestion Tax for Vehicle Type: " + vehicle.getVehicleType() + ", City: " + vehicle.getCity() +  " is " + taxFee);
    }
}
