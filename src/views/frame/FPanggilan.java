package views.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXDatePicker;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import config.Database;
import config.ImagePanel;
import daoimpl.DbDaoImpl;
import daoimpl.PanggilanDaoImpl;
import daoimpl.TahunDaoImpl;
import entities.Db;
import entities.Panggilan;
import views.panel.PSidang;

public class FPanggilan {

	public JFrame frame;
	public static FPanggilan window;
	private JTextField txtNamaPp;
	private JTextField txtJurusita;
	private JTextField txtBiaya;
	private JComboBox<String> cmbKecamatan;
	private String perkaraId;
	ButtonGroup btnGroup;
	private JTextField txtPerkaraNo;
	private JComboBox<String> cmbJenis;
	private JLabel lblTglPutus;
	private JRadioButton rdbtnPenggugat;
	private JRadioButton rdbtnTergugat;
	private JXDatePicker dpTglSidang;
	private JXDatePicker dpTglPutus;
	private JLabel lblTglSidang;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static boolean edit = false;
	public static String panggilanId;
	private DbDaoImpl daoImpl;
	private Db server;
	Session session = null;
	Connection con = null;
	boolean isServer = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new FPanggilan();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FPanggilan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				SetKecamatan();
				daoImpl = new DbDaoImpl();
				server = daoImpl.getActivated();
				if (server.getJenis().equals("Local")) {
					isServer = false;
					try {
						String url="jdbc:mysql://" + server.getHost() + "/" + server.getDbName() + "?autoReconnect=true&useSSL=false";
						Class.forName("com.mysql.jdbc.Driver");
						con =  DriverManager.getConnection(url, server.getUser(), server.getPassword());
					} catch (Exception er){
						JOptionPane.showMessageDialog(null, "Koneksi Database Gagal " + er);
					}
				}
				else if (server.getJenis().equals("Server")) {
					isServer = true;
					//SSL Tunnerl settings
			        String host = server.getHost();  //Remote host to connect to
			        String user = server.getUser();					//Remote shell username
			        String password = server.getPassword();	//Remote shell password
			        int lport = 56560; 						//Local port to create
			        int rport = 3306; 						//Destination port
			        String rhost ="127.0.0.1";				//Destination address

			        //MySQL Connection settings
			        String dbuserName = server.getDbUser();			//mysql username
			        String dbpassword = server.getDbPassword();			//mysql password
			        String url = "jdbc:mysql://127.0.0.1:"+lport+"/" + server.getDbName(); //connect to local end of SSL tunnel
			        String driverName ="com.mysql.jdbc.Driver";
					try{
			            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
			            java.util.Properties config = new java.util.Properties();
			            config.put("StrictHostKeyChecking", "no");
			            JSch jsch = new JSch();
			            session=jsch.getSession(user, host, server.getPort());
			            session.setPassword(password);
			            session.setConfig(config);
			            session.connect();
			            System.out.println("-- SSH connection successful");
			            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
			            System.out.println("-- localhost:"+assinged_port+" tunneled to "+host+":"+rport);

			            //mysql database connectivity
			            Class.forName(driverName);
			            System.out.println ("-- Mysql connect to " +url +" " +dbuserName +" " +dbpassword);
			            con = DriverManager.getConnection (url, dbuserName, dbpassword);
			        }catch(Exception er){
			            JOptionPane.showMessageDialog(null, er);
			        }
				}
				if (edit) {
					txtPerkaraNo.setEditable(false);
				}
			}
		});
		frame.setBounds(100, 100, 823, 469);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 257, 100, 13, 50, 157, 143, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 20, 0, 0, 11, 0, 0, 7, 0, 0, 0, 32, 11, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel_2 = new JLabel("<html><center>Panggilan (Sidang 1/</center><center>Tunda/Ikrar/PBT/Mediasi/Kontra)</center></html>");
		lblNewLabel_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridwidth = 8;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 15;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_4 = new JLabel("Jenis Panggilan");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		cmbJenis = new JComboBox<>();
		cmbJenis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbJenis.getSelectedIndex() > 0) {
					if (cmbJenis.getSelectedIndex() == 2) {
						lblTglPutus.setVisible(false);
						dpTglPutus.setVisible(false);
						lblTglSidang.setVisible(true);
						dpTglSidang.setVisible(true);

						lblTglSidang.setText("Tgl sidang selanjutnya");
					} else if (cmbJenis.getSelectedIndex() == 4 || cmbJenis.getSelectedIndex() == 3) {
						lblTglPutus.setVisible(true);
						dpTglPutus.setVisible(true);
						lblTglSidang.setVisible(false);
						dpTglSidang.setVisible(false);

						lblTglSidang.setText("Tgl sidang");
					} else {
						lblTglPutus.setVisible(false);
						dpTglPutus.setVisible(false);
						lblTglSidang.setVisible(true);
						dpTglSidang.setVisible(true);

						lblTglSidang.setText("Tgl sidang");
					}
				}
			}
		});
		cmbJenis.setModel(new DefaultComboBoxModel<>(new String[] {
			"Pilih Jenis Panggilan",
			"SIDANG 1",
			"TUNDA",
			"IKRAR",
			"PBT",
			"MEDIASI",
			"KONTRA"
		}));
		cmbJenis.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbJenis.setBorder(null);
		GridBagConstraints gbc_cmbJenis = new GridBagConstraints();
		gbc_cmbJenis.insets = new Insets(0, 0, 5, 0);
		gbc_cmbJenis.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbJenis.gridx = 0;
		gbc_cmbJenis.gridy = 1;
		panel.add(cmbJenis, gbc_cmbJenis);

		JLabel lblNewLabel = new JLabel("Perkara No");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		txtPerkaraNo = new JTextField();
		txtPerkaraNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					GetPerkara(txtPerkaraNo.getText());
					txtNamaPp.setText(GetPanitera(perkaraId));
					txtJurusita.setText(GetJurusita(perkaraId));
				}
			}
		});
		txtPerkaraNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtPerkaraNo.setColumns(10);
		GridBagConstraints gbc_txtPerkaraNo = new GridBagConstraints();
		gbc_txtPerkaraNo.insets = new Insets(0, 0, 5, 0);
		gbc_txtPerkaraNo.fill = GridBagConstraints.BOTH;
		gbc_txtPerkaraNo.gridx = 0;
		gbc_txtPerkaraNo.gridy = 3;
		panel.add(txtPerkaraNo, gbc_txtPerkaraNo);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 4;
		panel.add(separator, gbc_separator);

		lblTglPutus = new JLabel("Tanggal Putus");
		lblTglPutus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTglPutus = new GridBagConstraints();
		gbc_lblTglPutus.insets = new Insets(0, 0, 5, 0);
		gbc_lblTglPutus.anchor = GridBagConstraints.WEST;
		gbc_lblTglPutus.gridx = 0;
		gbc_lblTglPutus.gridy = 5;
		panel.add(lblTglPutus, gbc_lblTglPutus);

		dpTglPutus = new JXDatePicker();
		dpTglPutus.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		dpTglPutus.getEditor().setEditable(false);
		dpTglPutus.getEditor().setBorder(null);
		GridBagConstraints gbc_dpTglPutus = new GridBagConstraints();
		gbc_dpTglPutus.insets = new Insets(0, 0, 5, 0);
		gbc_dpTglPutus.fill = GridBagConstraints.BOTH;
		gbc_dpTglPutus.gridx = 0;
		gbc_dpTglPutus.gridy = 6;
		panel.add(dpTglPutus, gbc_dpTglPutus);

		lblTglSidang = new JLabel("Tanggal Sidang");
		lblTglSidang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTglSidang = new GridBagConstraints();
		gbc_lblTglSidang.insets = new Insets(0, 0, 5, 0);
		gbc_lblTglSidang.anchor = GridBagConstraints.WEST;
		gbc_lblTglSidang.gridx = 0;
		gbc_lblTglSidang.gridy = 7;
		panel.add(lblTglSidang, gbc_lblTglSidang);

		dpTglSidang = new JXDatePicker();
		dpTglSidang.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		dpTglSidang.getEditor().setEditable(false);
		dpTglSidang.getEditor().setBorder(null);
		GridBagConstraints gbc_dpTglSidang = new GridBagConstraints();
		gbc_dpTglSidang.insets = new Insets(0, 0, 5, 0);
		gbc_dpTglSidang.fill = GridBagConstraints.HORIZONTAL;
		gbc_dpTglSidang.gridx = 0;
		gbc_dpTglSidang.gridy = 8;
		panel.add(dpTglSidang, gbc_dpTglSidang);

		JLabel lblNewLabel_1_2 = new JLabel("Pgl/PBT untuk");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_2.gridx = 0;
		gbc_lblNewLabel_1_2.gridy = 9;
		panel.add(lblNewLabel_1_2, gbc_lblNewLabel_1_2);

		rdbtnPenggugat = new JRadioButton("Penggugat/Pemohon");
		rdbtnPenggugat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rdbtnPenggugat.setActionCommand("Penggugat/Pemohon");
		GridBagConstraints gbc_rdbtnPenggugat = new GridBagConstraints();
		gbc_rdbtnPenggugat.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnPenggugat.anchor = GridBagConstraints.WEST;
		gbc_rdbtnPenggugat.gridx = 0;
		gbc_rdbtnPenggugat.gridy = 10;
		panel.add(rdbtnPenggugat, gbc_rdbtnPenggugat);

		rdbtnTergugat = new JRadioButton("Tergugat/Termohon");
		rdbtnTergugat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rdbtnTergugat.setActionCommand("Tergugat/Termohon");
		GridBagConstraints gbc_rdbtnTergugat = new GridBagConstraints();
		gbc_rdbtnTergugat.anchor = GridBagConstraints.WEST;
		gbc_rdbtnTergugat.gridx = 0;
		gbc_rdbtnTergugat.gridy = 11;
		panel.add(rdbtnTergugat, gbc_rdbtnTergugat);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.BOTH;
		gbc_separator_1.gridheight = 15;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 3;
		gbc_separator_1.gridy = 2;
		frame.getContentPane().add(separator_1, gbc_separator_1);

		JLabel lblNewLabel_3 = new JLabel("Nama PP");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 5;
		gbc_lblNewLabel_3.gridy = 3;
		frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtNamaPp = new JTextField();
		txtNamaPp.setOpaque(false);
		txtNamaPp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNamaPp.setColumns(10);
		txtNamaPp.setBorder(null);
		GridBagConstraints gbc_txtNamaPp = new GridBagConstraints();
		gbc_txtNamaPp.gridwidth = 2;
		gbc_txtNamaPp.insets = new Insets(0, 0, 5, 5);
		gbc_txtNamaPp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNamaPp.gridx = 5;
		gbc_txtNamaPp.gridy = 4;
		frame.getContentPane().add(txtNamaPp, gbc_txtNamaPp);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 2;
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 5;
		gbc_separator_2.gridy = 5;
		frame.getContentPane().add(separator_2, gbc_separator_2);

		JLabel lblNewLabel_3_1 = new JLabel("Nama JS/JSP");
		lblNewLabel_3_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.gridx = 5;
		gbc_lblNewLabel_3_1.gridy = 6;
		frame.getContentPane().add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);

		txtJurusita = new JTextField();
		txtJurusita.setOpaque(false);
		txtJurusita.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtJurusita.setColumns(10);
		txtJurusita.setBorder(null);
		GridBagConstraints gbc_txtJurusita = new GridBagConstraints();
		gbc_txtJurusita.gridwidth = 2;
		gbc_txtJurusita.insets = new Insets(0, 0, 5, 5);
		gbc_txtJurusita.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJurusita.gridx = 5;
		gbc_txtJurusita.gridy = 7;
		frame.getContentPane().add(txtJurusita, gbc_txtJurusita);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.gridwidth = 2;
		gbc_separator_3.fill = GridBagConstraints.BOTH;
		gbc_separator_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3.gridx = 5;
		gbc_separator_3.gridy = 8;
		frame.getContentPane().add(separator_3, gbc_separator_3);

		JLabel lblNewLabel_3_1_1 = new JLabel("Kecamatan");
		lblNewLabel_3_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1_1.gridx = 5;
		gbc_lblNewLabel_3_1_1.gridy = 9;
		frame.getContentPane().add(lblNewLabel_3_1_1, gbc_lblNewLabel_3_1_1);

		cmbKecamatan = new JComboBox<>();
		cmbKecamatan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbKecamatan.setBorder(null);
		GridBagConstraints gbc_cmbKecamatan = new GridBagConstraints();
		gbc_cmbKecamatan.insets = new Insets(0, 0, 5, 5);
		gbc_cmbKecamatan.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbKecamatan.gridx = 5;
		gbc_cmbKecamatan.gridy = 10;
		frame.getContentPane().add(cmbKecamatan, gbc_cmbKecamatan);

		JLabel lblNewLabel_3_1_1_2 = new JLabel("Biaya Pgl/Pbt");
		lblNewLabel_3_1_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3_1_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1_1_2.gridx = 5;
		gbc_lblNewLabel_3_1_1_2.gridy = 11;
		frame.getContentPane().add(lblNewLabel_3_1_1_2, gbc_lblNewLabel_3_1_1_2);

		txtBiaya = new JTextField();
		txtBiaya.setOpaque(false);
		txtBiaya.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtBiaya.setColumns(10);
		txtBiaya.setBorder(null);
		GridBagConstraints gbc_txtBiaya = new GridBagConstraints();
		gbc_txtBiaya.gridwidth = 2;
		gbc_txtBiaya.insets = new Insets(0, 0, 5, 5);
		gbc_txtBiaya.fill = GridBagConstraints.BOTH;
		gbc_txtBiaya.gridx = 5;
		gbc_txtBiaya.gridy = 12;
		frame.getContentPane().add(txtBiaya, gbc_txtBiaya);

		JSeparator separator_3_1 = new JSeparator();
		separator_3_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_3_1 = new GridBagConstraints();
		gbc_separator_3_1.fill = GridBagConstraints.BOTH;
		gbc_separator_3_1.gridwidth = 2;
		gbc_separator_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3_1.gridx = 5;
		gbc_separator_3_1.gridy = 13;
		frame.getContentPane().add(separator_3_1, gbc_separator_3_1);

		btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnPenggugat);
		btnGroup.add(rdbtnTergugat);

		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.setIcon(new ImageIcon(".\\icon\\floppy-disk.png"));
		btnSimpan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				if (cmbJenis.getSelectedIndex() > 0) {
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut", "Konfirmasi", 0);
					if (konfirm == 0) {
						if (cmbJenis.getSelectedIndex() == 2) {
							if (txtPerkaraNo.getText().equals("") || txtNamaPp.getText().equals("") ||
									txtJurusita.getText().equals("") || 
									btnGroup.isSelected(null) || dpTglSidang.getDate() == null ||
									cmbKecamatan.getSelectedIndex() == 0) {
								JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
							} else {
								SimpanTunda();
								try {
									con.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
						 		if (isServer)
						 			session.disconnect();
								FMain.window.frame.setEnabled(true);
								PSidang.TampilData("");
								window.frame.dispose();
							}
						} else if (cmbJenis.getSelectedIndex() == 4 || cmbJenis.getSelectedIndex() == 3) {
							if (txtPerkaraNo.getText().equals("") || txtNamaPp.getText().equals("") ||
									txtJurusita.getText().equals("") || 
									btnGroup.isSelected(null) || dpTglPutus.getDate() == null ||
									cmbKecamatan.getSelectedIndex() == 0) {
								JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
							} else {
								SimpanPemberitahuan();
								try {
									con.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
						 		if (isServer)
						 			session.disconnect();
								FMain.window.frame.setEnabled(true);
								PSidang.TampilData("");
								window.frame.dispose();
							}
						} else {
							if (txtPerkaraNo.getText().equals("") || txtNamaPp.getText().equals("") ||
									txtJurusita.getText().equals("") || 
									btnGroup.isSelected(null) || dpTglSidang.getDate() == null ||
									cmbKecamatan.getSelectedIndex() == 0) {
								JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
						 	} else {
						 		Simpan();
						 		try {
									con.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
						 		if (isServer)
						 			session.disconnect();
						 		FMain.window.frame.setEnabled(true);
								PSidang.TampilData("");
								window.frame.dispose();
						 	}
						}
					}
				} else
					JOptionPane.showMessageDialog(null, "Isi jenis panggilan terlebih dahulu!");
			}
		});
		btnSimpan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimpan.gridx = 5;
		gbc_btnSimpan.gridy = 16;
		frame.getContentPane().add(btnSimpan, gbc_btnSimpan);

		JButton btnBatal = new JButton("Batal");
		btnBatal.setIcon(new ImageIcon(".\\icon\\cancel.png"));
		btnBatal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isServer)
		 			session.disconnect();
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnBatal = new GridBagConstraints();
		gbc_btnBatal.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBatal.insets = new Insets(0, 0, 5, 5);
		gbc_btnBatal.gridx = 6;
		gbc_btnBatal.gridy = 16;
		frame.getContentPane().add(btnBatal, gbc_btnBatal);

		ImagePanel lblNewLabel1 = new ImagePanel(new ImageIcon(".\\icon\\BG Panggilan(1).png").getImage());
		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.gridx = 0;
		gbc_lblNewLabel1.gridy = 0;
		gbc_lblNewLabel1.gridheight = 18;
		gbc_lblNewLabel1.gridwidth = 8;
		frame.getContentPane().add(lblNewLabel1, gbc_lblNewLabel1);
	}



	private void SetKecamatan() {
		PanggilanDaoImpl pDaoImpl = new PanggilanDaoImpl();
		List<Panggilan> panggilan = pDaoImpl.getKecamatan();

		cmbKecamatan.removeAllItems();
		cmbKecamatan.addItem("Pilih Kecamatan");
		for (Panggilan p : panggilan) {
			cmbKecamatan.addItem(p.getKecamatan());
		}
	}

	private void GetPerkara(String perkaraNo) {
		TahunDaoImpl t = new TahunDaoImpl();
		String tahun = t.getTahunAktif();
		try {
			String query = "select perkara_id, nomor_perkara from perkara where nomor_perkara = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, perkaraNo + "/" + tahun + "/PA.Kab.Kdr");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				perkaraId = rs.getString("perkara_id");
				txtPerkaraNo.setText(rs.getString("nomor_perkara"));
			} else {
				JOptionPane.showMessageDialog(null, "Nomor perkara tidak ditemukan!");
			}
//			JOptionPane.showMessageDialog(null, perkaraId);
			rs.close();
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String GetPanitera(String perkaraId) {
		String panitera = "";
		try {
			String query = "select panitera_nama as data "
					+ "from perkara_panitera_pn "
					+ "where aktif= ? and perkara_id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "Y");
			ps.setString(2, perkaraId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				panitera = rs.getString("data");
				
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(panitera);
		return panitera;
	}

	private String GetKetuaMajelis(String perkaraId) {
		String majelis = "";
		try {
			String query = "select b.nama_gelar as data from perkara_hakim_pn a "
					+ "join hakim_pn b on a.hakim_id = b.id "
					+ "where a.aktif=? and a.urutan=? and a.perkara_id=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "Y");
			ps.setString(2, "1");
			ps.setString(3, perkaraId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				majelis = rs.getString("data");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return majelis;
	}

	private String GetJurusita(String perkaraId) {
		String jurusita = "";
		
		try {
			String query = "select b.nama_gelar as data "
					+ "from perkara_jurusita a "
					+ "join jurusita b on a.jurusita_id =b.id "
					+ "where a.aktif=? and a.urutan=? and a.perkara_id=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "Y");
			ps.setString(2, "1");
			ps.setString(3, perkaraId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				jurusita = rs.getString("data");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return jurusita;
	}

	private List<String> GetPenggugatTergugat(String perkaraId, String table) {
		List<String> penggugat = new ArrayList<>();
		try {
			String query = "select * from " + table + " where perkara_id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, perkaraId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				penggugat.add(rs.getString("nama"));
				penggugat.add(rs.getString("alamat"));
			}

			ps.close();
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return penggugat;
	}

	private void Simpan() {
		String ketuaMajelis = GetKetuaMajelis(perkaraId);
		List<String> p = null;
		if (btnGroup.getSelection().getActionCommand().equals("Penggugat/Pemohon")) {
			p = GetPenggugatTergugat(perkaraId, "perkara_pihak1");

		} else {
			p = GetPenggugatTergugat(perkaraId, "perkara_pihak2");
		}
		System.out.println(btnGroup.getSelection().getActionCommand());
		Database db = new Database();
		db.con();
		Connection con =  db.con;
		try {
			String query = "insert into tb_panggilan (no_perkara, jenis, "
					+ "nama_pp, nama_js, nama, alamat, tgl_sidang, pgl_pbt, "
					+ "kecamatan, biaya, hakim) values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, txtPerkaraNo.getText());
			ps.setString(2, cmbJenis.getSelectedItem().toString());
			ps.setString(3, txtNamaPp.getText());
			ps.setString(4, txtJurusita.getText());
			ps.setString(5, p.get(0));
			ps.setString(6, p.get(1));
			ps.setString(7, df.format(dpTglSidang.getDate()));
			ps.setString(8, btnGroup.getSelection().getActionCommand());
			ps.setString(9, cmbKecamatan.getSelectedItem().toString());
			if (txtBiaya.getText().equals(""))
				ps.setNull(10, Types.INTEGER);
			else
				ps.setString(10, txtBiaya.getText());
			ps.setString(11, ketuaMajelis);
			ps.execute();

			ps.close();
			con.close();

			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void SimpanTunda() {
		String ketuaMajelis = GetKetuaMajelis(perkaraId);
		List<String> p = new ArrayList<>();

		if (btnGroup.getSelection().getActionCommand().equals("Penggugat/Pemohon")) {
			p = GetPenggugatTergugat(perkaraId, "perkara_pihak1");
		} else {
			p = GetPenggugatTergugat(perkaraId, "perkara_pihak2");
		}

		Database db = new Database();
		db.con();
		Connection con =  db.con;
		try {
			String query = "insert into tb_panggilan (no_perkara, jenis, "
					+ "nama_pp, nama_js, nama, alamat, tgl_sidang, pgl_pbt, "
					+ "kecamatan, biaya, hakim) values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, txtPerkaraNo.getText());
			ps.setString(2, cmbJenis.getSelectedItem().toString());
			ps.setString(3, txtNamaPp.getText());
			ps.setString(4, txtJurusita.getText());
			ps.setString(5, p.get(0));
			ps.setString(6, p.get(1));
			ps.setString(7, df.format(dpTglSidang.getDate()));
			ps.setString(8, btnGroup.getSelection().getActionCommand());
			ps.setString(9, cmbKecamatan.getSelectedItem().toString());
			if (txtBiaya.getText().equals(""))
				ps.setNull(10, Types.INTEGER);
			else
				ps.setString(10, txtBiaya.getText());
			ps.setString(11, ketuaMajelis);
			ps.execute();

			ps.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void SimpanPemberitahuan() {
		String ketuaMajelis = GetKetuaMajelis(perkaraId);

		List<String> p = new ArrayList<>();
		if (btnGroup.getSelection().getActionCommand().equals("Penggugat/Pemohon")) {
			p = GetPenggugatTergugat(perkaraId, "perkara_pihak1");
		} else {
			p = GetPenggugatTergugat(perkaraId, "perkara_pihak2");
		}

		Database db = new Database();
		db.con();
		Connection con =  db.con;
		try {
			String query = "insert into tb_panggilan (no_perkara, jenis, "
					+ "nama_pp, nama_js, nama, alamat, tgl_putus, pgl_pbt, "
					+ "kecamatan, biaya, hakim) values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, txtPerkaraNo.getText());
			ps.setString(2, cmbJenis.getSelectedItem().toString());
			ps.setString(3, txtNamaPp.getText());
			ps.setString(4, txtJurusita.getText());
			ps.setString(5, p.get(0));
			ps.setString(6, p.get(1));
			ps.setString(7, df.format(dpTglPutus.getDate()));
			ps.setString(8, btnGroup.getSelection().getActionCommand());
			ps.setString(9, cmbKecamatan.getSelectedItem().toString());
			if (txtBiaya.getText().equals(""))
				ps.setNull(10, Types.INTEGER);
			else
				ps.setString(10, txtBiaya.getText());
			ps.setString(11, ketuaMajelis);
			ps.execute();

			ps.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
