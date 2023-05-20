package handlers;

import user_domain.model.User;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import persistence.ServiceLogPersistence;
import persistence.UserPersistence;

public class GetRequestHandler {
    public Object handleGetRequest(String request) {
        String[] parts = request.split(" ");
        if(parts.length == 1) return 470;
        if(parts.length >= 2) {
            switch (parts[1]) {
                case "distinctOwners" -> {
                    OwnerPersistence ownerPersistence = new OwnerPersistence();
                    return ownerPersistence.selectDistinctOwnerNames();
                }
                case "distinctBrands" -> {
                    CarPersistence  carPersistence = new CarPersistence();
                    return carPersistence.selectDistinctColumn("brand");
                }
                case "distinctFuelTypes" -> {
                    CarPersistence carPersistence = new CarPersistence();
                    return carPersistence.selectDistinctColumn("fuelType");
                }
                case "distinctColors" -> {
                    CarPersistence carPersistence = new CarPersistence();
                    return carPersistence.selectDistinctColumn("color");
                }
                case "carsWithOwners" -> {
                    CarPersistence carPersistence = new CarPersistence();
                    return carPersistence.selectAllCarsWithOwners();
                }
                case "cars" -> {
                    CarPersistence carPersistence = new CarPersistence();
                    return carPersistence.selectAllCars();
                }
                case "owners" -> {
                    OwnerPersistence ownerPersistence = new OwnerPersistence();
                    return ownerPersistence.selectAllOwners();
                }
                case "users" -> {
                    UserPersistence userPersistence = new UserPersistence();
                    return userPersistence.selectAllUsers();
                }
                case "serviceLogWithNumber" -> {
                    ServiceLogPersistence serviceLogPersistence = new ServiceLogPersistence();
                    return serviceLogPersistence.selectObject(parts[2]);
                }
                case "userWithUsername" -> {
                    UserPersistence userPersistence = new UserPersistence();
                    User user = userPersistence.selectObject(parts[2]);
                    return user == null ? new User() : user;
                }
            }
        }
        return 471;
    }
}
