import model.Car
import model.Owner
import model.ServiceLog
import model.User
import persistence.CarPersistence
import persistence.OwnerPersistence
import persistence.ServiceLogPersistence
import persistence.UserPersistence
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Connection
import java.sql.DriverManager

@Unroll
class DatabaseSpec extends Specification {

    @Shared
    DatabaseFactory factory = new DatabaseFactory()

    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "passw0rd")

    def tableWithNameExists(String tableName) {
        def resultSet = con.getMetaData().getTables(null, null, tableName, null)
        boolean exists = false
        while (resultSet.next()) {
            if (resultSet.getString(3).equals(tableName))
                exists = true
        }
        return exists
    }

    def objectExists(def objectPersistence, def object) {
        def objectList = objectPersistence.selectObjectsFromQuery("SELECT * FROM " + object.getClass().getSimpleName().toLowerCase())
        for (int i = 0; i < objectList.size(); i++) {
            if (object.equals(objectList.get(i)))
                return true
        }
        return false
    }

    def setupSpec() {
        factory.dropDatabase()
    }

    def cleanupSpec() {
        factory.dropDatabase()
    }

    def 'When no database exists, a new database without any tables is created'() {
        given:
        def created = factory.createNewDatabase()
        def resultSet = con.getMetaData().getCatalogs()
        String databaseName = null
        while (resultSet.next()) {
            databaseName = resultSet.getString(1)
            break
        }
        resultSet.close()

        expect:
        databaseName == "auto_service"
        created
    }

    def 'When a database exists, creating a new database fails'() {
        given:
        def created = factory.createNewDatabase()

        expect:
        !created
    }

    def 'When a database exists and has no tables, creating table #tableName succeeds'() {
        given:
        createTableMethod.call()

        expect:
        tableWithNameExists(tableName)

        where:
        createTableMethod                        | tableName
        ({ factory.createNewCarTable() })        | "car"
        ({ factory.createNewOwnerTable() })      | "owner"
        ({ factory.createNewServiceLogTable() }) | "servicelog"
        ({ factory.createNewUserTable() })       | "user"
    }

    def 'When trying to insert a new object into table #tableName, the insert succeeds'() {
        given:
        def existsBefore = objectExists(objectPersistence, object)
        objectPersistence.insertObject(object)
        def exists = objectExists(objectPersistence, object)
        expect:
        !existsBefore
        exists

        where:
        object                                                               | objectPersistence           | tableName
        new Car("testId", "testBrand", "testModel", "testColor", "testFuel") | new CarPersistence()        | "car"
        new Owner("testCnp", "testName", "testSurname")                      | new OwnerPersistence()      | "owner"
        new User("testId", "testUsername", "testPassword", "testRole")       | new UserPersistence()       | "user"
        new ServiceLog("testNumber", "testCnp", "testId")                    | new ServiceLogPersistence() | "servicelog"
    }

    def 'When trying to select an object that exists from table #tableName, the selection succeeds'() {
        given:
        def selectedObject = objectPersistence.selectObject(id)

        expect:
        selectedObject != null

        where:
        id             | objectPersistence           | tableName
        "testId"       | new CarPersistence()        | "car"
        "testCnp"      | new OwnerPersistence()      | "owner"
        "testUsername" | new UserPersistence()       | "user"
        "testNumber"   | new ServiceLogPersistence() | "servicelog"
    }

    def 'When trying to update an existing object into table #tableName, the update succeeds'() {
        given:
        def previousObjectExistsBefore = objectExists(objectPersistence, previousObject)
        def newObjectExistsBefore = objectExists(objectPersistence, newObject)
        updateMethod.call(objectPersistence, newObject, previousObject)
        def previousObjectExistsAfter = objectExists(objectPersistence, previousObject)
        def newObjectExistsAfter = objectExists(objectPersistence, newObject)

        expect:
        previousObjectExistsBefore
        !newObjectExistsBefore
        !previousObjectExistsAfter
        newObjectExistsAfter

        where:
        previousObject                                                       | newObject                                                                           | objectPersistence           | tableName    | updateMethod
        new Car("testId", "testBrand", "testModel", "testColor", "testFuel") | new Car("newTestId", "newTestBrand", "newTestModel", "newTestColor", "newTestFuel") | new CarPersistence()        | "car"        | ({ persistence, newCar, prevCar -> (persistence.updateCar(newCar, prevCar)) })
        new Owner("testCnp", "testName", "testSurname")                      | new Owner("newTestCnp", "newTestName", "newTestSurname")                            | new OwnerPersistence()      | "owner"      | ({ persistence, newOwner, prevOwner -> (persistence.updateOwner(newOwner, prevOwner)) })
        new User("testId", "testUsername", "testPassword", "testRole")       | new User("newTestId", "newTestUsername", "newTestPassword", "newTestRole")          | new UserPersistence()       | "user"       | ({ persistence, newUser, prevUser -> (persistence.updateUser(newUser, prevUser)) })
        new ServiceLog("testNumber", "testCnp", "testId")                    | new ServiceLog("newTestNumber", "newTestCnp", "newTestId")                          | new ServiceLogPersistence() | "servicelog" | ({ persistence, newLog, prevLog -> (persistence.updateLog(newLog, prevLog)) })
    }

    def 'When trying to delete an existing object from table #tableName, the delete succeeds'() {
        given:
        def objectExistsBefore = objectExists(objectPersistence, object)
        objectPersistence.deleteObject(object)
        def objectExistsAfter = objectExists(objectPersistence, object)

        expect:
        objectExistsBefore
        !objectExistsAfter

        where:
        object                                                                              | objectPersistence           | tableName
        new Car("newTestId", "newTestBrand", "newTestModel", "newTestColor", "newTestFuel") | new CarPersistence()        | "car"
        new Owner("newTestCnp", "newTestName", "newTestSurname")                            | new OwnerPersistence()      | "owner"
        new User("newTestId", "newTestUsername", "newTestPassword", "newTestRole")          | new UserPersistence()       | "user"
        new ServiceLog("newTestNumber", "newTestCnp", "newTestId")                          | new ServiceLogPersistence() | "servicelog"
    }

}
