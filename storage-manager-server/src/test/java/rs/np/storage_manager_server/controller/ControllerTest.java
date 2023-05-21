package rs.np.storage_manager_server.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import rs.np.storage_manager_common.domain.*;
import rs.np.storage_manager_common.domain.abstraction.*;
import rs.np.storage_manager_common.domain.abstraction.implementation.*;
import rs.np.storage_manager_common.domain.utility.DateParser;
import rs.np.storage_manager_server.property.PropertyFileOperation;
import rs.np.storage_manager_server.repository.DBBroker;

class ControllerTest {
	private static Controller controller;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		List<String> params = Arrays.asList("jdbc:mysql://localhost:3306/np_test",
				"root", "");
		PropertyFileOperation.writeDataToPropertyFile(params, "config/dbconfig.properties");
		controller = Controller.getInstance();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		List<String> params = Arrays.asList("jdbc:mysql://127.0.0.1:3306/np_production",
				"root", "");
		PropertyFileOperation.writeDataToPropertyFile(params, "config/dbconfig.properties");
		controller = null;
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Login test")
	void loginTest() {
		User user = new User(1, "Milan", "Stankovic",
				"stanmil_", "123abc");
		user.setMode(WhereClauseMode.BY_USERNAME_PASSWORD);
		try {
			User dbUser = controller.login("stanmil_", "123abc");
			assertEquals(dbUser, user);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an Exception");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("User not found test")
	void loginTestNotFound() {
		//korisnik koji ne postoji u bazi
		assertThrows(Exception.class,
				()->controller.login("pera123", "abcdgef"));
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Insert product test")
	void insertProductTest() {
		
		ExtraQueries.truncateTable("artikal");
		
		Product p = new Product(1,"productName",
				10.0, false, 20, ProductType.CarAccesory, new BigDecimal(1000.0));
		try {
			controller.insertProduct(p);
			List<Product> dbProducts = controller.getAllProducts(p);
			
			assertTrue(dbProducts.size() == 1);
			assertTrue(dbProducts.get(0).equals(p));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an Exception");
		} finally {
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all products")
	void getAllProductsTest() {
		
		ExtraQueries.truncateTable("artikal");
		
		Product p1 = new Product(1,"table T1",
				10.0, false, 20, ProductType.Furniture, new BigDecimal(1000.0));
		Product p2 = new Product(2,"laptop",
				10.0, true, 50, ProductType.Laptop, new BigDecimal(60000.0));
		
		try {
			controller.insertProduct(p1);
			controller.insertProduct(p2);
			
			List<Product> dbProducts = controller.getAllProducts();
			assertTrue(dbProducts.size() == 2);
			assertTrue(dbProducts.contains(p1));
			assertTrue(dbProducts.contains(p2));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} finally {
			ExtraQueries.truncateTable("artikal");
		}
	}

	
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all products by part of name")
	void selectAllProductsParamTest() {
		ExtraQueries.truncateTable("artikal");
		
		Product p1 = new Product(1,"table T1",
				10.0, false, 20, ProductType.Furniture, new BigDecimal(1000.0));
		Product p2 = new Product(2,"laptop",
				10.0, true, 50, ProductType.Laptop, new BigDecimal(60000.0));
		Product p3 = new Product(3,"table T2",
				10.0, false, 20, ProductType.Furniture, new BigDecimal(1000.0));
		Product p4 = new Product(4,"srafciger",
				10.0, true, 50, ProductType.Tool, new BigDecimal(60000.0));
		
		Product info = new Product();
		info.setProductName("table");
		try {
			controller.insertProduct(p1);
			controller.insertProduct(p2);
			controller.insertProduct(p3);
			controller.insertProduct(p4);
			List<Product> dbProducts =
					controller.getAllProducts(info);
			
			assertTrue(dbProducts.size() == 2);
			assertTrue(dbProducts.get(0).equals(p1) || dbProducts.get(1).equals(p1));
			assertTrue(dbProducts.get(0).equals(p3) || dbProducts.get(1).equals(p3));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} finally {
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Update product")
	void updateProductTest() {
		ExtraQueries.truncateTable("artikal");
		
		Product p1 = new Product(1,"table T1",
				10.0, false, 20, ProductType.Furniture, new BigDecimal(1000.0));

		try {
			controller.insertProduct(p1);
			p1 = controller.getAllProducts(p1).get(0);
		Product p2 = new Product(1,"table T1",
				10.0, true, 70, ProductType.Furniture, new BigDecimal(1000.0));
		p2.setID(p1.getID());
			controller.updateProduct(p2);
			Product pUpdated = controller.getAllProducts(p2).get(0);
			
			assertEquals(pUpdated, p1);
			assertEquals(70, pUpdated.getAmount());
			assertTrue(pUpdated.getFragile());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} finally {
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Delete product")
	void deleteProductTest() {
		ExtraQueries.truncateTable("artikal");
		
		Product p1 = new Product(15,"table T1",
				10.0, false, 20, ProductType.Furniture, new BigDecimal(1000.0));
	
		try {
			controller.insertProduct(p1);
			p1.setMode(WhereClauseMode.BY_NAME);
			controller.deleteProduct(p1);
			
			assertTrue(controller.getAllProducts(p1).size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} finally {
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all partners")
	void getAllPartnersNonParamTest() {
		Partner partner1 = new Partner(6, "firmaPart1", "adresaPart1");
		Partner partner2 = new Partner(7, "firmaPart2", "adresaPart2");
		Partner partner3 = new Partner(8, "firmaPart3", "adresaPart3");
		Partner partner4 = new Partner(9, "firmaPart4", "adresaPart4");
		Partner partner5 = new Partner(10, "firmaPart5", "adresaPart5");
	
		try {
			List<Partner> partners = controller.getAllPartners();
			assertTrue(partners.size() == 5);
			assertTrue(partners.contains(partner1));
			assertTrue(partners.contains(partner2));
			assertTrue(partners.contains(partner3));
			assertTrue(partners.contains(partner4));
			assertTrue(partners.contains(partner5));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all firms")
	void getAllFirmsNonParamTest() {
		Firm firm1 = new Firm(1, "firmName1", "firmAddress1");
		Firm firm2 = new Firm(2, "firmName2", "firmAddress2");
		Firm firm3 = new Firm(3, "firmName3", "firmAddress3");
		
		try {
			List<Firm> firms = controller.getAllFirms();
			assertTrue(firms.size() == 3);
			assertTrue(firms.contains(firm1));
			assertTrue(firms.contains(firm2));
			assertTrue(firms.contains(firm3));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		}
	}
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all natural persons")
	void getAllNaturalPersons() {
		Buyer buyer1 = new Buyer();
		buyer1.setID(1);
		Buyer buyer2 = new Buyer();
		buyer2.setID(2);
		Buyer buyer3 = new Buyer();
		buyer3.setID(3);
		NaturalPerson np1 = new NaturalPerson(1, buyer1 , "firstName1", "lastName1");
		NaturalPerson np2 = new NaturalPerson(2, buyer2 , "firstName2", "lastName2");
		NaturalPerson np3 = new NaturalPerson(3, buyer3 , "firstName3", "lastName3");
		
		try {
			List<NaturalPerson> naturalPersons = controller.getAllNaturalPersons();
			assertEquals(3, naturalPersons.size());
			assertTrue(naturalPersons.contains(np1));
			assertTrue(naturalPersons.contains(np2));
			assertTrue(naturalPersons.contains(np3));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all legal persons")
	void getAllLegalPersonsNoParamsTest() {
		Buyer buyer4 = new Buyer();
		buyer4.setID(4);
		Buyer buyer5 = new Buyer();
		buyer5.setID(5);
		try {
			LegalPerson lp1 = new LegalPerson(4, buyer4, "firmName1", sdf.parse("2020-01-01"));
			LegalPerson lp2 = new LegalPerson(6, buyer5, "firmName2", sdf.parse("2019-02-02"));
			
			List<LegalPerson> persons = controller.getAllLegalPersons();
			
			assertTrue(persons.size() == 2);
			assertTrue(persons.contains(lp1));
			assertTrue(persons.contains(lp2));
			
		} catch (ParseException e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} 
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Insert report test")
	void insertReportTest() {
		ExtraQueries.truncateTable("stavkaizvestaja");
		ExtraQueries.truncateTable("izvestaj");
		ExtraQueries.truncateTable("artikal");
		
		Product p1 = new Product(1,"productName1",10.0,false,10,ProductType.Art,new BigDecimal(10.0));
		Product p2 = new Product(2, "productName2", 20.0, false,20, ProductType.CarAccesory, new BigDecimal(20.0));
		try {
			ReportItem ri1 = new ReportItem(1, sdf.parse("2022-03-03"), 20.0, p1);
			ReportItem ri2 = new ReportItem(2, sdf.parse("2022-03-03"), 20.0, p2);
			
			Report report = new Report(sdf.parse("2022-03-03"), 90.0);
//			report.setReportItems(items);
			controller.insertProduct(p1);
			p1 = controller.getAllProducts(p1).get(0);
			controller.insertProduct(p2);
			p2 = controller.getAllProducts(p2).get(0);
			ri1.setProduct(p1);
			ri2.setProduct(p2);

			List<ReportItem> items = Arrays.asList(ri1, ri2);
			report.setReportItems(items);
			
			controller.insertReport(report);
			
			List<Report> reports = controller.getAllReports(report);
			assertEquals(reports.size(), 1);;
			assertTrue(reports.get(0).equals(report));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			fail("Test has thrown an exception");
		} finally {
			ExtraQueries.truncateTable("stavkaizvestaja");
			ExtraQueries.truncateTable("izvestaj");
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Insert note test")
	void insertNoteTest() {
		
		ExtraQueries.truncateTable("stavkaprijemnice");
		ExtraQueries.truncateTable("prijemnica");
		ExtraQueries.truncateTable("artikal");
		
		
		Product p1 = new Product(1,"productName1",10.0,false,10,ProductType.Art,new BigDecimal(10.0));
		Product p2 = new Product(2, "productName2", 20.0, false,20, ProductType.CarAccesory, new BigDecimal(20.0));
		
		Firm firm1 = new Firm(1, "firmName1", "firmAddress1");
		Partner partner1 = new Partner(6, "firmaPart1", "adresaPart1");
		GoodsReceivedNoteItem item1 = new GoodsReceivedNoteItem(1, 1, firm1, partner1, 2, p1);
		GoodsReceivedNoteItem item2 = new GoodsReceivedNoteItem(2, 1, firm1, partner1, 5, p2);
		try {
			System.out.println("aaaaaaaa");
			controller.insertProduct(p1);
			controller.insertProduct(p2);
		p1 = controller.getAllProducts(p1).get(0);
		p2 = controller.getAllProducts(p2).get(0);
		item1.setProduct(p1);
		item2.setProduct(p2);
		
		List<GoodsReceivedNoteItem> items = Arrays.asList(item1, item2);
		
			GoodsReceivedNote note = new GoodsReceivedNote(1, firm1, partner1, sdf.parse("2020-04-04"), sdf.parse("2025-01-04"), new BigDecimal(1200.0));
			note.setItems(items);
			
			controller.insertGoodsReceivedNote(note);
			List<GoodsReceivedNote> notes = ExtraQueries.selectAllNotes();
			List<GoodsReceivedNoteItem> dbItems = ExtraQueries.selectAllNoteItems();
			
			assertTrue(notes.size() == 1);
			assertTrue(notes.get(0).equals(note));
			assertTrue(dbItems.size() == 2);
			assertTrue(dbItems.get(0).getDocumentID() == item1.getDocumentID());
			assertTrue(dbItems.get(1).getDocumentID() == item2.getDocumentID());
			assertTrue(dbItems.get(0).getDocumentID() == item2.getDocumentID());
			assertTrue(dbItems.get(1).getDocumentID() == item1.getDocumentID());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} finally {
			
			ExtraQueries.truncateTable("stavkaprijemnice");
			ExtraQueries.truncateTable("prijemnica");
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Insert bill test")
	void insertNBillTest() {
		ExtraQueries.truncateTable("stavkaotpremnice");
		ExtraQueries.truncateTable("otpremnica");
		ExtraQueries.truncateTable("artikal");
		
		Product p1 = new Product(1,"productName1",10.0,false,10,ProductType.Art,new BigDecimal(10.0));
		Product p2 = new Product(2, "productName2", 20.0, false,20, ProductType.CarAccesory, new BigDecimal(20.0));
		
		Firm firm1 = new Firm(1, "firmName1", "firmAddress1");
		Buyer buyer = new Buyer();
		buyer.setID(1);
		NaturalPerson np = new NaturalPerson(1, buyer, "firstName", "lastName");
		BillOfLadingItem item1 = new BillOfLadingItem(1, 1, np ,firm1, 2, p1, WhereClauseMode.BY_ID);
		BillOfLadingItem item2 = new BillOfLadingItem(2, 1, np, firm1, 5, p2, WhereClauseMode.BY_NAME);
		
		try {
			controller.insertProduct(p1);
			controller.insertProduct(p2);
			p1 = controller.getAllProducts(p1).get(0);
			p2 = controller.getAllProducts(p2).get(0);
			item1.setProduct(p1);
			item2.setProduct(p2);
			List<BillOfLadingItem> items = Arrays.asList(item1, item2);
			
			BillOfLading bill = new BillOfLading(1, np, firm1, sdf.parse("2020-04-04"), sdf.parse("2025-01-04"), new BigDecimal(1200.0), WhereClauseMode.BY_ID);
			bill.setItems(items);
			
			controller.insertBillOfLading(bill);
			List<BillOfLading> bills = ExtraQueries.selectAllBills();
			
			List<BillOfLadingItem> dbItems = ExtraQueries.selectAllBillItems();
			
			
			assertTrue(bills.size() == 1);
			assertTrue(bills.get(0).equals(bill));
			assertTrue(dbItems.size() == 2);
			assertTrue(dbItems.get(0).getDocumentID() == item1.getDocumentID());
			assertTrue(dbItems.get(1).getDocumentID() == item2.getDocumentID());
			assertTrue(dbItems.get(0).getDocumentID() == item2.getDocumentID());
			assertTrue(dbItems.get(1).getDocumentID() == item1.getDocumentID());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		} finally {
			ExtraQueries.truncateTable("stavkaotpremnice");
			ExtraQueries.truncateTable("otpremnica");
			ExtraQueries.truncateTable("artikal");
		}
	}
	
	@Test
	@Timeout (value = 10, unit = TimeUnit.SECONDS)
	@DisplayName("Select all reports test")
	void getAllReportsTest() {
		//ovo i nije neophodno, test je dodat radi kompletnosti.
		//testom insertReportTest je provereno i selectAllReports.
		insertReportTest();
	}
	
	private static class ExtraQueries{
	private static List<GoodsReceivedNote> selectAllNotes(){
		Connection conn;
		try {
			conn = DBBroker.getInstance().establishConnection();
			String query = "SELECT * FROM PRIJEMNICA;";
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			List<GoodsReceivedNote> notes = new ArrayList<>();
			while(rs.next()) {
				Integer ID = rs.getInt("IDPrijemnice");
				Integer IDFirme = rs.getInt("IDFirme");
				Integer IDPartnera = rs.getInt("IDPartnera");
				Date issueDate = DateParser.sqlDateToUtilDate(rs.getDate("datumIzdavanjaP"));
				Date dueDate = DateParser.sqlDateToUtilDate(rs.getDate("datumValuteP"));
				BigDecimal totalPrice = new BigDecimal(rs.getDouble("totalnaCena"));
				
				Firm firm = new Firm();
				firm.setID(IDFirme);
				Partner partner = new Partner();
				partner.setID(IDPartnera);
				GoodsReceivedNote note = new GoodsReceivedNote(ID, firm, partner, issueDate, dueDate, totalPrice);
				notes.add(note);
			}
			
			return notes;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<GoodsReceivedNoteItem> selectAllNoteItems(){
		String query = "SELECT * FROM STAVKAPRIJEMNICE;";
		try {
			Connection conn = DBBroker.getInstance().establishConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			List<GoodsReceivedNoteItem> items = new ArrayList<>();
			while(rs.next()) {
				Integer ID = rs.getInt("IDStavkePrij");
				Integer NoteID = rs.getInt("IDPrijemnice");
				Integer FirmID = rs.getInt("IDFirme");
				Integer PartnerID = rs.getInt("IDPartnera");
				Integer amountAdded = rs.getInt("primljenaKolP");
				Integer ProductID = rs.getInt("sifraArtikla");
				
				Firm firm = new Firm();
				firm.setID(FirmID);
				Partner partner = new Partner();
				partner.setID(PartnerID);
				Product product = new Product();
				product.setID(ProductID);
				
				GoodsReceivedNoteItem item = new 
						GoodsReceivedNoteItem(ID, NoteID, firm, partner, amountAdded, product);
				items.add(item);
			}
			
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static List<BillOfLading> selectAllBills(){
		Connection conn;
		try {
			conn = DBBroker.getInstance().establishConnection();
			String query = "SELECT * FROM OTPREMNICA;";
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			List<BillOfLading> bills = new ArrayList<>();
			while(rs.next()) {
				Integer ID = rs.getInt("IDOtpremnice");
				Integer IDKupca = rs.getInt("IDKupca");
				Integer IDFirme = rs.getInt("IDFirme");
				Date issueDate = DateParser.sqlDateToUtilDate(rs.getDate("datumIzdavanja"));
				Date dueDate = DateParser.sqlDateToUtilDate(rs.getDate("datumValute"));
				BigDecimal totalPrice = new BigDecimal(rs.getDouble("ukupanIznos"));
				
				Firm firm = new Firm();
				firm.setID(IDFirme);
				Buyer buyer = new Buyer();
				buyer.setID(IDKupca);
				
				BillOfLading bill = new BillOfLading(ID, buyer, firm, issueDate, dueDate, totalPrice, WhereClauseMode.BY_ID);
				bills.add(bill);
			}
			
			return bills;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<BillOfLadingItem> selectAllBillItems(){
		String query = "SELECT * FROM STAVKAOTPREMNICE;";
		try {
			Connection conn = DBBroker.getInstance().establishConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			List<BillOfLadingItem> items = new ArrayList<>();
			while(rs.next()) {
				Integer ID = rs.getInt("IDStavke");
				Integer billID = rs.getInt("IDOtpremnice");
				Integer FirmID = rs.getInt("IDFirme");
				Integer buyerID = rs.getInt("IDKupca");
				Integer amountTaken = rs.getInt("izdataKolP");
				Integer ProductID = rs.getInt("sifraArtikla");
				
				Firm firm = new Firm();
				firm.setID(FirmID);
				Buyer buyer = new Buyer();
				buyer.setID(buyerID);
				Product product = new Product();
				product.setID(ProductID);
				
				BillOfLadingItem item = new 
						BillOfLadingItem(ID, billID, buyer, firm, amountTaken, product, WhereClauseMode.BY_ID);
				items.add(item);
			}
			
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void insertReportItem(ReportItem item) {
		String query = "INSERT INTO " + item.getTableName() +
                " (" + item.getColumnsWithoutID() + ") " +
               "VALUES " + item.getInsertValues() + ";";
		System.out.println(query);
		try {
			Connection conn = DBBroker.getInstance().establishConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("============================");
			System.out.println("           SUCCESS          ");
			System.out.println("============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void truncateTable(String tableName) {
		if(tableName == null || tableName.isBlank()) {
			throw new NullPointerException("Table name must not be empty");
		}
		String query2 = "DELETE FROM "+ tableName +";";
		
		try {
			Connection conn = DBBroker.getInstance().establishConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate(query2);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test has thrown an exception");
		}
	}
	}
}
