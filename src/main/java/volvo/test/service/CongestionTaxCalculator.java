package volvo.test.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import volvo.test.model.Vehicle;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.*;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class CongestionTaxCalculator {

    @Value("${tax.rate.lowest}")
    int lowestTax;
    @Value("${tax.rate.medium}")
    int mediumTax;
    @Value("${tax.rate.highest}")
    int highestTax;

    public int getTax(Vehicle vehicle, List<LocalDateTime> localDateTimes) {
        Map<LocalDate, Integer> map = calculateFeePerday(vehicle, localDateTimes);
        int totalFee = 0;
        for (int value : map.values()) {
            totalFee += Math.min(value, 60);
        }
        return  totalFee;
    }

    public int getTollFee(LocalDateTime date, Vehicle vehicle)
    {
        if (vehicle == null) {
            throw new Error("No vehicle found");
        }
        if (isTollFreeDate(date) || TollFreeVehicles.isTollFreeVehicle(vehicle.getVehicleType())) return 0;

        int hour = date.getHour();
        int minute = date.getMinute();

        if (hour == 6) return minute <= 29 ? lowestTax : mediumTax;
        else if (hour == 7) return highestTax;
        else if (hour == 8) return minute <= 29 ? mediumTax : highestTax;
        else if (hour > 8 && (hour < 15 )) return lowestTax;
        else if (hour == 15 ) return minute <= 29 ? mediumTax : highestTax;
        else if (hour == 16) return highestTax;
        else if (hour == 17) return mediumTax;
        else if (hour == 18 && minute <= 29) return lowestTax;
        else return 0;
    }

    private Map<LocalDate, Integer> calculateFeePerday(Vehicle vehicle, List<LocalDateTime> localDateTimes) {
        localDateTimes = localDateTimes.stream().sorted().collect(Collectors.toList());
        LocalDateTime previous = localDateTimes.get(0);
        int totalFee = getTollFee(previous, vehicle);
        Map<LocalDate, Integer> map = initiateMap(previous, totalFee);

        for (int i = 1; i < localDateTimes.size(); i++) {
            LocalDateTime current = localDateTimes.get(i);
            long duration = Duration.between(previous.toLocalTime(), current.toLocalTime()).toMinutes();
            int currentFee;
            LocalDate dateOf = LocalDate.of(current.getDayOfYear(), current.getMonth(), current.getDayOfMonth());

            if (duration < 60 && (previous.getDayOfYear() == current.getDayOfYear())) {
                int previousTollFee = getTollFee(previous, vehicle);
                int currentTollFee = getTollFee(current, vehicle);
                currentFee = Math.max(currentTollFee, previousTollFee) ;
                if (previousTollFee < currentTollFee) {
                    map.put(dateOf, map.getOrDefault(dateOf, 0) - previousTollFee + currentFee);
                }
            } else {
                currentFee = getTollFee(current, vehicle);
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

    private boolean isTollFreeDate(LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfWeek().getValue();
        int dayOfMonth = date.getDayOfMonth();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 2013) {
            return month == 3 && (dayOfMonth == 28 || dayOfMonth == 29) || month == 4 && (dayOfMonth == 1 || dayOfMonth == 30) || month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9) || month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21) || month == 7 || month == 11 && dayOfMonth == 1 || month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31);
        }
        return false;
    }
}
