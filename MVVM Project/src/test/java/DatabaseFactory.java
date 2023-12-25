public class DatabaseFactory extends model.persistence.DatabaseFactory {
    public boolean createNewDatabase() {
        return createDatabase();
    }

    public boolean createNewUserTable() {
        return createUserTable();
    }

    public boolean createNewCarTable() {
        return createCarTable();
    }

    public boolean createNewOwnerTable() {
        return createOwnerTable();
    }

    public boolean createNewServiceLogTable() {
        return createServiceLogTable();
    }

}
