package model.persistence;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Persistence<T> {
    private final Class<T> type;
    private Connection databaseConnection;

    @SuppressWarnings("unchecked")
    public Persistence() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract T selectObject(String id);

    protected void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_service", "root", "passw0rd");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection() {
        if (databaseConnection != null) {
            try {
                databaseConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<T> selectObjectsFromQuery(String query) {
        openConnection();
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            stmt.executeQuery(query);
            ResultSet resultSet = stmt.getResultSet();
            List<T> objects = createObjects(resultSet);
            closeConnection();
            return objects;
        } catch (SQLException e) {
            System.out.println("Unable to retrieve all " + type.getSimpleName() + "s");
        }

        closeConnection();
        return null;
    }

    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] constructors = type.getDeclaredConstructors();
        Constructor constructor = null;
        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                constructor.setAccessible(true);
                T instance = (T) constructor.newInstance();
                populateInstance(resultSet, instance);
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void populateInstance(ResultSet resultSet, T instance) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();
            Object value = resultSet.getObject(fieldName);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(instance, value);
        }
    }

    protected T createObject(ResultSet resultSet) {
        try {
            Constructor constructor = type.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance();

            while (resultSet.next()) {
                populateInstance(resultSet, instance);
            }

            return instance;
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 IntrospectionException e) {
            return null;
        }
    }

    private String createInsertQuery(T t) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(type.getSimpleName()).append(" (");
        String prefix = "";
        for (Field field : type.getDeclaredFields()) {
            query.append(prefix);
            prefix = ", ";
            query.append(field.getName());
        }
        query.append(") VALUES (");
        prefix = "";
        try {
            for (Field field : type.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                query.append(prefix);
                prefix = ", ";
                query.append("'").append(method.invoke(t)).append("'");
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        query.append(");");
        return query.toString();
    }

    public void insertObject(T object) {
        openConnection();

        String insertQuery = createInsertQuery(object);
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            System.out.println("Could not insert new " + type.getSimpleName());
        }

        closeConnection();
    }

    private String createUpdateQuery(T object, List<String> filters) {
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";
        stringBuilder.append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        try {
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();
                stringBuilder.append(prefix);
                prefix = ", ";
                stringBuilder.append(fieldName).append("=\"").append(method.invoke(object)).append("\"");
            }

            stringBuilder.append(" WHERE ");
            prefix = "";
            for (String filter : filters) {
                String[] parts = filter.split(":");
                stringBuilder.append(prefix).append(parts[0]).append("=\"").append(parts[1]).append("\"");
                prefix = " AND ";
            }
            stringBuilder.append(";");

        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void updateObject(T object, List<String> filters) {
        openConnection();

        String updateQuery = createUpdateQuery(object, filters);
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            stmt.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("Could not update the given " + type.getSimpleName());
        }

        closeConnection();
    }

    private String createDeleteQuery(T object) {
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";
        stringBuilder.append("DELETE FROM ").append(type.getSimpleName()).append(" WHERE ");
        try {
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getReadMethod();
                stringBuilder.append(prefix);
                prefix = " AND ";
                stringBuilder.append(fieldName).append("='").append(method.invoke(object)).append("'");
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void deleteObject(T object) {
        openConnection();

        String deleteQuery = createDeleteQuery(object);
        try {
            Statement stmt = getDatabaseConnection().createStatement();
            stmt.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.out.println("Could not delete the given " + type.getSimpleName());
        }

        closeConnection();
    }


    protected Connection getDatabaseConnection() {
        return databaseConnection;
    }

}
