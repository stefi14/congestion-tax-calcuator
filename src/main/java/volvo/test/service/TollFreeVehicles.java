package volvo.test.service;

public enum TollFreeVehicles {
    MOTORCYCLE,
    TRACTOR,
    DIPLOMAT,
    FOREIGN,
    MILITARY;

    public static boolean isTollFreeVehicle(String vehicleType) {
        for (TollFreeVehicles type : TollFreeVehicles.values()){
            if (type.name().equalsIgnoreCase(vehicleType)) {
                return true;
            }
        }
        return false;
    }
}
