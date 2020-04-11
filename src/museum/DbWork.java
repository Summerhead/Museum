package museum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class DbWork {
	static Connection conn = null;
	
	public static void dbWork() {
		openConnection();
//		printAllCustomers();
//		addUser("ij@gmail.com", "4tgf", "Jade", "Fox");
//		registration();
//		deleteUser();
//		printAllCustomers();
//		select1();
//		select2();
//		select3();
//		select4();
//		select5();
//		select6();
//		select7();
//		select8();
//		select9();
//		select10();
//		update1();
//		update2();
//		update3();
		closeConnection();
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		}
		catch (Exception e) {
			System.out.println("Error in closeConnection: " + e);
		}
	}

	public static void openConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/museum?serverTimezone=UTC", "root", "usbw");
			if (conn != null) {
				System.out.println("Connection successful");
			}
		}
		catch (Exception e) {
			System.out.println("Error in openConnection: " + e);
		}
	}
	
	public static void printAllCustomers() {
//		try {
//			Statement st = conn.createStatement();
//			ResultSet rs = st.executeQuery("SELECT * FROM customer");
//			while (rs.next()) {
//				String name = rs.getString("name");
//				String email = rs.getString("email");
//				String tel = rs.getString("tel");
//				System.out.format("%s %s %s\n", name, email, tel);
//			}
//		}
//		catch (Exception e){
//			System.out.println("Error in printAllUsers: " + e);
//		}
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customer");
			while (rs.next()) {
				builder.append(rs.getString("id")).append(" | ");
				builder.append(rs.getString("first_name")).append(" ");
				builder.append(rs.getString("last_name")).append(" | ");
				builder.append(rs.getString("email")).append(" | ");
				builder.append(rs.getString("password")).append(" | ");
				builder.append(rs.getString("tel")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
//	public static void addUser(String email, String password, String fn, String ln) throws SQLException {
//		Statement st = conn.createStatement();
//		if (verifyEmail(email) == true) {
//			st.executeUpdate("INSERT INTO user (email, password, firstName, lastName) VALUES ('"
//		+email+"', '"+password+"', '"+fn+"', '"+ln+"')");
//		}
//		else {
//			System.out.println("Error in addUser: User's email already exists");
//		}
//	}
	
	public static boolean verifyEmail(String email) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM costumer");
			while (rs.next()) {
				if (rs.getString("email").equals(email)) {
					return false;
				}
			}
		}
		catch (Exception e) {
			System.out.print("Error in verifyEmail: " + e);
		}
		return true;
	}
	
	public static void registration() {
		try {
			Statement st = conn.createStatement();
			Scanner in = new Scanner(System.in);
			System.out.print("Enter name: ");
			String name = in.next();
			System.out.print("Enter email: ");
			String email = in.next();
			while (verifyEmail(email) == false) {
				System.out.println("This email already exists, please try again: ");
				email = in.next();
			}
			System.out.print("Enter password: ");
			String password = in.next();
			System.out.print("Enter tel: ");
			String tel = in.next();
			
			in.close();
			st.executeUpdate("INSERT INTO customer (name, email, password, tel) VALUES ('"
			+name+"', '"+email+"','"+password+"', '"+tel+"')");
		}
		catch (Exception e) {
			System.out.print("Error in registration: " + e);
		}		
	}
	
	public static void deleteUser() {
		try {
			Statement selectSt = conn.createStatement();
			Scanner in = new Scanner(System.in);
			System.out.print("Enter user's ID to delete: ");
			int idToDel = in.nextInt();
			ResultSet selectRs = selectSt.executeQuery("SELECT * FROM customer");
			while (selectRs.next()) {
				int id = selectRs.getInt("id");
				if (id == idToDel) {
					String name = selectRs.getString("name");
					String email = selectRs.getString("email");
					String password = selectRs.getString("password");
					String tel = selectRs.getString("tel");
					System.out.println("Are you sure you want to delete the following user? y/n: ");
					System.out.format("%s %s %s %s\n", name, email, password, tel);
					String answer = in.next();
					if (answer.equalsIgnoreCase("y")) {
						Statement deleteSt = conn.createStatement();
						deleteSt.executeUpdate("DELETE FROM user WHERE id = " + idToDel);
					}
					break;
				}
			}
			in.close();
		}
		catch (Exception e) {
			System.out.println("Error in deleteUser: " + e);
		}
	}
	
	public static void select1() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT Customer.first_name, Customer.last_name, Customer.email, Customer.tel FROM customer");
			while (rs.next()) {
				builder.append(rs.getString("Customer.first_name")).append(" ");
				builder.append(rs.getString("Customer.last_name")).append(" | ");
				builder.append(rs.getString("Customer.email")).append(" | ");
				builder.append(rs.getString("Customer.tel")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select2() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT Customer.first_name, Customer.last_name, MuseumArea.name FROM Customer, Order_, Worker, MuseumArea \r\n" + 
					"WHERE Order_.cust=Customer.id AND Order_.worker=Worker.id AND Worker.musArea=MuseumArea.id");
			while (rs.next()) {
				builder.append(rs.getString("Customer.first_name")).append(" ");
				builder.append(rs.getString("Customer.last_name")).append(" | ");
				builder.append(rs.getString("MuseumArea.name")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select3() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT Customer.first_name, Customer.last_name, Customer.email, MuseumArea.name, Order_.price FROM Customer, MuseumArea, Order_, Worker \r\n" + 
					"WHERE Order_.cust=Customer.id AND Order_.worker=Worker.id AND Worker.musArea=MuseumArea.id AND Order_.price=30");
			while (rs.next()) {
				builder.append(rs.getString("Customer.first_name")).append(" ");
				builder.append(rs.getString("Customer.last_name")).append(" | ");
				builder.append(rs.getString("Customer.email")).append(" | ");
				builder.append(rs.getString("MuseumArea.name")).append(" | ");
				builder.append(rs.getString("Order_.price")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select4() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT Customer.first_name, Customer.last_name, Order_.price FROM Customer, Order_ WHERE Order_.cust=Customer.id ORDER BY Order_.price DESC");
			while (rs.next()) {
				builder.append(rs.getString("Customer.first_name")).append(" ");
				builder.append(rs.getString("Customer.last_name")).append(" | ");
				builder.append(rs.getString("Order_.price")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select5() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(Worker.id) AS count FROM Worker, MuseumArea WHERE Worker.musArea=MuseumArea.id AND (Worker.musArea=1 OR Worker.musArea=2)");
			while (rs.next()) {
				builder.append(rs.getString("count"));
			}
			
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select6() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MuseumArea.name, Customer.first_name, Customer.last_name, Worker.name, Profession.name, Order_.time FROM MuseumArea, Customer, Worker, Order_, Profession \r\n" + 
					"WHERE Order_.cust=Customer.id AND Order_.worker=Worker.id AND Worker.musArea=MuseumArea.id AND Worker.prof=Profession.id AND Order_.time<120");
			while (rs.next()) {
				builder.append(rs.getString("MuseumArea.name")).append(" | ");
				builder.append(rs.getString("Customer.first_name")).append(" ");
				builder.append(rs.getString("Customer.last_name")).append(" | ");
				builder.append(rs.getString("Worker.name")).append(" | ");
				builder.append(rs.getString("Profession.name")).append(" | ");
				builder.append(rs.getString("Order_.time")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select7() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MuseumArea.id, MuseumArea.name, (SELECT COUNT(*) FROM Worker WHERE Worker.musArea=MuseumArea.id) AS count FROM MuseumArea ORDER BY count DESC LIMIT 1");
			while (rs.next()) {
				builder.append(rs.getString("MuseumArea.id")).append(" | ");
				builder.append(rs.getString("MuseumArea.name")).append(" | ");
				builder.append(rs.getString("count")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select8() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT Worker.name, Order_.price, MuseumArea.id, MuseumArea.name FROM Worker, MuseumArea, Order_ \r\n" + 
					"WHERE Order_.worker=Worker.id AND Worker.musArea=MuseumArea.id AND Order_.price>25 AND (MuseumArea.id=4 OR MuseumArea.id=5)");
			while (rs.next()) {
				builder.append(rs.getString("Worker.name")).append(" | ");
				builder.append(rs.getString("Order_.price")).append(" | ");
				builder.append(rs.getString("MuseumArea.id")).append(" | ");
				builder.append(rs.getString("MuseumArea.name")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select9() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT Worker.name, Profession.name, Feedback.comment, Feedback.mark FROM Profession, Feedback, Order_, Worker \r\n" + 
					"WHERE Worker.prof=Profession.id AND Feedback.order_=Order_.id AND Order_.worker=Worker.id AND Feedback.mark=(SELECT MAX(Feedback.mark) FROM Feedback)");
			while (rs.next()) {
				builder.append(rs.getString("Worker.name")).append(" | ");
				builder.append(rs.getString("Profession.name")).append(" | ");
				builder.append(rs.getString("Feedback.comment")).append(" | ");
				builder.append(rs.getString("Feedback.mark")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void select10() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT AVG(Feedback.mark) AS av FROM Feedback");
			while (rs.next()) {
				builder.append(rs.getString("av"));
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void update1() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO customer (first_name, last_name, email, password, tel) VALUES (\"Jade\", \"Fox\", \"ij@gmail.com\", \"4tgf\", \"23-04-50\")");
			ResultSet rs = st.executeQuery("SELECT * FROM customer");
			while (rs.next()) {
				builder.append(rs.getString("id")).append(" | ");
				builder.append(rs.getString("first_name")).append(" ");
				builder.append(rs.getString("last_name")).append(" | ");
				builder.append(rs.getString("email")).append(" | ");
				builder.append(rs.getString("password")).append(" | ");
				builder.append(rs.getString("tel")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void update2() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM customer WHERE customer.id=27");
			ResultSet rs = st.executeQuery("SELECT * FROM customer");
			while (rs.next()) {
				builder.append(rs.getString("id")).append(" | ");
				builder.append(rs.getString("first_name")).append(" ");
				builder.append(rs.getString("last_name")).append(" | ");
				builder.append(rs.getString("email")).append(" | ");
				builder.append(rs.getString("password")).append(" | ");
				builder.append(rs.getString("tel")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void update3() {
		StringBuilder builder = new StringBuilder(0);
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("UPDATE customer SET email=\"whell@gmail.com\" WHERE customer.id=12");
			ResultSet rs = st.executeQuery("SELECT * FROM customer");
			while (rs.next()) {
				builder.append(rs.getString("id")).append(" | ");
				builder.append(rs.getString("first_name")).append(" ");
				builder.append(rs.getString("last_name")).append(" | ");
				builder.append(rs.getString("email")).append(" | ");
				builder.append(rs.getString("password")).append(" | ");
				builder.append(rs.getString("tel")).append("\n");
			}
		}
		catch (Exception ex) {
			System.out.println("Error in printAllUsers: " + ex);
		}
		JOptionPane.showMessageDialog(null, builder.toString(), "List of customers", JOptionPane.PLAIN_MESSAGE);
	}
}