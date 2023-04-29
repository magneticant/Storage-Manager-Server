//package operation.user;
package rs.np.storage_manager_server.operation.user;

import java.util.List;

import rs.np.storage_manager_common.domain.User;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 *
 * @author Milan
 */
public class SelectAllUsersParam extends GenericSystemOperation{
private List<User> users;

    @Override
    protected void preconditions(Object parameter) throws Exception {
        if(parameter == null || !(parameter instanceof User)){
           throw new Exception("Please input a valid user to fetch.");
        }
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        users = repository.selectAll((User)parameter);
    }

    public List<User> getUsers() {
        return users;
    }
 
    
}
