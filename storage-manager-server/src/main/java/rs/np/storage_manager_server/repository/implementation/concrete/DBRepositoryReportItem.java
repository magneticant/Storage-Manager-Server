package rs.np.storage_manager_server.repository.implementation.concrete;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rs.np.storage_manager_common.domain.Product;
import rs.np.storage_manager_common.domain.ProductType;
import rs.np.storage_manager_common.domain.ReportItem;
import rs.np.storage_manager_common.domain.utility.DateParser;
import rs.np.storage_manager_server.repository.DBBroker;
import rs.np.storage_manager_server.repository.DBRepository;

/**
 * Klasa koja predstavlja repozitorijum za stavku izvestaja, implementira interfejs DBRepository 
 * i prosledjuje {@link ReportItem} kao genericki parametar
 * @author Milan
 */
public class DBRepositoryReportItem implements DBRepository<ReportItem>{

	@Override
	public List<ReportItem> selectAll() throws Exception {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM stavkaizvestaja s "
					 + "INNER JOIN artikal a ON a.sifraArtikla = s.sifraArtikla;";
		List<ReportItem> items = new ArrayList<>();
		try {
			Connection conn = DBBroker.getInstance().establishConnection();
			Statement st = conn.createStatement();
			var rs = st.executeQuery(query);
			
			while(rs.next()) {
				ReportItem item = new ReportItem();
				item.setID(rs.getInt("s.sifraStavke"));
				item.setProductCapacity(rs.getDouble("s.kapacitetArt"));
				item.setReportID(DateParser.sqlDateToUtilDate(rs.getDate("s.datumIzvestaja")));
				item.setTotalAvailableCapacity(rs.getInt("s.ukupanKapDostupno"));
				
				Product product = new Product();
                product.setID(rs.getInt("a.sifraArtikla"));
                product.setProductName(rs.getString("a.imeArtikla"));
                product.setWeight(rs.getDouble("a.gramaza"));
                product.setFragile(rs.getBoolean("a.lomljiv"));
                product.setAmount(rs.getInt("a.kolicina"));
                product.setType(ProductType.valueOf(rs.getString("a.tip")));
                product.setPrice(rs.getBigDecimal("a.cena"));
                
                item.setProduct(product);
                
                items.add(item);
			}
			st.close();
			conn.close();
			
			return items;
		}catch(SQLException ex) {
			System.out.println("DBRepositoryReportItem: Something went wrong while fetching all reportItems.");
            Logger.getLogger(DBRepositoryUser.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Report items cannot be fetched.");
		}
	}

	@Override
	public void insert(ReportItem parameter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ReportItem parameter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ReportItem parameter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReportItem> selectAll(ReportItem parameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
