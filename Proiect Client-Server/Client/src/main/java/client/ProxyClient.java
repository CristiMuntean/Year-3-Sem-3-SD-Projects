package client;

import car_domain.model.Car;
import car_domain.model.CarJoinedOwner;
import car_domain.model.Owner;
import car_domain.model.ServiceLog;
import user_domain.controller.LoginController;
import user_domain.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProxyClient implements ClientInterface {
    private Client client;
    private static HashMap<String, List<Object>> objectListHashMap;

    public ProxyClient(int port) throws IOException {
        objectListHashMap = new HashMap<>();
        client = new Client(port);
        LoginController loginController = new LoginController(this);
    }

    @Override
    public void sendMessageToServer(Object message) throws IOException {
        client.sendMessageToServer(message);
    }

    @Override
    public Object receiveResponseFromServer() throws IOException, ClassNotFoundException {
        return client.receiveResponseFromServer();
    }

    public List<User> getUserList() {
        if(objectListHashMap.containsKey("users"))
            return objectListHashMap.get("users").stream().map(el -> (User)el).collect(Collectors.toList());
        String message = "GET users";
        try {
            sendMessageToServer(message);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("users", response);
            return response.stream().map(el -> (User) el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not get user list from server");
        }
        return new ArrayList<>();
    }

    public User getUserWithUsername(String username) {
        List<User> userList = getUserList();
        return userList.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public List<CarJoinedOwner> getCarJoinedOwnersList() {
        if(objectListHashMap.containsKey("carsWithOwners"))
            return objectListHashMap.get("carsWithOwners").stream().map(el -> (CarJoinedOwner)el).collect(Collectors.toList());
        String requestMessage = "GET carsWithOwners";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("carsWithOwners", response);
            return response.stream().map(el -> (CarJoinedOwner)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive cars with owners list from server");
        }
        return new ArrayList<>();
    }

    public List<String> getDistinctOwners() {
        if(objectListHashMap.containsKey("distinctOwners"))
            return objectListHashMap.get("distinctOwners").stream().map(el -> (String)el).collect(Collectors.toList());
        String requestMessage = "GET distinctOwners";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("distinctOwners", response);
            return response.stream().map(el -> (String)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive distinct owner name list from server");
        }
        return new ArrayList<>();
    }

    public List<String> getDistinctCarBrands() {
        if(objectListHashMap.containsKey("distinctBrands"))
            return objectListHashMap.get("distinctBrands").stream().map(el -> (String)el).collect(Collectors.toList());
        String requestMessage = "GET distinctBrands";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("distinctBrands", response);
            return response.stream().map(el -> (String)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive distinct brand list from server");
        }
        return new ArrayList<>();
    }

    public List<String> getDistinctFuelTypes() {
        if(objectListHashMap.containsKey("distinctFuelTypes"))
            return objectListHashMap.get("distinctFuelTypes").stream().map(el -> (String)el).collect(Collectors.toList());
        String requestMessage = "GET distinctFuelTypes";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("distinctFuelTypes", response);
            return response.stream().map(el -> (String)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive distinct fuel type list from server");
        }
        return new ArrayList<>();
    }

    public List<String> getDistinctColors() {
        if(objectListHashMap.containsKey("distinctColors"))
            return objectListHashMap.get("distinctColors").stream().map(el -> (String)el).collect(Collectors.toList());
        String requestMessage = "GET distinctColors";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("distinctColors", response);
            return response.stream().map(el -> (String)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive distinct color list from server");
        }
        return new ArrayList<>();
    }

    public ServiceLog getServiceLogFromServiceLogNumber(String number) {
        if(objectListHashMap.containsKey("serviceLogs")) {
            List<ServiceLog> serviceLogs = objectListHashMap.get("serviceLogs").stream().map(el -> (ServiceLog)el).collect(Collectors.toList());
            return serviceLogs.stream().filter(el -> el.getServiceNumber().equals(number)).findFirst().orElse(null);
        }

        String requestMessage = "GET serviceLogWithNumber " + number;
        try {
            client.sendMessageToServer(requestMessage);
            Object response = client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("serviceLogs"))
                objectListHashMap.get("serviceLogs").add(response);
            else objectListHashMap.put("serviceLogs", Arrays.asList(response));
            return (ServiceLog) response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive user list from server");
        }
        return null;
    }

    public List<Car> getCarList() {
        if(objectListHashMap.containsKey("cars"))
            return objectListHashMap.get("cars").stream().map(el -> (Car)el).collect(Collectors.toList());
        String requestMessage = "GET cars";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("cars", response);
            return response.stream().map(el -> (Car)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive car list from server");
        }
        return new ArrayList<>();
    }

    public List<Owner> getOwnerList() {
        if(objectListHashMap.containsKey("owners"))
            return objectListHashMap.get("owners").stream().map(el -> (Owner)el).collect(Collectors.toList());
        String requestMessage = "GET owners";
        try {
            sendMessageToServer(requestMessage);
            List<Object> response = (List<Object>) receiveResponseFromServer();
            objectListHashMap.put("owners", response);
            return response.stream().map(el -> (Owner)el).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to receive owner list from server");
        }
        return new ArrayList<>();
    }

    public void insertOwner(Owner owner) {
        if(objectListHashMap.containsKey("owners")){
            List<Owner> ownerList = objectListHashMap.get("owners").stream().map(el -> (Owner)el).toList();
            for(Owner owner1: ownerList)
                if(owner1.equals(owner) || (owner1.getCnp().equals(owner.getCnp())))return;
        }
        String requestMessage = "INSERT owner";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(owner);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("owners"))
                objectListHashMap.get("owners").add(owner);
            objectListHashMap.remove("carsWithOwners");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform insertion of new owner");
        }
    }

    public void updateOwner(Owner newOwner, Owner oldOwner) {
        String requestMessage = "UPDATE owner";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(newOwner);
            client.receiveResponseFromServer();
            client.sendMessageToServer(oldOwner);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("owners")) {
                objectListHashMap.get("owners").remove(oldOwner);
                objectListHashMap.get("owners").add(newOwner);
            }
            objectListHashMap.remove("carsWithOwners");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to update selected owner");
        }
    }

    public void insertUser(User user) {
        if(objectListHashMap.containsKey("users")){
            List<User> userList = objectListHashMap.get("users").stream().map(el -> (User)el).toList();
            for(User user1: userList)
                if(user1.equals(user) || (user1.getId().equals(user.getId()) && user1.getUsername().equals(user.getUsername())))return;
        }
        String requestMessage = "INSERT user";
        try {
            sendMessageToServer(requestMessage);
            receiveResponseFromServer();
            sendMessageToServer(user);
            receiveResponseFromServer();
            if(objectListHashMap.containsKey("users"))
                objectListHashMap.get("users").add(user);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform insertion of new user");
        }
    }

    public void updateUser(User newUser, User oldUser) {
        String requestMessage = "UPDATE user";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(newUser);
            client.receiveResponseFromServer();
            client.sendMessageToServer(oldUser);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("users")) {
                objectListHashMap.get("users").remove(oldUser);
                objectListHashMap.get("users").add(newUser);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to update selected user");
        }
    }

    public void insertCar(Car car) {
        if(objectListHashMap.containsKey("cars")){
            List<Car> carList = objectListHashMap.get("cars").stream().map(el -> (Car)el).toList();
            for(Car car1: carList)
                if(car1.equals(car) || car1.getId().equals(car.getId()))return;
        }
        String requestMessage = "INSERT car";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(car);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("cars"))
                objectListHashMap.get("cars").add(car);
            objectListHashMap.remove("carsWithOwners");
            objectListHashMap.remove("distinctBrands");
            objectListHashMap.remove("distinctFuelTypes");
            objectListHashMap.remove("distinctColors");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform insertion of new car");
        }
    }

    public void updateCar(Car newCar, Car oldCar) {
        String requestMessage = "UPDATE car";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(newCar);
            client.receiveResponseFromServer();
            client.sendMessageToServer(oldCar);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("cars")) {
                objectListHashMap.get("cars").remove(oldCar);
                objectListHashMap.get("cars").add(newCar);
            }
            objectListHashMap.remove("carsWithOwners");
            objectListHashMap.remove("distinctBrands");
            objectListHashMap.remove("distinctFuelTypes");
            objectListHashMap.remove("distinctColors");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to update selected car");
        }
    }

    public void deleteCar(Car car) {
        String firstRequestMessage = "DELETE car";
        try {
            client.sendMessageToServer(firstRequestMessage);
            String ack = (String) client.receiveResponseFromServer();
            client.sendMessageToServer(car);
            boolean status = (boolean) client.receiveResponseFromServer();
            if(status) {
                if(objectListHashMap.containsKey("cars"))
                    objectListHashMap.get("cars").remove(car);
                objectListHashMap.remove("carsWithOwners");
                objectListHashMap.remove("distinctBrands");
                objectListHashMap.remove("distinctFuelTypes");
                objectListHashMap.remove("distinctColors");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform deletion of car");
        }
    }

    public void deleteOwner(Owner owner) {
        String firstRequestMessage = "DELETE owner";
        try {
            client.sendMessageToServer(firstRequestMessage);
            String ack = (String) client.receiveResponseFromServer();
            client.sendMessageToServer(owner);
            boolean status = (boolean) client.receiveResponseFromServer();
            if(status) {
                if(objectListHashMap.containsKey("owners"))
                    objectListHashMap.get("owners").remove(owner);
                objectListHashMap.remove("carsWithOwners");
                objectListHashMap.remove("distinctOwners");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform deletion of owner");
        }
    }

    public void deleteUser(User user) {
        String firstRequestMessage = "DELETE user";
        try {
            client.sendMessageToServer(firstRequestMessage);
            String ack = (String) client.receiveResponseFromServer();
            client.sendMessageToServer(user);
            boolean status = (boolean) client.receiveResponseFromServer();
            if(status) {
                if(objectListHashMap.containsKey("users"))
                    objectListHashMap.get("users").remove(user);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform deletion of user");
        }
    }

    public void insertServiceLog(ServiceLog serviceLog) {
        if(objectListHashMap.containsKey("serviceLogs")){
            List<ServiceLog> serviceLogs = objectListHashMap.get("serviceLogs").stream().map(el -> (ServiceLog)el).toList();
            for(ServiceLog serviceLog1: serviceLogs)
                if(serviceLog1.equals(serviceLog) || serviceLog1.getServiceNumber().equals(serviceLog.getServiceNumber()))return;
        }
        String requestMessage = "INSERT serviceLog";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(serviceLog);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("serviceLogs"))
                objectListHashMap.get("serviceLogs").add(serviceLog);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform insertion of new service log");
        }
    }

    public void updateServiceLog(ServiceLog newServiceLog, ServiceLog oldServiceLog) {
        String requestMessage = "UPDATE serviceLog";
        try {
            client.sendMessageToServer(requestMessage);
            client.receiveResponseFromServer();
            client.sendMessageToServer(newServiceLog);
            client.receiveResponseFromServer();
            client.sendMessageToServer(oldServiceLog);
            client.receiveResponseFromServer();
            if(objectListHashMap.containsKey("serviceLogs")) {
                objectListHashMap.get("serviceLogs").remove(oldServiceLog);
                objectListHashMap.get("serviceLogs").add(newServiceLog);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to update selected service log");
        }
    }

    public void deleteServiceLog(ServiceLog serviceLog) {
        String firstRequestMessage = "DELETE serviceLog";
        try {
            client.sendMessageToServer(firstRequestMessage);
            String ack = (String) client.receiveResponseFromServer();
            client.sendMessageToServer(serviceLog);
            boolean status = (boolean) client.receiveResponseFromServer();
            if(status) {
                if(objectListHashMap.containsKey("serviceLogs"))
                    objectListHashMap.get("serviceLogs").remove(serviceLog);
                objectListHashMap.remove("carsWithOwners");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to perform deletion of car");
        }
    }

    public int processLoginAction(String username, String password) {
        if(objectListHashMap.containsKey("users")) {
            List<Object> objects = objectListHashMap.get("users");
            List<User> userList = objects.stream().map(el -> (User) el).collect(Collectors.toList());
            return handleLogin(username, password, userList);
        }
        return handleLogin(username, password, getUserList());
    }

    private int handleLogin(String username, String password, List<User> userList) {
        for(User user: userList) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                switch (user.getRole()) {
                    case "employee" -> {
                        return 250;
                    }
                    case "manager" -> {
                        return 251;
                    }
                    case "admin" -> {
                        return 252;
                    }
                }
            }
        }
        return 462;
    }
}
