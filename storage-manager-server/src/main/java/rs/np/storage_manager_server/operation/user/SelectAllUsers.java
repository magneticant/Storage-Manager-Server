//package operation.user;
package rs.np.storage_manager_server.operation.user;

import java.util.List;

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllUsers extends GenericSystemOperation{
private List<User> users;

    @Override
    protected void preconditions(Object parameter) throws Exception {}

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        users = repository.selectAll();
    }

    public List<User> getUsers() {
        return users;
    }
    
}
