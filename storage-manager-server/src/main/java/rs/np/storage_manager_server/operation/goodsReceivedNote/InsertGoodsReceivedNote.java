//package operation.goodsReceivedNote;
package rs.np.storage_manager_server.operation.goodsReceivedNote;

import rs.np.storage_manager_common.domain.abstraction.implementation.GoodsReceivedNote;
import rs.np.storage_manager_server.operation.GenericSystemOperation;

/**
 * Klasa za unos prijemnice u bazu podataka. Nasledjuje klasu GenericSystemOperation.
 * 
 * @author Milan
 */
public class InsertGoodsReceivedNote extends GenericSystemOperation{
	/**
	 * preduslovi za transakciju
	 * @param parameter, tipa Object. Objekat nad kojim se vrsi sistemska operacija
	 * @throws Exception ako je parametar null ili nije tipa GoodsReceivedNote
	 */
    @Override
    protected void preconditions(Object parameter) throws Exception {
        if(parameter == null || !(parameter instanceof GoodsReceivedNote)){
           throw new Exception("Please input a valid goods received note to insert.");
        }
    }

    @Override
    protected void executeOperation(Object parameter) throws Exception {
        repository.insert((GoodsReceivedNote)parameter);
    }
    
}
