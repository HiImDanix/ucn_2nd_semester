package test.dal;

import controller.DBController;
import dal.TenantDB;
import dal.TenantDBIF;
import db.DBConnection;
import db.DataAccessException;
import model.Contract;
import model.StudyProof;
import model.Tenant;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestTenantDB {
    static DBConnection dbConnection;
    static TenantDBIF tenantDB = new TenantDB();
    static DBController dataCtrl;

    @BeforeAll
    static void setUp() throws DataAccessException {
        dbConnection = DBConnection.getInstance();
        dataCtrl = new DBController();
    }

    @BeforeEach
    void reset() throws DataAccessException, IOException {
        dataCtrl.clear();
    }

    private boolean tenantsAreEqual(Tenant t1, Tenant t2) {
        return t1.getID() == t2.getID() &&
                t1.getFirstName().equals(t2.getFirstName()) &&
                t1.getLastName().equals(t2.getLastName()) &&
                t1.getEmail().equals(t2.getEmail()) &&
                t1.getPhone().equals(t2.getPhone()) &&
                t1.getStudyProof() == t2.getStudyProof() &&
                t1.getContracts().equals(t2.getContracts());
    }

    @Test()
    void testAddGetTenant() throws DataAccessException {
        // Arrange
        String firstName = "Daniels";
        String lastName = "Kanepe";
        String email = "danixkanepe@gmail.com";
        String phone = "1234567890";
        StudyProof studyProof = null;
        List<Contract> contracts = Collections.emptyList();

        // Act
        Tenant tenant = new Tenant(-1, firstName, lastName, email, phone, studyProof, contracts);
        int tenantID = tenantDB.add(tenant);
        tenant.setId(tenantID);
        Tenant dbTenant = tenantDB.getById(tenantID);

        // Assert
        Assertions.assertTrue(tenantsAreEqual(tenant, dbTenant));
    }

    @Test()
    void testUpdateTenant() throws DataAccessException {
        // TODO: Implement this test when CRUD is implemented
    }

    @Test()
    void testDeleteTenant() throws DataAccessException {
        // Arrange
        Tenant tenant = new Tenant(-1, "Daniels", "Kanepe", "danixkanepe@gmail.com",
                "1234567890", null, Collections.emptyList());
        int tenantID = tenantDB.add(tenant);
        tenant.setId(tenantID);

        // Act
        tenantDB.delete(tenant);
        Tenant dbTenant = tenantDB.getById(tenantID);

        // Assert
        Assertions.assertNull(dbTenant);
    }

    @Test()
    void testGetAllTenantsNone() throws DataAccessException {
        // Arrange
        int expected = 0;

        // Act
        List<Tenant> tenants = tenantDB.getAll();

        // Assert
        assert tenants.size() == expected;
    }

    @Test()
    void testGetAllTenantsOneTenant() throws DataAccessException {

        // Arrange
        Tenant tenant = new Tenant(-1, "Daniels", "Kanepe", "danixkanepe@gmail.com",
                "1234567890", null, Collections.emptyList());
        int tenantID = tenantDB.add(tenant);
        tenant.setId(tenantID);

        // Act
        List<Tenant> tenants = tenantDB.getAll();


        // Assert
        Tenant dbTenant = tenants.get(tenants.size() - 1);
        Assertions.assertTrue(tenantsAreEqual(tenant, dbTenant));
    }

    @Test()
    void testGetAllTenantsTwoTenants() throws DataAccessException {

        // Arrange
        Tenant tenant1 = new Tenant(-1, "Daniels", "Kanepe", "danixkanepe@gmail.com",
                "1234567890", null, Collections.emptyList());
        Tenant tenant2 = new Tenant(-1, "Daniels", "Kanepe", "danixkanepe@gmail.com",
                "1234567890", null, Collections.emptyList());
        int tenantID1 = tenantDB.add(tenant1);
        int tenantID2 = tenantDB.add(tenant2);
        tenant1.setId(tenantID1);
        tenant2.setId(tenantID2);

        // Act
        List<Tenant> tenants = tenantDB.getAll();

        // Assert
        Tenant dbTenant1 = tenants.get(tenants.size() - 2);
        Tenant dbTenant2 = tenants.get(tenants.size() - 1);
        Assertions.assertTrue(tenants.size() == 2);
        Assertions.assertTrue(tenantsAreEqual(tenant1, dbTenant1));
        Assertions.assertTrue(tenantsAreEqual(tenant2, dbTenant2));
    }

    @AfterAll
    static void tearDown() throws DataAccessException, IOException {
        dataCtrl.clear();
        dataCtrl.addDemoData();
        dbConnection.closeConnection();
    }

}
