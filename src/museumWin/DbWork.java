package museumWin;

import java.awt.CardLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DbWork {
	static Connection conn;
	static int userID;
	
	
	
	public static void dbWork() {
		
	}
	
	
	
	public static void openConnection() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/museum?serverTimezone=UTC", "root", "usbw");
			if (conn != null) {
				System.out.println("Connection successful");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in openConnection: " + e);
		}
	}
	
	
	
	public static void closeConnection() {
		try {
			conn.close();
			System.out.println("Connection closed");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in closeConnection: " + e);
		}
	}
	
	
	
	public static void registration(String fn, String ln, String email, String password, String tel,
			CardLayout cardLayout, JPanel cardHolder, JButton btnRegister, JButton btnLogin1, JButton btnDataTable,
			JButton btnMyOrders, JButton btnOrder, JButton btnLogout) {
		openConnection();
		try {
			if ((fn.isEmpty()) || (ln.isEmpty()) || (email.isEmpty()) || (password.isEmpty()) || (tel.isEmpty())) {
				JOptionPane.showMessageDialog(null, "Please, fill all fields", "Error", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if (verifyEmail(email)) {
					Statement st = conn.createStatement();
					st.executeUpdate(
							"INSERT INTO customer (first_name, last_name, email, password, tel) VALUES ('" + fn + "', '"
									+ ln + "', '" + email + "', '" + password + "', '" + tel + "')",
							Statement.RETURN_GENERATED_KEYS);
					JOptionPane.showMessageDialog(null, "Account created", "Info", JOptionPane.PLAIN_MESSAGE);
					ResultSet rs = st.getGeneratedKeys();
					while (rs.next()) {
						userID = rs.getInt(1);
					}
//			        System.out.println(userID);
					btnRegister.setVisible(false);
					btnLogin1.setVisible(false);
					btnDataTable.setVisible(true);
					btnMyOrders.setVisible(true);
					btnOrder.setVisible(true);
					btnLogout.setVisible(true);
					cardLayout.show(cardHolder, "Order");
				}
				else {
					JOptionPane.showMessageDialog(null, "This email already exists, please, try again", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in registration: " + e);
		}
		closeConnection();
	}
	
	
	
	public static boolean verifyEmail(String email) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customer");
			while (rs.next()) {
				if (rs.getString("email").equals(email)) {
					return false;
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in verifyEmail: " + e);
		}
		return true;
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
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in deleteUser: " + e);
		}
	}
	
	
	
	public static void authorization(String email, String password, CardLayout cardLayout, JPanel cardHolder,
			JButton btnRegister, JButton btnLogin1, JButton btnDataTable, JButton btnMyOrders, JButton btnOrder,
			JButton btnLogout) {
		openConnection();
		try {
			if (verifyUser(email, password)) {
				JOptionPane.showMessageDialog(null, "Successfully logged in to system", "Info",
						JOptionPane.PLAIN_MESSAGE);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT customer.id FROM customer WHERE customer.email = '" + email
						+ "' " + "AND customer.password = '" + password + "'");
				while (rs.next()) {
					userID = rs.getInt(1);
				}
				btnRegister.setVisible(false);
				btnLogin1.setVisible(false);
				btnDataTable.setVisible(true);
				btnMyOrders.setVisible(true);
				btnOrder.setVisible(true);
				btnLogout.setVisible(true);
				cardLayout.show(cardHolder, "Start");
			}
			else {
				JOptionPane.showMessageDialog(null, "Failed to log in to system, please, try again", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in authorization: " + e);
		}
		closeConnection();
	}
	
	
	
	public static boolean verifyUser(String email, String password) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customer");
			if (email.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please, fill all fields", "Error", JOptionPane.WARNING_MESSAGE);
			}
			else {
				while (rs.next()) {
					if ((rs.getString("email").equals(email)) && (rs.getString("password").equals(password))) {
						return true;
					}
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in verifyEmail: " + e);
		}
		return false;
	}
	
	
	
	public static Object[][] constructDataTable(JCheckBox chckbxCustomers, JCheckBox chckbxOrders,
			JCheckBox chckbxMusAreas, JCheckBox chckbxFeedbacks, JCheckBox chckbxWorkers, JCheckBox chckbxProfs) {
		Object[][] listData = null;
		openConnection();
		HashMap<Integer, ArrayList<Object>> mapData = new HashMap<Integer, ArrayList<Object>>();
		try {
			ArrayList<String> allWHERE = new ArrayList<String>();
			allWHERE.add("feedback.order_ = order_.id");
			allWHERE.add("customer.id = order_.cust");
			allWHERE.add("order_.worker = worker.id");
			allWHERE.add("worker.musArea = museumarea.id");
			allWHERE.add("worker.prof = profession.id");
			ArrayList<Integer> checkBoxesWHERE = new ArrayList<Integer>();
			if (chckbxFeedbacks.isSelected()) {
				checkBoxesWHERE.add(0);
			}
			if (chckbxCustomers.isSelected()) {
				checkBoxesWHERE.add(1);
			}
			if (chckbxOrders.isSelected()) {
				checkBoxesWHERE.add(2);
			}
			if (chckbxWorkers.isSelected()) {
				checkBoxesWHERE.add(3);
			}
			if (chckbxMusAreas.isSelected()) {
				checkBoxesWHERE.add(4);
			}
			if (chckbxProfs.isSelected()) {
				checkBoxesWHERE.add(5);
			}
			if (chckbxMusAreas.isSelected() && chckbxProfs.isSelected() && !chckbxFeedbacks.isSelected()
					&& !chckbxCustomers.isSelected() && !chckbxOrders.isSelected() && !chckbxWorkers.isSelected()) {
				checkBoxesWHERE.clear();
				checkBoxesWHERE.add(3);
				checkBoxesWHERE.add(5);
			}
			if (chckbxFeedbacks.isSelected() && chckbxCustomers.isSelected() && !chckbxOrders.isSelected()
					&& !chckbxWorkers.isSelected() && !chckbxMusAreas.isSelected() && !chckbxProfs.isSelected()) {
				checkBoxesWHERE.clear();
				checkBoxesWHERE.add(0);
				checkBoxesWHERE.add(2);
			}
			ArrayList<String> arrWHERE = new ArrayList<String>();
			for (int i = checkBoxesWHERE.get(0); i < checkBoxesWHERE.get(checkBoxesWHERE.size() - 1); i++) {
				if (i == checkBoxesWHERE.get(0)) {
					arrWHERE.add("WHERE ");
					arrWHERE.add(allWHERE.get(i));
				}
				else {
					arrWHERE.add(" AND ");
					arrWHERE.add(allWHERE.get(i));
				}
			}
			StringBuilder sbWHERE = new StringBuilder();
			for (String wh : arrWHERE) {
				sbWHERE.append(wh);
			}
			ArrayList<String> allFROM = new ArrayList<String>();
			allFROM.add("feedback");
			allFROM.add("customer");
			allFROM.add("order_");
			allFROM.add("worker");
			allFROM.add("museumarea");
			allFROM.add("profession");
			ArrayList<String> arrFROM = new ArrayList<String>();
			for (int i = checkBoxesWHERE.get(0); i < checkBoxesWHERE.get(checkBoxesWHERE.size() - 1) + 1; i++) {
				if (i == checkBoxesWHERE.get(0)) {
					arrFROM.add("FROM ");
					arrFROM.add(allFROM.get(i));
				}
				else {
					arrFROM.add(", ");
					arrFROM.add(allFROM.get(i));
				}
			}
			StringBuilder sbFROM = new StringBuilder();
			for (String fr : arrFROM) {
				sbFROM.append(fr);
			}
			System.out.println("SELECT * " + sbFROM + " " + sbWHERE);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * " + sbFROM + " " + sbWHERE);
			int i = 0;
			while (rs.next()) {
				ArrayList<Object> row = new ArrayList<Object>();
				if (chckbxCustomers.isSelected()) {
					row.add(rs.getInt("customer.id"));
					row.add(rs.getString("customer.first_name"));
					row.add(rs.getString("customer.last_name"));
					row.add(rs.getString("customer.email"));
					row.add(rs.getString("customer.tel"));
				}
				if (chckbxOrders.isSelected()) {
					row.add(rs.getString("order_.price"));
					row.add(rs.getString("order_.date_"));
					row.add(rs.getString("order_.time"));
				}
				if (chckbxMusAreas.isSelected()) {
					row.add(rs.getString("museumarea.name"));
				}
				if (chckbxFeedbacks.isSelected()) {
					row.add(rs.getString("feedback.comment"));
					row.add(rs.getString("feedback.mark"));
				}
				if (chckbxWorkers.isSelected()) {
					row.add(rs.getString("worker.name"));
				}
				if (chckbxProfs.isSelected()) {
					row.add(rs.getString("profession.name"));
				}
				mapData.put(i, row);
				i++;
			}
		}
		catch (Exception e) {
			System.out.print("Error in showCustomers: " + e);
		}
		if (mapData.size() != 0) {
			listData = new Object[mapData.size()][mapData.get(0).size()];
			for (int x = 0; x < mapData.size(); x++) {
				for (int y = 0; y < mapData.get(0).size(); y++) {
					listData[x][y] = mapData.get(x).get(y);
				}
			}
		}
		closeConnection();
		return listData;
	}
	
	
	
	public static void tableMetaData() {
		try {
			openConnection();
			DatabaseMetaData dbmt = conn.getMetaData();
			ResultSet rs = dbmt.getExportedKeys(conn.getCatalog(), null, "worker");
			while (rs.next()) {
				String pkTableName = rs.getString("PKTABLE_NAME");
				String pkColumnName = rs.getString("PKCOLUMN_NAME");
				String fkTableName = rs.getString("FKTABLE_NAME");
				String fkColumnName = rs.getString("FKCOLUMN_NAME");
				int fkSequence = rs.getInt("KEY_SEQ");
				System.out.println("getExportedKeys(): pkTableName=" + pkTableName);
				System.out.println("getExportedKeys(): pkColumnName=" + pkColumnName);
				System.out.println("getExportedKeys(): fkTableName=" + fkTableName);
				System.out.println("getExportedKeys(): fkColumnName=" + fkColumnName);
				System.out.println("getExportedKeys(): fkSequence=" + fkSequence);
			}
			rs = dbmt.getImportedKeys(conn.getCatalog(), null, "worker");
			while (rs.next()) {
				String pkTableName = rs.getString("PKTABLE_NAME");
				String pkColumnName = rs.getString("PKCOLUMN_NAME");
				String fkTableName = rs.getString("FKTABLE_NAME");
				String fkColumnName = rs.getString("FKCOLUMN_NAME");
				int fkSequence = rs.getInt("KEY_SEQ");
				System.out.println("getImportedKeys(): pkTableName=" + pkTableName);
				System.out.println("getImportedKeys(): pkColumnName=" + pkColumnName);
				System.out.println("getImportedKeys(): fkTableName=" + fkTableName);
				System.out.println("getImportedKeys(): fkColumnName=" + fkColumnName);
				System.out.println("getImportedKeys(): fkSequence=" + fkSequence);
			}
			closeConnection();
		}
		catch (SQLException e) {
			closeConnection();
			e.printStackTrace();
		}
	}
	
	
	
	public static String[] tableDataHeaders(JCheckBox chckbxCustomers, JCheckBox chckbxOrders, JCheckBox chckbxMusAreas,
			JCheckBox chckbxFeedbacks, JCheckBox chckbxWorkers, JCheckBox chckbxProfs) {
		ArrayList<String> arrHeaders = new ArrayList<String>();
		if (chckbxCustomers.isSelected()) {
			arrHeaders.add("ID");
			arrHeaders.add("First name");
			arrHeaders.add("Last name");
			arrHeaders.add("Email");
			arrHeaders.add("Telephone");
		}
		if (chckbxOrders.isSelected()) {
			arrHeaders.add("Price");
			arrHeaders.add("Date");
			arrHeaders.add("Time");
		}
		if (chckbxMusAreas.isSelected()) {
			arrHeaders.add("Area");
		}
		if (chckbxFeedbacks.isSelected()) {
			arrHeaders.add("Comment");
			arrHeaders.add("Rating");
		}
		if (chckbxWorkers.isSelected()) {
			arrHeaders.add("Worker's name");
		}
		if (chckbxProfs.isSelected()) {
			arrHeaders.add("Profession");
		}
		String[] strHeaders = new String[arrHeaders.size()];
		for (int i = 0; i < arrHeaders.size(); i++) {
			strHeaders[i] = arrHeaders.get(i);
		}
		return strHeaders;
	}
	
	
	
	public static void updateDataTable(JTable tableData, JCheckBox chckbxCustomers, JCheckBox chckbxOrders,
			JCheckBox chckbxMusAreas, JCheckBox chckbxFeedbacks, JCheckBox chckbxWorkers, JCheckBox chckbxProfs) {
		DefaultTableModel dtm = (DefaultTableModel)tableData.getModel();
		if (constructDataTable(chckbxCustomers, chckbxOrders, chckbxMusAreas, chckbxFeedbacks, chckbxWorkers,
				chckbxProfs) == null) {
			dtm.setRowCount(0);
			dtm.setColumnCount(0);
		}
		else {
			dtm.setDataVector(
					constructDataTable(chckbxCustomers, chckbxOrders, chckbxMusAreas, chckbxFeedbacks, chckbxWorkers,
							chckbxProfs),
					tableDataHeaders(chckbxCustomers, chckbxOrders, chckbxMusAreas, chckbxFeedbacks, chckbxWorkers,
							chckbxProfs));
		}
	}
	
	
	
	public static void musAreaJRadioButtons(JPanel panelOrder, ButtonGroup bg) {
		try {
			openConnection();
			Statement select = conn.createStatement();
			ResultSet rs = select.executeQuery("SELECT museumarea.id, museumarea.name FROM museumarea");
			int distance1 = 39;
			int distance2 = 125;
			int count = 0;
			while (rs.next()) {
				if (count == 5) {
					distance1 = 39;
					distance2 += 40;
				}
				JRadioButton rb = new JRadioButton(rs.getString("museumarea.name"));
				rb.setBounds(distance1, distance2, 109, 23);
				bg.add(rb);
				panelOrder.add(rb);
				distance1 += 114;
				count++;
			}
			closeConnection();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in musAreaJRadioButtons: " + e);
		}
	}
	
	
	
	public static void makeOrder(CardLayout cardLayout, JPanel cardHolder, JPanel panelOrder) {
		openConnection();
		HashMap<Integer, JRadioButton> mapRadioButtons = new HashMap<Integer, JRadioButton>();
		int i = 1;
		for (Component c : panelOrder.getComponents()) {
			if (c instanceof JRadioButton) {
				try {
					Statement st = conn.createStatement();
					System.out.println(
							"SELECT id FROM museumarea WHERE museumarea.name = '" + ((JRadioButton)c).getText() + "'");
					ResultSet rs = st.executeQuery(
							"SELECT id FROM museumarea WHERE museumarea.name = '" + ((JRadioButton)c).getText() + "'");
					while (rs.next()) {
						System.out.println(rs.getInt("id") + "; " + ((JRadioButton)c).getText());
						mapRadioButtons.put(rs.getInt("id"), (JRadioButton)c);
						i++;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			Statement st = conn.createStatement();
			ArrayList<Integer> IDs = new ArrayList<Integer>();
			Iterator<Entry<Integer, JRadioButton>> it = mapRadioButtons.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, JRadioButton> pair = (Map.Entry<Integer, JRadioButton>)it.next();
//				System.out.println(pair.getKey() + " = " + pair.getValue());
				if (((JRadioButton)pair.getValue()).isSelected()) {
					System.out.println("SELECT id FROM worker WHERE worker.musArea = " + (int)pair.getKey());
					ResultSet rs = st
							.executeQuery("SELECT id FROM worker WHERE worker.musArea = " + (int)pair.getKey());
					while (rs.next()) {
						IDs.add(rs.getInt("id"));
					}
					String areaName = ((JRadioButton)pair.getValue()).getText();
					st.executeUpdate(
							"INSERT INTO order_ (cust, worker) VALUES ('" + userID + "', '" + getRandom(IDs) + "')");
					JOptionPane.showMessageDialog(null,
							"You've ordered a visit to " + areaName + ". We'll be waiting for you!", "Info",
							JOptionPane.PLAIN_MESSAGE);
					cardLayout.show(cardHolder, "Start");
					it.remove();
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in makeOrder: ");
			e.printStackTrace();
		}
		closeConnection();
	}
	
	
	
	public static int getRandom(ArrayList<Integer> IDs) {
		int rand = new Random().nextInt(IDs.size());
		return IDs.get(rand);
	}
	
	
	
	public static Object[][] constructMyOrdersTable() {
		openConnection();
		Object[][] arrData = null;
		HashMap<Integer, ArrayList<Object>> mapMyOrders = new HashMap<Integer, ArrayList<Object>>();
		ArrayList<Object> listMyOrders = null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customer, order_, worker, museumarea, profession WHERE "
					+ "customer.id = order_.cust AND order_.worker = worker.id AND worker.musArea = museumarea.id AND worker.prof = profession.id AND customer.id = "
					+ userID);
			int i = 0;
			while (rs.next()) {
				listMyOrders = new ArrayList<Object>();
				listMyOrders.add(rs.getString("museumarea.name"));
				listMyOrders.add(rs.getString("order_.price"));
				listMyOrders.add(rs.getString("order_.date_"));
				listMyOrders.add(rs.getString("order_.time"));
				listMyOrders.add(rs.getString("worker.name"));
				listMyOrders.add(rs.getString("profession.name"));
				mapMyOrders.put(i, listMyOrders);
				i++;
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops! Something went wrong... Please, try again later", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in constructMyOrders: " + e);
		}
		if (mapMyOrders.size() > 0) {
			arrData = new Object[mapMyOrders.size()][mapMyOrders.get(0).size()];
			for (int x = 0; x < mapMyOrders.size(); x++) {
				for (int y = 0; y < mapMyOrders.get(0).size(); y++) {
					arrData[x][y] = mapMyOrders.get(x).get(y);
				}
			}
		}
		closeConnection();
		return arrData;
	}
	
	
	
	public static String[] tableMyOrdersHeaders() {
		String[] arrHeaders = new String[6];
		arrHeaders[0] = "Museum area";
		arrHeaders[1] = "Price";
		arrHeaders[2] = "Date";
		arrHeaders[3] = "Time";
		arrHeaders[4] = "Worker";
		arrHeaders[5] = "Profession";
		return arrHeaders;
	}
	
	
	
	public static void updateMyOrdersTable(JTable tableMyOrders) {
		DefaultTableModel dtm = (DefaultTableModel)tableMyOrders.getModel();
		if (constructMyOrdersTable() == null) {
			dtm.setRowCount(0);
			dtm.setColumnCount(0);
		}
		else {
			dtm.setDataVector(constructMyOrdersTable(), tableMyOrdersHeaders());
		}
	}
	
	
	
	public static void logout(JButton btnRegister, JButton btnLogin1, JButton btnDataTable, JButton btnMyOrders,
			JButton btnOrder, JButton btnLogout) {
		btnRegister.setVisible(true);
		btnLogin1.setVisible(true);
		btnDataTable.setVisible(false);
		btnMyOrders.setVisible(false);
		btnOrder.setVisible(false);
		btnLogout.setVisible(false);
	}
}