package tm.client.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 2554779308649225621L;
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtstandard;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField txtKa;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		setTitle("Transaction Manger (Client)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		
		Box verticalBox = Box.createVerticalBox();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JLabel lblAktuellesKonto = new JLabel("Saldo des aktuellen Kontos");
		lblAktuellesKonto.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtKa = new JTextField();
		txtKa.setHorizontalAlignment(SwingConstants.RIGHT);
		txtKa.setText("k.A.");
		txtKa.setEditable(false);
		txtKa.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(178, Short.MAX_VALUE)
					.addComponent(lblAktuellesKonto, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtKa, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtKa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAktuellesKonto, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
		);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Bankverbindung", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("Verbinde mit Bank und Konto");
		btnNewButton.setBounds(133, 120, 203, 30);
		panel_1.add(btnNewButton);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIp.setBounds(10, 11, 40, 20);
		panel_1.add(lblIp);
		
		textField = new JTextField();
		textField.setText("127.0.0.1");
		textField.setBounds(60, 11, 100, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPort.setBounds(10, 42, 40, 20);
		panel_1.add(lblPort);
		
		txtstandard = new JTextField();
		txtstandard.setText("(Standard)");
		txtstandard.setBounds(60, 42, 100, 20);
		panel_1.add(txtstandard);
		txtstandard.setColumns(10);
		
		JLabel lblKundennr = new JLabel("Kunde (Name, Vorname)");
		lblKundennr.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKundennr.setBounds(170, 11, 166, 20);
		panel_1.add(lblKundennr);
		
		textField_2 = new JTextField();
		textField_2.setBounds(346, 11, 100, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Kontonummer");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(170, 42, 166, 20);
		panel_1.add(lblNewLabel);
		
		textField_3 = new JTextField();
		textField_3.setBounds(346, 42, 100, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("BLZ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(170, 73, 166, 20);
		panel_1.add(lblNewLabel_2);
		
		textField_11 = new JTextField();
		textField_11.setBounds(346, 73, 100, 20);
		panel_1.add(textField_11);
		textField_11.setColumns(10);
		
		JButton btnAusfhren = new JButton("Ausf�hren...");
		btnAusfhren.setBounds(297, 115, 150, 30);
		JPanel panel = new JPanel();
		tabbedPane.addTab("Buchungsformular", null, panel, null);
		tabbedPane.setEnabledAt(1, true);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textArea, verticalBox, panel, btnAusfhren, panel_1, btnNewButton, lblIp, textField, lblPort, txtstandard, tabbedPane}));
		panel.setLayout(null);
		panel.add(btnAusfhren);
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton rdbtnberweisung = new JRadioButton("\u00DCberweisung");
		rdbtnberweisung.setSelected(true);
		rdbtnberweisung.setBounds(10, 134, 109, 23);
		panel.add(rdbtnberweisung);
		group.add(rdbtnberweisung);
		
		JRadioButton rdbtnAuszahlung = new JRadioButton("Auszahlung");
		rdbtnAuszahlung.setBounds(121, 108, 150, 23);
		panel.add(rdbtnAuszahlung);
		group.add(rdbtnAuszahlung);
		
		JRadioButton rdbtnUmbuchung = new JRadioButton("Umbuchung");
		rdbtnUmbuchung.setBounds(10, 108, 109, 23);
		panel.add(rdbtnUmbuchung);
		group.add(rdbtnUmbuchung);
		
		JRadioButton rdbtnEinzahlung = new JRadioButton("Einzahlung");
		rdbtnEinzahlung.setBounds(121, 134, 109, 23);
		panel.add(rdbtnEinzahlung);
		group.add(rdbtnEinzahlung);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(110, 8, 120, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblKontoinhaber = new JLabel("Kontoinhaber");
		lblKontoinhaber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKontoinhaber.setBounds(10, 8, 90, 20);
		panel.add(lblKontoinhaber);
		
		JLabel lblKontonummer = new JLabel("Kontonummer");
		lblKontonummer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKontonummer.setBounds(10, 39, 90, 20);
		panel.add(lblKontonummer);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setBounds(110, 39, 120, 20);
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblBetrag = new JLabel("Betrag");
		lblBetrag.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBetrag.setBounds(10, 70, 90, 20);
		panel.add(lblBetrag);
		
		textField_7 = new JTextField();
		textField_7.setBounds(110, 70, 120, 20);
		panel.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblEmpfnger = new JLabel("Empf\u00E4nger");
		lblEmpfnger.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmpfnger.setBounds(240, 8, 89, 20);
		panel.add(lblEmpfnger);
		
		textField_8 = new JTextField();
		textField_8.setBounds(339, 8, 120, 20);
		panel.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Kontonummer");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(240, 39, 89, 20);
		panel.add(lblNewLabel_1);
		
		textField_9 = new JTextField();
		textField_9.setBounds(339, 39, 120, 20);
		panel.add(textField_9);
		textField_9.setColumns(10);
		
		JLabel lblBlz = new JLabel("BLZ");
		lblBlz.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBlz.setBounds(240, 70, 89, 20);
		panel.add(lblBlz);
		
		textField_10 = new JTextField();
		textField_10.setBounds(339, 70, 120, 20);
		panel.add(textField_10);
		textField_10.setColumns(10);
		contentPane.setLayout(gl_contentPane);
	}
}
