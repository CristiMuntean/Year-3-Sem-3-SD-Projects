package handlers;

import car_domain.model.Car;
import car_domain.model.Owner;
import car_domain.model.ServiceLog;
import user_domain.model.User;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import persistence.ServiceLogPersistence;
import persistence.UserPersistence;

public class UpdateRequestHandler {
    public Object handleUpdateRequest(Object newObject, Object oldObject, String objectClasses) {
        switch (objectClasses) {
            case "user" -> {
                UserPersistence userPersistence = new UserPersistence();
                userPersistence.updateUser((User) newObject, (User) oldObject);
            }
            case "owner" -> {
                OwnerPersistence ownerPersistence = new OwnerPersistence();
                ownerPersistence.updateOwner((Owner) newObject, (Owner) oldObject);
            }
            case "car" -> {
                CarPersistence carPersistence = new CarPersistence();
                carPersistence.updateCar((Car) newObject, (Car) oldObject);
            }
            case "serviceLog" -> {
                ServiceLogPersistence serviceLogPersistence = new ServiceLogPersistence();
                serviceLogPersistence.updateLog((ServiceLog) newObject, (ServiceLog) oldObject);
            }
        }
        return true;
    }
}
