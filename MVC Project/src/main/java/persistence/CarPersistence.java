package persistence;

import model.Car;
import model.CarJoinedOwner;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarPersistence extends Persistence<Car> {

    public List<Car> selectAllCars() {
        String query = "SELECT * FROM car";
        return selectObjectsFromQuery(query);
    }

    public List<CarJoinedOwner> selectAllCarsWithOwners() {
        String query = "SELECT serviceNumber, carId, brand, modelName, color, fuelType, cnp, " +
                "CONCAT(owner.name, ' ', owner.surname) as carOwner from car" +
                " JOIN servicelog ON car.id = servicelog.carId" +
                " JOIN owner ON servicelog.ownerCnp = owner.cnp;";

        openConnection();

        try {
            Statement stmt = getDatabaseConnection().createStatement();
            stmt.executeQuery(query);
            ResultSet resultSet = stmt.getResultSet();
            List<CarJoinedOwner> objects = createJoinedObjects(resultSet);
            closeConnection();
            return objects;
        } catch (SQLException e) {
            System.out.println("Unable to return joined table");
        }

        closeConnection();
        return null;
    }

    private List<CarJoinedOwner> createJoinedObjects(ResultSet resultSet) {
        List<CarJoinedOwner> list = new ArrayList<>();
        Constructor[] constructors = CarJoinedOwner.class.getDeclaredConstructors();
        Constructor constructor = null;
        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                constructor.setAccessible(true);
                CarJoinedOwner instance = (CarJoinedOwner) constructor.newInstance();
                for (Field field : CarJoinedOwner.class.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, CarJoinedOwner.class);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> selectDistinctColumn(String columnName) {
        openConnection();
        ArrayList<String> ownerNames = new ArrayList<>();
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String selectQuery = "SELECT distinct " + columnName + " from auto_service.car;";
            stmt.executeQuery(selectQuery);
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                ownerNames.add((String) resultSet.getObject(columnName));
            }
            closeConnection();
            return ownerNames;
        } catch (SQLException e) {
            System.out.println("Can't execute distinct brand select query");
        }
        closeConnection();
        return null;
    }

    private List<String> getSelectedCarFields(Car selectedCar) {
        List<String> selectedOwnerFields = new ArrayList<>();
        for (Field field : Car.class.getDeclaredFields()) {
            String fieldName = field.getName();
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, Car.class);
                Method method = propertyDescriptor.getReadMethod();
                selectedOwnerFields.add(fieldName + ":" + method.invoke(selectedCar));
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        return selectedOwnerFields;
    }

    public void updateCar(Car car, Car selectedCar) {
        List<String> filters = getSelectedCarFields(selectedCar);
        updateObject(car, filters);
    }

    @Override
    public Car selectObject(String id) {
        openConnection();

        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String sqlQuery = "SELECT * FROM car WHERE id = \"" + id + "\";";
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            Car value = createObject(resultSet);
            closeConnection();

            return value;
        } catch (SQLException e) {
            System.out.println("Unable to retrieve car");
        }
        closeConnection();
        return null;
    }
}
