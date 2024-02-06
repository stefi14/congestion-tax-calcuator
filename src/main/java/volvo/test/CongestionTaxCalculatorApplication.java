package volvo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import volvo.test.model.Car;
import volvo.test.service.CongestionTaxCalculator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@SpringBootApplication
public class CongestionTaxCalculatorApplication {

	public static void main(String[] args) {SpringApplication.run(CongestionTaxCalculatorApplication.class, args);}

}
