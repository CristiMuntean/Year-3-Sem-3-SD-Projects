package model.persistence;

import model.Owner;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OwnerPersistence extends Persistence<Owner> {

    public List<Owner> selectAllOwners() {
        String query = "SELECT * FROM owner";
        return selectObjectsFromQuery(query);
    }

    public List<String> selectDistinctOwnerNames() {
        openConnection();
        ArrayList<String> ownerNames = new ArrayList<>();
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String selectQuery = "SELECT distinct CONCAT(name, ' ', surname) as fullName from auto_service.owner;";
            stmt.executeQuery(selectQuery);
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                ownerNames.add((String) resultSet.getObject("fullName"));
            }
            closeConnection();
            return ownerNames;
        } catch (SQLException e) {
            System.out.println("Can't execute distinct owners select query");
        }
        closeConnection();
        return null;
    }

    private List<String> getSelectedOwnerFields(Owner selectedOwner) {
        List<String> selectedOwnerFields = new ArrayList<>();
        for (Field field : Owner.class.getDeclaredFields()) {
            String fieldName = field.getName();
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, Owner.class);
                Method method = propertyDescriptor.getReadMethod();
                selectedOwnerFields.add(fieldName + ":" + method.invoke(selectedOwner));
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        return selectedOwnerFields;
    }

    public void updateOwner(Owner owner, Owner selectedOwner) {
        List<String> filters = getSelectedOwnerFields(selectedOwner);
        updateObject(owner, filters);
    }

    @Override
    public Owner selectObject(String id) {
        openConnection();

        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String sqlQuery = "SELECT * FROM owner WHERE cnp = \"" + id + "\";";
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            Owner value = createObject(resultSet);
            closeConnection();

            return value;
        } catch (SQLException e) {
            System.out.println("Unable to retrieve owner");
        }
        closeConnection();
        return null;
    }
}
