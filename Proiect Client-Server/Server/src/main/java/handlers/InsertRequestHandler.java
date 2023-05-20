package handlers;

import car_domain.model.Car;
import car_domain.model.Owner;
import car_domain.model.ServiceLog;
import user_domain.model.User;
import persistence.CarPersistence;
import persistence.OwnerPersistence;
import persistence.ServiceLogPersistence;
import persistence.UserPersistence;

public class InsertRequestHandler {
    public Object handleInsertRequest(Object insertedObject, String insertedObjectClass) {
        switch (insertedObjectClass) {
            case "user" -> {
                UserPersistence userPersistence = new UserPersistence();
                userPersistence.insertObject((User) insertedObject);
            }
            case "owner" -> {
                OwnerPersistence ownerPersistence = new OwnerPersistence();
                ownerPersistence.insertObject((Owner) insertedObject);
            }
            case "car" -> {
                CarPersistence carPersistence = new CarPersistence();
                carPersistence.insertObject((Car) insertedObject);
            }
            case "serviceLog" -> {
                ServiceLogPersistence serviceLogPersistence = new ServiceLogPersistence();
                serviceLogPersistence.insertObject((ServiceLog) insertedObject);
            }
        }
        return true;
    }
}
