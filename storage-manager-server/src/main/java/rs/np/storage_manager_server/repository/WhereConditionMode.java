//package repository;
package rs.np.storage_manager_server.repository;

/**
 * Enum koji predstavlja vrednosti po kojima radi "WHERE" uslov u S.O.
 * @author Milan
 */
public enum WhereConditionMode {
	/**
	 * BY_ID - sistemska operacija za domenski objekat po ID-ju
	 * BY_NAME - sistemska operacija za domenski objekat po nazivu
	 * BY_USERNAME_PASSWORD - sistemska operacija za domenski objekat po korisnickom imenu i lozinki 
	 * (samo za korisnike)
	 */
    BY_ID, BY_NAME, BY_USERNAME_PASSWORD
}
