package test.dal;

import controller.DBController;
import dal.TenantDB;
import dal.TenantDBIF;
import db.DBConnection;
import db.DataAccessException;
import model.Tenant;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
* Author: Daniels Kanepe
 */
public class TenantDBTest {
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
                Objects.equals(t1.getStudyProof(), t2.getStudyProof()) &&
                Objects.equals(t1.getContracts(), t2.getContracts());
    }

    @Test()
    void testAddGetTenant() throws DataAccessException {
        // Arrange
        Tenant tenant = new Tenant(-1, "Daniels", "Kanepe", "email@gmail.com",
        		"1234567890", null, Collections.emptyList());
        int tenantID = tenantDB.add(tenant);
        tenant.setId(tenantID);

        // Act
        Tenant dbTenant = tenantDB.getById(tenantID);

        // Assert
        Assertions.assertTrue(tenantsAreEqual(tenant, dbTenant));
    }

    @Test()
    void testDeleteTenant() throws DataAccessException {
        // Arrange
        Tenant tenant = new Tenant(-1, "Daniels", "Kanepe", "emaile@gmail.com",
                "1234567890", null, Collections.emptyList());
        int tenantID = tenantDB.add(tenant);
        tenant.setId(tenantID);

        // Act
        tenantDB.delete(tenant);

        // Assert
        Tenant dbTenant = tenantDB.getById(tenantID);

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
        Tenant tenant = new Tenant(-1, "Daniels", "Kanepe", "email@gmail.com",
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
        Tenant tenant1 = new Tenant(-1, "Daniels", "Kanepe", "email@gmail.com",
                "1234567890", null, Collections.emptyList());
        Tenant tenant2 = new Tenant(-1, "Daniels", "Kanepe", "email@gmail.com",
                "1234567890", null, Collections.emptyList());
        int tenantID1 = tenantDB.add(tenant1);
        int tenantID2 = tenantDB.add(tenant2);
        tenant1.setId(tenantID1);
        tenant2.setId(tenantID2);

        // Act
        List<Tenant> tenants = tenantDB.getAll();

        // Assert
        Assertions.assertEquals(2, tenants.size());
    }

    @AfterAll
    static void tearDown() throws DataAccessException, IOException {
        dataCtrl.clear();
        dataCtrl.addDemoData();
        dbConnection.closeConnection();
    }
}
