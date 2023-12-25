package model.persistence;

import model.ServiceLog;

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

public class ServiceLogPersistence extends Persistence<ServiceLog> {
    private List<String> getSelectedServiceLogFields(ServiceLog selectedServiceLog) {
        List<String> selectedServiceLogFields = new ArrayList<>();
        for (Field field : ServiceLog.class.getDeclaredFields()) {
            String fieldName = field.getName();
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(fieldName, ServiceLog.class);
                Method method = propertyDescriptor.getReadMethod();
                selectedServiceLogFields.add(fieldName + ":" + method.invoke(selectedServiceLog));
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        return selectedServiceLogFields;
    }

    public void updateLog(ServiceLog log, ServiceLog selectedLog) {
        List<String> filters = getSelectedServiceLogFields(selectedLog);
        updateObject(log, filters);
    }

    @Override
    public ServiceLog selectObject(String id) {
        openConnection();

        try {
            Statement stmt = getDatabaseConnection().createStatement();
            String sqlQuery = "SELECT * FROM servicelog WHERE serviceNumber = \"" + id + "\";";
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();

            ServiceLog value = createObject(resultSet);
            closeConnection();

            return value;
        } catch (SQLException e) {
            System.out.println("Unable to retrieve servicelog");
        }
        closeConnection();
        return null;
    }
}
