package museumWin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame {
	static Connection conn;
	static CardLayout cardLayout = new CardLayout();
	static JPanel cardHolder = new JPanel(cardLayout);
	static JTextField textFieldFN;
	static JTextField textFieldLN;
	static JTextField textFieldEmail1;
	static JTextField textFieldPassword1;
	static JTextField textFieldTel;
	static JButton btnDataTable;
	static JTextField textFieldEmail2;
	static JTextField textFieldPassword2;
	private JTable tableData;
	private JCheckBox chckbxCustomers;
	private JCheckBox chckbxOrders;
	private JCheckBox chckbxMusAreas;
	private JCheckBox chckbxFeedbacks;
	private JCheckBox chckbxWorkers;
	private JCheckBox chckbxProfs;
	private JTable tableMyOrders;
	
	
	
	public Window() {
		JPanel mainPanel = new JPanel();
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 776, 0 };
		gbl_mainPanel.rowHeights = new int[] { 189, 0, 0 };
		gbl_mainPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);
		getContentPane().add(mainPanel, BorderLayout.WEST);
		
		// Start
		JPanel panelStart = new JPanel();
		panelStart.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Registration");
			}
		});
		panelStart.add(btnRegister);
		
		JLabel lblWelcome = new JLabel("Welcome to The National Museum of Georgia Database");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelStart.add(lblWelcome);
		
		JButton btnLogin1 = new JButton("Log in");
		
		btnDataTable = new JButton("Database");
		btnDataTable.setVisible(false);
		btnDataTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "List");
			}
		});
		
		JButton btnMyOrders = new JButton("My orders");
		btnMyOrders.setVisible(false);
		btnMyOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.updateMyOrdersTable(tableMyOrders);
				cardLayout.show(cardHolder, "MyOrders");
			}
		});
		
		JPanel panelOrder = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		
		JButton btnOrder = new JButton("Order");
		btnOrder.setVisible(false);
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.musAreaJRadioButtons(panelOrder, bg);
				cardLayout.show(cardHolder, "Order");
			}
		});
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setVisible(false);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DbWork.logout(btnRegister, btnLogin1, btnDataTable, btnMyOrders, btnOrder, btnLogout);
			}
		});
		GroupLayout gl_panelStart = new GroupLayout(panelStart);
		gl_panelStart
				.setHorizontalGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelStart.createSequentialGroup().addGap(152)
										.addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(182))
						.addGroup(Alignment.TRAILING, gl_panelStart.createSequentialGroup().addContainerGap()
								.addGroup(gl_panelStart.createParallelGroup(Alignment.TRAILING).addComponent(btnLogout)
										.addGroup(gl_panelStart.createSequentialGroup().addComponent(btnDataTable)
												.addGap(18).addComponent(btnMyOrders).addGap(18).addComponent(btnOrder)
												.addPreferredGap(ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
												.addComponent(btnRegister).addGap(18).addComponent(btnLogin1)))
								.addGap(44)));
		gl_panelStart.setVerticalGroup(gl_panelStart.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStart.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelStart.createParallelGroup(Alignment.BASELINE).addComponent(btnRegister)
								.addComponent(btnLogin1).addComponent(btnDataTable).addComponent(btnMyOrders)
								.addComponent(btnOrder))
						.addGap(49).addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE).addGap(72)
						.addComponent(btnLogout).addGap(40)));
		btnLogin1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Authorization");
			}
		});
		panelStart.add(btnLogin1);
		panelStart.setLayout(gl_panelStart);
		
		cardHolder.add(panelStart, "Start");
		
		// Registration
		JPanel panelReg = new JPanel();
		panelReg.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelReg.setLayout(null);
		
		JLabel lblRegister = new JLabel("Registration");
		lblRegister.setBounds(316, 20, 77, 19);
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelReg.add(lblRegister);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(192, 62, 68, 14);
		panelReg.add(lblFirstName);
		
		textFieldFN = new JTextField();
		textFieldFN.setBounds(265, 59, 195, 20);
		textFieldFN.setColumns(10);
		panelReg.add(textFieldFN);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setBounds(192, 87, 68, 14);
		panelReg.add(lblLastName);
		
		textFieldLN = new JTextField();
		textFieldLN.setBounds(265, 84, 195, 20);
		textFieldLN.setColumns(10);
		panelReg.add(textFieldLN);
		
		JLabel lblEmail1 = new JLabel("Email");
		lblEmail1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail1.setBounds(192, 112, 68, 14);
		panelReg.add(lblEmail1);
		
		textFieldEmail1 = new JTextField();
		textFieldEmail1.setBounds(265, 109, 195, 20);
		panelReg.add(textFieldEmail1);
		
		JLabel lblPassword1 = new JLabel("Password");
		lblPassword1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword1.setBounds(192, 137, 68, 14);
		panelReg.add(lblPassword1);
		
		textFieldPassword1 = new JTextField();
		textFieldPassword1.setBounds(265, 134, 195, 20);
		textFieldPassword1.setColumns(10);
		panelReg.add(textFieldPassword1);
		
		cardHolder.add(panelReg, "Registration");
		
		JButton btnLogIn2 = new JButton("Log in");
		btnLogIn2.setBounds(265, 187, 195, 23);
		btnLogIn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Authorization");
			}
		});
		
		JLabel lblTel = new JLabel("Telephone number");
		lblTel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTel.setBounds(143, 163, 117, 14);
		panelReg.add(lblTel);
		
		textFieldTel = new JTextField();
		textFieldTel.setBounds(265, 160, 195, 20);
		panelReg.add(textFieldTel);
		textFieldTel.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(465, 159, 77, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.registration(textFieldFN.getText(), textFieldLN.getText(), textFieldEmail1.getText(),
						textFieldPassword1.getText(), textFieldTel.getText(), cardLayout, cardHolder, btnRegister,
						btnLogin1, btnDataTable, btnMyOrders, btnOrder, btnLogout);
				DbWork.musAreaJRadioButtons(panelOrder, bg);
			}
		});
		panelReg.add(btnOk);
		
		JLabel lblAlreadyHave = new JLabel("Already have an account?");
		lblAlreadyHave.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlreadyHave.setBounds(91, 191, 169, 14);
		panelReg.add(lblAlreadyHave);
		panelReg.add(btnLogIn2);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(cardHolder, "Start");
			}
		});
		btnBack.setBounds(10, 11, 75, 23);
		panelReg.add(btnBack);
		
		// Authorization
		JPanel panelAuth = new JPanel();
		panelAuth.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelAuth.setLayout(null);
		
		JLabel lblAuthorization = new JLabel("Authorization");
		lblAuthorization.setBounds(312, 45, 85, 19);
		lblAuthorization.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelAuth.add(lblAuthorization);
		
		JLabel lblEmail2 = new JLabel("Email");
		lblEmail2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail2.setBounds(175, 83, 83, 14);
		panelAuth.add(lblEmail2);
		
		textFieldEmail2 = new JTextField();
		textFieldEmail2.setBounds(263, 80, 173, 20);
		textFieldEmail2.setColumns(10);
		panelAuth.add(textFieldEmail2);
		
		JLabel lblPassword2 = new JLabel("Password");
		lblPassword2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword2.setBounds(175, 109, 83, 14);
		panelAuth.add(lblPassword2);
		
		textFieldPassword2 = new JTextField();
		textFieldPassword2.setBounds(263, 106, 173, 20);
		textFieldPassword2.setColumns(10);
		panelAuth.add(textFieldPassword2);
		
		JButton btnOk_1 = new JButton("OK");
		btnOk_1.setBounds(441, 105, 69, 23);
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.authorization(textFieldEmail2.getText(), textFieldPassword2.getText(), cardLayout, cardHolder,
						btnRegister, btnLogin1, btnDataTable, btnMyOrders, btnOrder, btnLogout);
			}
		});
		panelAuth.add(btnOk_1);
		
		JLabel lblDoesNot = new JLabel("Don't have account yet?");
		lblDoesNot.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDoesNot.setBounds(97, 137, 161, 14);
		panelAuth.add(lblDoesNot);
		
		JButton btnReg = new JButton("Register");
		btnReg.setBounds(263, 133, 173, 23);
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Registration");
			}
		});
		panelAuth.add(btnReg);
		
		cardHolder.add(panelAuth, "Authorization");
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
			}
		});
		btnBack_1.setBounds(10, 11, 75, 23);
		panelAuth.add(btnBack_1);
		GridBagConstraints gbc_cardHolder = new GridBagConstraints();
		gbc_cardHolder.insets = new Insets(0, 0, 5, 0);
		gbc_cardHolder.anchor = GridBagConstraints.NORTHWEST;
		gbc_cardHolder.gridx = 0;
		gbc_cardHolder.gridy = 0;
		mainPanel.add(cardHolder, gbc_cardHolder);
		
		// Table
		JPanel panelTable = new JPanel();
		cardHolder.add(panelTable, "List");
		
		GridBagLayout gbl_panelTable = new GridBagLayout();
		gbl_panelTable.columnWidths = new int[] { 123, 380, 0 };
		gbl_panelTable.rowHeights = new int[] { 318, 0 };
		gbl_panelTable.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelTable.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelTable.setLayout(gbl_panelTable);
		
		JPanel panelChoose = new JPanel();
		panelChoose.setLayout(null);
		GridBagConstraints gbc_panelChoose = new GridBagConstraints();
		gbc_panelChoose.insets = new Insets(0, 0, 0, 5);
		gbc_panelChoose.fill = GridBagConstraints.BOTH;
		gbc_panelChoose.gridx = 0;
		gbc_panelChoose.gridy = 0;
		panelTable.add(panelChoose, gbc_panelChoose);
		
		JLabel lblTables = new JLabel("Tables");
		lblTables.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTables.setBounds(132, 36, 48, 14);
		panelChoose.add(lblTables);
		
		chckbxCustomers = new JCheckBox("Customers");
		chckbxCustomers.setBounds(16, 85, 97, 23);
		panelChoose.add(chckbxCustomers);
		
		chckbxOrders = new JCheckBox("Orders");
		chckbxOrders.setBounds(115, 85, 65, 23);
		panelChoose.add(chckbxOrders);
		
		chckbxMusAreas = new JCheckBox("Museum areas");
		chckbxMusAreas.setBounds(207, 85, 114, 23);
		panelChoose.add(chckbxMusAreas);
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.updateDataTable(tableData, chckbxCustomers, chckbxOrders, chckbxMusAreas, chckbxFeedbacks,
						chckbxWorkers, chckbxProfs);
			}
		});
		btnShow.setBounds(115, 184, 81, 23);
		panelChoose.add(btnShow);
		
		chckbxFeedbacks = new JCheckBox("Feedbacks");
		chckbxFeedbacks.setBounds(16, 130, 97, 23);
		panelChoose.add(chckbxFeedbacks);
		
		chckbxWorkers = new JCheckBox("Workers");
		chckbxWorkers.setBounds(115, 130, 87, 23);
		panelChoose.add(chckbxWorkers);
		
		chckbxProfs = new JCheckBox("Professions");
		chckbxProfs.setBounds(208, 130, 95, 23);
		panelChoose.add(chckbxProfs);
		
		JButton btnBack_2 = new JButton("Back");
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
			}
		});
		btnBack_2.setBounds(10, 11, 81, 23);
		panelChoose.add(btnBack_2);
		
		tableData = new JTable();
		tableData.setBounds(345, 0, 367, 245);
		tableData.setPreferredScrollableViewportSize(new Dimension(500, 50));
		tableData.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tableData);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		panelTable.add(scrollPane, gbc_scrollPane);
		
		cardHolder.add(panelOrder, "Order");
		panelOrder.setLayout(null);
		
		JLabel lblPleaseMakeAn = new JLabel("Please, make an order");
		lblPleaseMakeAn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPleaseMakeAn.setBounds(270, 40, 162, 17);
		panelOrder.add(lblPleaseMakeAn);
		
		JLabel lblMuseumAreas = new JLabel("Museum areas:");
		lblMuseumAreas.setBounds(49, 84, 109, 14);
		panelOrder.add(lblMuseumAreas);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.makeOrder(cardLayout, cardHolder, panelOrder);
			}
		});
		btnSubmit.setBounds(290, 189, 89, 23);
		panelOrder.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
			}
		});
		btnCancel.setBounds(10, 11, 75, 23);
		panelOrder.add(btnCancel);
		
		JPanel panelMyOrders = new JPanel();
		cardHolder.add(panelMyOrders, "MyOrders");
		GridBagLayout gbl_panelMyOrders = new GridBagLayout();
		gbl_panelMyOrders.columnWidths = new int[] { 114, 576 };
		gbl_panelMyOrders.rowHeights = new int[] { 279, 0, 0 };
		gbl_panelMyOrders.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelMyOrders.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panelMyOrders.setLayout(gbl_panelMyOrders);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		panelMyOrders.add(scrollPane_1, gbc_scrollPane_1);
		
		tableMyOrders = new JTable();
		scrollPane_1.setViewportView(tableMyOrders);
		tableMyOrders.setPreferredScrollableViewportSize(new Dimension(500, 50));
		tableMyOrders.setFillsViewportHeight(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panelMyOrders.add(panel, gbc_panel);
		
		JButton btnBack_3 = new JButton("Back");
		btnBack_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
			}
		});
		btnBack_3.setBounds(10, 11, 68, 23);
		panel.add(btnBack_3);
	}
}
