package dal;

import java.util.List;

import db.DataAccessException;
import model.Contract;
import model.Tenant;

public interface ContractDBIF {

    Contract getById(int id) throws DataAccessException;
    int add(Contract c) throws DataAccessException;
    void update(Contract c) throws DataAccessException;
    void delete(Contract c) throws DataAccessException;

    List<Contract> getAll() throws DataAccessException;
    List<Integer> getAllContractIDsByRoomID(int roomID) throws DataAccessException;
}
