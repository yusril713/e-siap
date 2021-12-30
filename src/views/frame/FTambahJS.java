package views.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import config.ImagePanel;
import config.JPlaceholderPasswordField;
import config.JPlaceholderTextField;
import daoimpl.UserDaoImpl;
import entities.User;
import views.panel.PDataJS;

public class FTambahJS {

	JFrame frame;
	static FTambahJS window;
	private JTextField txtNip;
	private JSeparator separator;
	private JLabel lblNama;
	private JTextField txtNama;
	private JSeparator separator_1;
	private JLabel lblJenisKelamin;
	private JComboBox<String> cmbJenisKelamin;
	private JLabel lblTempatLahir;
	private JTextField txtTempatLahir;
	private JSeparator separator_2;
	private JLabel lblTanggalLahir;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cmbTanggal;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cmbBulan;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cmbTahun;
	private JLabel lblNoTelp;
	private JTextField txtNoTelp;
	private JLabel lblAlamat;
	private JTextField txtAlamat;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JLabel lblUsername;
	private JTextField txtUsername;
	private JSeparator sepUsername;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JSeparator sepPassword;
	private JLabel lblLevelUser;
	private JComboBox<String> cmbLevel;
	private JButton btnTutup;
	private JButton btnSimpan;
	private int konfirm = 0;
	public static boolean edit = false;
	public static String nip = null;
	private User user;
	private UserDaoImpl uDaoImpl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new FTambahJS();
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
	public FTambahJS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				SetAllCombobox();
				user = new User();
				uDaoImpl = new UserDaoImpl();
				if (edit) {
					lblUsername.setVisible(false);
					lblPassword.setVisible(false);
					txtUsername.setVisible(false);
					txtPassword.setVisible(false);
					sepUsername.setVisible(false);
					sepPassword.setVisible(false);
					SetValueTextField();
				}
			}
		});
		frame.setBounds(100, 100, 582, 332);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNip = new JLabel("Nip");
		lblNip.setBounds(20, 19, 52, 16);
		frame.getContentPane().add(lblNip);

		txtNip = new JPlaceholderTextField("Masukkan nip...");
		txtNip.setBorder(null);
		txtNip.setBounds(20, 37, 238, 26);
		txtNip.setOpaque(false);
		frame.getContentPane().add(txtNip);
		txtNip.setColumns(10);

		separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(20, 60, 238, 8);
		frame.getContentPane().add(separator);

		lblNama = new JLabel("Nama");
		lblNama.setBounds(20, 75, 52, 16);
		frame.getContentPane().add(lblNama);

		txtNama = new JPlaceholderTextField("Masukkan nama lengkap...");
		txtNama.setOpaque(false);
		txtNama.setColumns(10);
		txtNama.setBorder(null);
		txtNama.setBounds(20, 92, 238, 26);
		frame.getContentPane().add(txtNama);

		separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(20, 115, 238, 8);
		frame.getContentPane().add(separator_1);

		lblJenisKelamin = new JLabel("Jenis Kelamin");
		lblJenisKelamin.setBounds(20, 130, 71, 16);
		frame.getContentPane().add(lblJenisKelamin);

		cmbJenisKelamin = new JComboBox<>();
		cmbJenisKelamin.setBounds(20, 147, 238, 26);
		frame.getContentPane().add(cmbJenisKelamin);

		lblTempatLahir = new JLabel("Tempat Lahir");
		lblTempatLahir.setBounds(20, 185, 71, 16);
		frame.getContentPane().add(lblTempatLahir);

		txtTempatLahir = new JPlaceholderTextField("Masukkan tempat lahir...");
		txtTempatLahir.setOpaque(false);
		txtTempatLahir.setColumns(10);
		txtTempatLahir.setBorder(null);
		txtTempatLahir.setBounds(20, 202, 238, 26);
		frame.getContentPane().add(txtTempatLahir);

		separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(20, 225, 238, 8);
		frame.getContentPane().add(separator_2);

		lblTanggalLahir = new JLabel("Tanggal Lahir");
		lblTanggalLahir.setBounds(20, 240, 83, 16);
		frame.getContentPane().add(lblTanggalLahir);

		cmbTanggal = new JComboBox<>();
		cmbTanggal.setBounds(20, 257, 72, 26);
		frame.getContentPane().add(cmbTanggal);

		cmbBulan = new JComboBox<>();
		cmbBulan.setBounds(99, 257, 72, 26);
		frame.getContentPane().add(cmbBulan);

		cmbTahun = new JComboBox<>();
		cmbTahun.setBounds(183, 257, 72, 26);
		frame.getContentPane().add(cmbTahun);

		lblNoTelp = new JLabel("No Telp");
		lblNoTelp.setBounds(316, 26, 52, 16);
		frame.getContentPane().add(lblNoTelp);

		txtNoTelp = new JPlaceholderTextField("Masukkan no telp...");
		txtNoTelp.setOpaque(false);
		txtNoTelp.setColumns(10);
		txtNoTelp.setBorder(null);
		txtNoTelp.setBounds(316, 43, 238, 26);
		frame.getContentPane().add(txtNoTelp);

		lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(316, 81, 52, 16);
		frame.getContentPane().add(lblAlamat);

		txtAlamat = new JPlaceholderTextField("Masukkan alamat...");
		txtAlamat.setOpaque(false);
		txtAlamat.setColumns(10);
		txtAlamat.setBorder(null);
		txtAlamat.setBounds(316, 96, 238, 26);
		frame.getContentPane().add(txtAlamat);

		separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(316, 66, 238, 8);
		frame.getContentPane().add(separator_3);

		separator_4 = new JSeparator();
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(316, 119, 238, 8);
		frame.getContentPane().add(separator_4);

		lblUsername = new JLabel("Username");
		lblUsername.setBounds(316, 185, 71, 16);
		frame.getContentPane().add(lblUsername);

		txtUsername = new JPlaceholderTextField("Masukkan username...");
		txtUsername.setOpaque(false);
		txtUsername.setColumns(10);
		txtUsername.setBorder(null);
		txtUsername.setBounds(316, 201, 238, 26);
		frame.getContentPane().add(txtUsername);

		sepUsername = new JSeparator();
		sepUsername.setForeground(Color.BLACK);
		sepUsername.setBounds(316, 224, 238, 8);
		frame.getContentPane().add(sepUsername);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(316, 236, 52, 16);
		frame.getContentPane().add(lblPassword);

		txtPassword = new JPlaceholderPasswordField("Masukkan password...");
		txtPassword.setOpaque(false);
		txtPassword.setColumns(10);
		txtPassword.setBorder(null);
		txtPassword.setBounds(316, 252, 238, 26);
		frame.getContentPane().add(txtPassword);

		sepPassword = new JSeparator();
		sepPassword.setForeground(Color.BLACK);
		sepPassword.setBounds(316, 275, 238, 8);
		frame.getContentPane().add(sepPassword);

		lblLevelUser = new JLabel("Level user");
		lblLevelUser.setBounds(316, 130, 52, 16);
		frame.getContentPane().add(lblLevelUser);

		cmbLevel = new JComboBox<>();
		cmbLevel.setBounds(316, 147, 238, 26);
		frame.getContentPane().add(cmbLevel);

		btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setIcon(new ImageIcon(".\\icon\\cancel.png"));
		btnTutup.setBounds(457, 289, 97, 26);
		frame.getContentPane().add(btnTutup);

		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpand data tersebut?", "Konfirmasi", 0);
				if (txtAlamat.getText().equals("") || txtNama.getText().equals("") ||
						txtNip.getText().equals("") || txtNoTelp.getText().equals("") ||
						txtPassword.getText().equals("") || txtTempatLahir.getText().equals("") ||
						txtUsername.getText().equals("") || cmbBulan.getSelectedIndex() == 0 ||
						cmbJenisKelamin.getSelectedIndex() == 0 || cmbLevel.getSelectedIndex() == 0 ||
						cmbTahun.getSelectedIndex() == 0 || cmbTanggal.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu...");
				else {
					if (konfirm == 0) {
						if (!edit) {
							if (!uDaoImpl.IsAvalilable(txtNip.getText(), txtUsername.getText()))
								Store();
							else
								JOptionPane.showMessageDialog(null, "Data sudah ada...");
						} else {
							Update(nip);
						}
						FMain.window.frame.setEnabled(true);
						PDataJS.Find("");
						window.frame.dispose();
					}
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(".\\icon\\floppy-disk.png"));
		btnSimpan.setBounds(359, 289, 97, 26);
		frame.getContentPane().add(btnSimpan);



		ImagePanel lblNewLabel1 = new ImagePanel(".\\icon\\BG User(1).png");
		lblNewLabel1.setBounds(0, 0, 582, 332);
		frame.getContentPane().add(lblNewLabel1);
	}

	private void SetAllCombobox() {
		cmbJenisKelamin.removeAllItems();
		cmbJenisKelamin.addItem("Pilih Jenis Kelamin");
		cmbJenisKelamin.addItem("Laki-laki");
		cmbJenisKelamin.addItem("Perempuan");

		cmbTanggal.removeAllItems();
		cmbTanggal.addItem("Tanggal");
		for (int i = 1; i <= 31; i++)
			cmbTanggal.addItem(i);

		cmbBulan.removeAllItems();
		cmbBulan.addItem("Bulan");
		for (int i = 1; i <= 12; i++)
			cmbBulan.addItem(i);

		cmbTahun.removeAllItems();
		cmbTahun.addItem("Tahun");
		int tahun = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = tahun; i >= tahun-60; i--)
			cmbTahun.addItem(i);

		cmbLevel.removeAllItems();
		cmbLevel.addItem("Pilih Level User");
		cmbLevel.addItem("Super Admin");
		cmbLevel.addItem("Admin");
		cmbLevel.addItem("User 1 (Hanya list data)");
		cmbLevel.addItem("User 2 (Jurusita)");
	}


	private void SetValueTextField() {
		User user = uDaoImpl.find(nip);
		txtAlamat.setText(user.getAlamat());
		txtNama.setText(user.getNama());
		txtNip.setText(user.getNip());
		txtNoTelp.setText(user.getNoTelp());
		txtTempatLahir.setText(user.getTempatLahir());
		cmbBulan.setSelectedItem(Integer.parseInt(new SimpleDateFormat("MM").format(user.getTglLahir())));
		cmbJenisKelamin.setSelectedItem(user.getJenisKelamin());
		cmbTahun.setSelectedItem(Integer.parseInt(new SimpleDateFormat("yyyy").format(user.getTglLahir())));
		cmbTanggal.setSelectedItem(Integer.parseInt(new SimpleDateFormat("dd").format(user.getTglLahir())));
		cmbLevel.setSelectedIndex(user.getLevel());
	}

	@SuppressWarnings("deprecation")
	private void Store() {
		String tglLahir = cmbTahun.getSelectedItem().toString() + "-"
				 + cmbBulan.getSelectedItem().toString() + "-"
				 + cmbTanggal.getSelectedItem().toString();

		user.setAlamat(txtAlamat.getText());
		user.setJenisKelamin(cmbJenisKelamin.getSelectedItem().toString());
		user.setLevel(cmbLevel.getSelectedIndex());
		user.setNama(txtNama.getText());
		user.setNip(txtNip.getText());
		user.setNoTelp(txtNoTelp.getText());
		user.setPassword(txtPassword.getText());
		user.setTempatLahir(txtTempatLahir.getText());
		try {
			user.setTglLahir(new SimpleDateFormat("yyyy-MM-dd").parse(tglLahir));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setUsername(txtUsername.getText());
		uDaoImpl.insert(user);
	}

	private void Update(String nip) {
		String tglLahir = cmbTahun.getSelectedItem().toString() + "-"
				 + cmbBulan.getSelectedItem().toString() + "-"
				 + cmbTanggal.getSelectedItem().toString();

		user.setAlamat(txtAlamat.getText());
		user.setJenisKelamin(cmbJenisKelamin.getSelectedItem().toString());
		user.setLevel(cmbLevel.getSelectedIndex());
		user.setNama(txtNama.getText());
		user.setNip(txtNip.getText());
		user.setNoTelp(txtNoTelp.getText());
		user.setTempatLahir(txtTempatLahir.getText());
		try {
			user.setTglLahir(new SimpleDateFormat("yyyy-MM-dd").parse(tglLahir));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		uDaoImpl.update(user, nip);
	}
}
