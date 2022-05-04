package test.dal;

import controller.DataController;
import dal.TenantDB;
import dal.TenantDBIF;
import db.DBConnection;
import db.DBHelper;
import db.DataAccessException;
import model.Contract;
import model.StudyProof;
import model.Tenant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestTenantDB {
    static DBConnection dbConnection;
    static TenantDBIF tenantDB = new TenantDB();
    static DataController dataCtrl;

    @BeforeAll
    static void setUp() throws DataAccessException {
        dbConnection = DBConnection.getInstance();
        dataCtrl = new DataController();
    }

    @BeforeEach
    void reset() throws DataAccessException, IOException {
//        dataCtrl.clear();
    }

    @Test()
    void testAddGetTenant() throws DataAccessException {
        String firstName = "Daniels";
        String lastName = "Kanepe";
        String email = "danixkanepe@gmail.com";
        String phone = "1234567890";
        StudyProof studyProof = null;
        List<Contract> contracts = Collections.emptyList();

        Tenant tenant = new Tenant(-1, firstName, lastName, email, phone, studyProof, contracts);
        int tenantID = tenantDB.add(tenant);

        Tenant dbTenant = tenantDB.getById(tenantID);
        assert dbTenant.getID() == tenantID;
        assert dbTenant.getFirstName().equals(firstName);
        assert dbTenant.getLastName().equals(lastName);
        assert dbTenant.getEmail().equals(email);
        assert dbTenant.getPhone().equals(phone);
        assert dbTenant.getStudyProof() == null;
        assert dbTenant.getContracts().isEmpty();
    }

    @Test()
    void testUpdateTenant() throws DataAccessException {
        // TODO: Implement this test
    }

    @Test()
    void testDeleteTenant() throws DataAccessException {
    }

    @Test()
    void testGetAllTenants() throws DataAccessException {
        // TODO: Implement this test
    }

    @AfterAll
    static void tearDown() throws DataAccessException, IOException {
//        dataCtrl.clear();
//        dataCtrl.addDemoData();
        dbConnection.closeConnection();
    }

}
