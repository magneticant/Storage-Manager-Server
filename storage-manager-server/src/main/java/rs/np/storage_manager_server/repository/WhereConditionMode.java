//package repository;
package rs.np.storage_manager_server.repository;

/**
 * Enum koji predstavlja vrednosti po kojima radi "WHERE" uslov u S.O.
 * @author Milan
 */
public enum WhereConditionMode {
	/**
	 * sistemska operacija za domenski objekat po ID-ju
	 */
    BY_ID,
    /**
     * sistemska operacija za domenski objekat po nazivu
	 */
    BY_NAME,
    /**
     * sistemska operacija za domenski objekat po korisnickom imenu i lozinki 
	 * (samo za korisnike)
     */
    BY_USERNAME_PASSWORD
}
