package handlers;

import car_domain.model.Car;
import car_domain.model.Owner;
import car_domain.model.ServiceLog;
import user_domain.model.User;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import persistence.ServiceLogPersistence;
import persistence.UserPersistence;

public class DeleteRequestHandler {
    public Object handleDeleteRequest(Object deletedObject, String deletedObjectClass) {
        switch (deletedObjectClass) {
            case "car" -> {
                CarPersistence carPersistence = new CarPersistence();
                carPersistence.deleteObject((Car) deletedObject);
            }
            case "owner" -> {
                OwnerPersistence ownerPersistence = new OwnerPersistence();
                ownerPersistence.deleteObject((Owner) deletedObject);
            }
            case "user" -> {
                UserPersistence userPersistence = new UserPersistence();
                userPersistence.deleteObject((User) deletedObject);
            }
            case "serviceLog" -> {
                ServiceLogPersistence serviceLogPersistence = new ServiceLogPersistence();
                serviceLogPersistence.deleteObject((ServiceLog) deletedObject);
            }
        }
        return true;
    }
}
