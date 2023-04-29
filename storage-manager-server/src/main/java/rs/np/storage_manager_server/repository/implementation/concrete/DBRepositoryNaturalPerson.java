//package repository.implementation.concrete;
package rs.np.storage_manager_server.repository.implementation.concrete;

import java.util.List;

import rs.np.storage_manager_common.domain.abstraction.implementation.NaturalPerson;
import rs.np.storage_manager_server.repository.DBRepository;

/**
 *
 * @author Milan
 */
public class DBRepositoryNaturalPerson implements DBRepository<NaturalPerson>{

    @Override
    public List<NaturalPerson> selectAll(NaturalPerson parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(NaturalPerson parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(NaturalPerson parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(NaturalPerson parameter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NaturalPerson> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
