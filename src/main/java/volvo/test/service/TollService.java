package volvo.test.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import volvo.test.model.TollPrices;
import volvo.test.model.Vehicle;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
public class TollService {

    @Autowired
    @PersistenceContext
    private final EntityManager entityManager;

    public TollService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<TollPrices> getTollFeeByCity(Long cityId) {
        return entityManager.createQuery(
                        "SELECT t FROM TollPrices t WHERE t.cityId = :cityId", TollPrices.class)
                .setParameter("cityId", cityId)
                .getResultList();
    }


        public int getTollFee(LocalDateTime date, Vehicle vehicle)
    {
        int lowestTax = getFeeByCity(vehicle, "lowest");
        int mediumTax = getFeeByCity(vehicle, "medium");
        int highestTax = getFeeByCity(vehicle, "highest");
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

    private Integer getFeeByCity(Vehicle vehicle, String type) {
        List<TollPrices> tollPrices = getTollFeeByCity(vehicle.getCity());
        return tollPrices.stream().filter(prices -> prices.getType().equalsIgnoreCase(type)).findFirst().map(TollPrices::getPrice).orElse(0);
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
