//package form.model;
package rs.np.storage_manager_server.form.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import rs.np.storage_manager_common.domain.User;

/**
 * Predstavlja table model klasu za ispis podataka o korisniku
 * 
 * @author Milan
 */
public class UserTableModel extends AbstractTableModel{
	/**
	 * privatni staticki atribut, serijski broj generisan na zahtev Serializable interfejsa.
	 */
	private static final long serialVersionUID = 9029828148687578883L;
	/**
	 * package scope niz naziva kolona tabele (columnNames) kao String[]
	 */
	String[] columnNames = new String[]{"ID","name","last name","username", "password"};
	/**
	 * package scope lista aktivnih korisnika programa
	 */
    List<User> users;
    /**
     * parametrizovani konstruktor klase
     *  
     * @param users, kao lista korisnika. Lista aktivnih korisnika programa (klijenata koji su prijavljeni)
     */
    public UserTableModel(List<User> users) {
        this.users = users;
    }
    
    @Override
    public String getColumnName(int column) {
    if(column>columnNames.length) return "n/a";
    return columnNames[column];
    }
    /**
     * Override metode isCellEditable iz {@link AbstractTableModel} klase. Nijedno polje nije izmenjivo
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
   
        User u = users.get(rowIndex);
        switch (columnIndex) {
            case 0: return u.getID();
            case 1: return u.getName();
            case 2: return u.getLastName();
            case 3: return u.getUsername();
            case 4: return u.getPassword();
            default:return "n/a";
        }
    }
    /**
     * javna metoda za dodavanje korisnika u tabelu
     * 
     * @param u tipa {@link User}, korisnik za dodavanje u internu listu aktivnih korisnika
     */
    public void addUser(User u){
        if(!users.contains(u)){
        users.add(u);
        fireTableDataChanged();
        }
    }
    
}
