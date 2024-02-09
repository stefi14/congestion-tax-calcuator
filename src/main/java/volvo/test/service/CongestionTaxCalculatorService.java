package volvo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import volvo.test.model.Vehicle;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class CongestionTaxCalculatorService {

    @Autowired
    private final TollService tollService;

    public CongestionTaxCalculatorService(TollService tollService) {
        this.tollService = tollService;
    }

    public int getTax(Vehicle vehicle) {
        Map<LocalDate, Integer> map = calculateFeePerday(vehicle);
        int totalFee = 0;
        for (int value : map.values()) {
            totalFee += Math.min(value, 60);
        }
        return  totalFee;
    }

    private Map<LocalDate, Integer> calculateFeePerday(Vehicle vehicle) {

        List<LocalDateTime> localDateTimes = vehicle.getDates().stream().sorted().toList();
        LocalDateTime previous = localDateTimes.get(0);
        int totalFee = tollService.getTollFee(previous, vehicle);
        Map<LocalDate, Integer> map = initiateMap(previous, totalFee);

        for (int i = 1; i < localDateTimes.size(); i++) {
            LocalDateTime current = localDateTimes.get(i);
            long duration = Duration.between(previous.toLocalTime(), current.toLocalTime()).toMinutes();
            int currentFee;
            LocalDate dateOf = LocalDate.of(current.getDayOfYear(), current.getMonth(), current.getDayOfMonth());

            if (duration < 60 && (previous.getDayOfYear() == current.getDayOfYear())) {
                int previousTollFee = tollService.getTollFee(previous, vehicle);
                int currentTollFee = tollService.getTollFee(current, vehicle);
                currentFee = Math.max(currentTollFee, previousTollFee) ;
                if (previousTollFee < currentTollFee) {
                    map.put(dateOf, map.getOrDefault(dateOf, 0) - previousTollFee + currentFee);
                }
            } else {
                currentFee = tollService.getTollFee(current, vehicle);
                map.put(dateOf, map.getOrDefault(dateOf, 0) + currentFee);
                previous = current;
            }

            if (previous.getDayOfYear() != current.getDayOfYear()) {
                previous = current;
            }
        }

        return map;
    }

    private Map<LocalDate, Integer> initiateMap( LocalDateTime previous, int totalFee) {
        Map<LocalDate, Integer> map = new HashMap<>();
        LocalDate dateOf = LocalDate.of(previous.getDayOfYear(), previous.getMonth(), previous.getDayOfMonth());

        map.put(dateOf, map.getOrDefault(dateOf, 0) + totalFee);
        return map;
    }


}
