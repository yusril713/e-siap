package views.panel;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;

import config.Database;
import config.ImagePanel;
import daoimpl.UserDaoImpl;
import entities.User;
import views.frame.FGantiPassword;
import views.frame.FMain;

import java.awt.Insets;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PUser extends JPanel {
	private static final long serialVersionUID = 1L;
	private User user;
	private UserDaoImpl userDaoImpl;
	private JTextField txtNip;
	private JTextField txtNama;
	private JComboBox<String> cmbJenisKelamin;
	private JTextField txtTempatLahir;
	private JTextField txtNoTelp;
	private JTextField txtAlamat;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cmbTanggal;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cmbBulan;
	@SuppressWarnings("rawtypes")
	private JComboBox<Comparable> cmbTahun;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("rawtypes")
	public PUser() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				new Database();
				user = new User();
				userDaoImpl = new UserDaoImpl();
				SetAllCombobox();
				Find(FMain.user);
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		
		ImagePanel lblImage = new ImagePanel(".\\icon\\BG User(1).png");
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.fill = GridBagConstraints.BOTH;
		gbc_lblImage.gridx = 0;
		gbc_lblImage.gridy = 0;
		add(lblImage, gbc_lblImage);
		GridBagLayout gbl_lblImage = new GridBagLayout();
		gbl_lblImage.columnWidths = new int[]{20, 150, 150, 150, 100, 300, 150, 0, 0};
		gbl_lblImage.rowHeights = new int[]{20, 0, 0, 10, 0, 0, 10, 0, 0, 0, 0, 10, 0, 0, 0, 0};
		gbl_lblImage.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_lblImage.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		lblImage.setLayout(gbl_lblImage);
		
		JLabel lblNip = new JLabel("Nip");
		lblNip.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNip = new GridBagConstraints();
		gbc_lblNip.anchor = GridBagConstraints.WEST;
		gbc_lblNip.insets = new Insets(0, 0, 5, 5);
		gbc_lblNip.gridx = 1;
		gbc_lblNip.gridy = 1;
		lblImage.add(lblNip, gbc_lblNip);
		
		JLabel lblNoTelp = new JLabel("No. Telp");
		lblNoTelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNoTelp = new GridBagConstraints();
		gbc_lblNoTelp.anchor = GridBagConstraints.WEST;
		gbc_lblNoTelp.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoTelp.gridx = 5;
		gbc_lblNoTelp.gridy = 1;
		lblImage.add(lblNoTelp, gbc_lblNoTelp);
		
		txtNip = new JTextField();
		txtNip.setBorder(null);
		txtNip.setOpaque(false);
		txtNip.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtNip = new GridBagConstraints();
		gbc_txtNip.gridwidth = 3;
		gbc_txtNip.insets = new Insets(0, 0, 5, 5);
		gbc_txtNip.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNip.gridx = 1;
		gbc_txtNip.gridy = 2;
		lblImage.add(txtNip, gbc_txtNip);
		txtNip.setColumns(10);
		
		txtNoTelp = new JTextField();
		txtNoTelp.setBorder(null);
		txtNoTelp.setOpaque(false);
		txtNoTelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNoTelp.setColumns(10);
		GridBagConstraints gbc_txtNoTelp = new GridBagConstraints();
		gbc_txtNoTelp.gridwidth = 2;
		gbc_txtNoTelp.insets = new Insets(0, 0, 5, 5);
		gbc_txtNoTelp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNoTelp.gridx = 5;
		gbc_txtNoTelp.gridy = 2;
		lblImage.add(txtNoTelp, gbc_txtNoTelp);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 3;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 3;
		lblImage.add(separator, gbc_separator);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1_2 = new GridBagConstraints();
		gbc_separator_1_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1_2.gridwidth = 2;
		gbc_separator_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1_2.gridx = 5;
		gbc_separator_1_2.gridy = 3;
		lblImage.add(separator_1_2, gbc_separator_1_2);
		
		JLabel lblNama = new JLabel("Nama");
		lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNama = new GridBagConstraints();
		gbc_lblNama.anchor = GridBagConstraints.WEST;
		gbc_lblNama.insets = new Insets(0, 0, 5, 5);
		gbc_lblNama.gridx = 1;
		gbc_lblNama.gridy = 4;
		lblImage.add(lblNama, gbc_lblNama);
		
		JLabel lblNoTelp_1 = new JLabel("Alamat");
		lblNoTelp_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNoTelp_1 = new GridBagConstraints();
		gbc_lblNoTelp_1.anchor = GridBagConstraints.WEST;
		gbc_lblNoTelp_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoTelp_1.gridx = 5;
		gbc_lblNoTelp_1.gridy = 4;
		lblImage.add(lblNoTelp_1, gbc_lblNoTelp_1);
		
		txtNama = new JTextField();
		txtNama.setBorder(null);
		txtNama.setOpaque(false);
		txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNama.setColumns(10);
		GridBagConstraints gbc_txtNama = new GridBagConstraints();
		gbc_txtNama.gridwidth = 3;
		gbc_txtNama.insets = new Insets(0, 0, 5, 5);
		gbc_txtNama.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNama.gridx = 1;
		gbc_txtNama.gridy = 5;
		lblImage.add(txtNama, gbc_txtNama);
		
		txtAlamat = new JTextField();
		txtAlamat.setBorder(null);
		txtAlamat.setOpaque(false);
		txtAlamat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtAlamat.setColumns(10);
		GridBagConstraints gbc_txtAlamat = new GridBagConstraints();
		gbc_txtAlamat.gridwidth = 2;
		gbc_txtAlamat.insets = new Insets(0, 0, 5, 5);
		gbc_txtAlamat.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAlamat.gridx = 5;
		gbc_txtAlamat.gridy = 5;
		lblImage.add(txtAlamat, gbc_txtAlamat);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 3;
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 1;
		gbc_separator_1.gridy = 6;
		lblImage.add(separator_1, gbc_separator_1);
		
		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1_3 = new GridBagConstraints();
		gbc_separator_1_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1_3.gridwidth = 2;
		gbc_separator_1_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1_3.gridx = 5;
		gbc_separator_1_3.gridy = 6;
		lblImage.add(separator_1_3, gbc_separator_1_3);
		
		JLabel lblJenisKelamin = new JLabel("Jenis Kelamin");
		lblJenisKelamin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblJenisKelamin = new GridBagConstraints();
		gbc_lblJenisKelamin.anchor = GridBagConstraints.WEST;
		gbc_lblJenisKelamin.insets = new Insets(0, 0, 5, 5);
		gbc_lblJenisKelamin.gridx = 1;
		gbc_lblJenisKelamin.gridy = 7;
		lblImage.add(lblJenisKelamin, gbc_lblJenisKelamin);
		
		cmbJenisKelamin = new JComboBox<String>();
		cmbJenisKelamin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbJenisKelamin = new GridBagConstraints();
		gbc_cmbJenisKelamin.gridwidth = 3;
		gbc_cmbJenisKelamin.insets = new Insets(0, 0, 5, 5);
		gbc_cmbJenisKelamin.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbJenisKelamin.gridx = 1;
		gbc_cmbJenisKelamin.gridy = 8;
		lblImage.add(cmbJenisKelamin, gbc_cmbJenisKelamin);
		
		JLabel lblTempatLahir = new JLabel("Tempat Lahir");
		lblTempatLahir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTempatLahir = new GridBagConstraints();
		gbc_lblTempatLahir.anchor = GridBagConstraints.WEST;
		gbc_lblTempatLahir.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempatLahir.gridx = 1;
		gbc_lblTempatLahir.gridy = 9;
		lblImage.add(lblTempatLahir, gbc_lblTempatLahir);
		
		txtTempatLahir = new JTextField();
		txtTempatLahir.setBorder(null);
		txtTempatLahir.setOpaque(false);
		txtTempatLahir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtTempatLahir = new GridBagConstraints();
		gbc_txtTempatLahir.gridwidth = 3;
		gbc_txtTempatLahir.insets = new Insets(0, 0, 5, 5);
		gbc_txtTempatLahir.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTempatLahir.gridx = 1;
		gbc_txtTempatLahir.gridy = 10;
		lblImage.add(txtTempatLahir, gbc_txtTempatLahir);
		txtTempatLahir.setColumns(10);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1_1 = new GridBagConstraints();
		gbc_separator_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1_1.gridwidth = 3;
		gbc_separator_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1_1.gridx = 1;
		gbc_separator_1_1.gridy = 11;
		lblImage.add(separator_1_1, gbc_separator_1_1);
		
		JLabel lblTanggalLahir = new JLabel("Tanggal Lahir");
		lblTanggalLahir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTanggalLahir = new GridBagConstraints();
		gbc_lblTanggalLahir.anchor = GridBagConstraints.WEST;
		gbc_lblTanggalLahir.insets = new Insets(0, 0, 5, 5);
		gbc_lblTanggalLahir.gridx = 1;
		gbc_lblTanggalLahir.gridy = 12;
		lblImage.add(lblTanggalLahir, gbc_lblTanggalLahir);
		
		cmbTanggal = new JComboBox<Comparable>();
		cmbTanggal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbTanggal = new GridBagConstraints();
		gbc_cmbTanggal.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTanggal.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTanggal.gridx = 1;
		gbc_cmbTanggal.gridy = 13;
		lblImage.add(cmbTanggal, gbc_cmbTanggal);
		
		cmbBulan = new JComboBox<Comparable>();
		cmbBulan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbBulan = new GridBagConstraints();
		gbc_cmbBulan.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBulan.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBulan.gridx = 2;
		gbc_cmbBulan.gridy = 13;
		lblImage.add(cmbBulan, gbc_cmbBulan);
		
		cmbTahun = new JComboBox<Comparable>();
		cmbTahun.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbTahun = new GridBagConstraints();
		gbc_cmbTahun.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTahun.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTahun.gridx = 3;
		gbc_cmbTahun.gridy = 13;
		lblImage.add(cmbTahun, gbc_cmbTahun);
		
		JButton btnGantiPassword = new JButton("Ganti Password");
		btnGantiPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FGantiPassword.main(null);
				FMain.window.frame.setEnabled(false);
			}
		});
		btnGantiPassword.setIcon(new ImageIcon("D:\\Documents\\eclipse-workspace\\AppSidang\\icon\\edit.png"));
		btnGantiPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnGantiPassword = new GridBagConstraints();
		gbc_btnGantiPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGantiPassword.insets = new Insets(0, 0, 0, 5);
		gbc_btnGantiPassword.gridx = 1;
		gbc_btnGantiPassword.gridy = 14;
		lblImage.add(btnGantiPassword, gbc_btnGantiPassword);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah data?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Update(FMain.user);
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon("D:\\Documents\\eclipse-workspace\\AppSidang\\icon\\floppy-disk.png"));
		btnSimpan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimpan.insets = new Insets(0, 0, 0, 5);
		gbc_btnSimpan.gridx = 6;
		gbc_btnSimpan.gridy = 14;
		lblImage.add(btnSimpan, gbc_btnSimpan);
		
		
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
	}

	private void Find(String username) {
		User user = userDaoImpl.findMyAccount(username);
		txtAlamat.setText(user.getAlamat());
		txtNama.setText(user.getNama());
		txtNip.setText(user.getNip());
		txtNoTelp.setText(user.getNoTelp());
		txtTempatLahir.setText(user.getTempatLahir());
		cmbBulan.setSelectedItem(Integer.parseInt(new SimpleDateFormat("MM").format(user.getTglLahir())));
		cmbJenisKelamin.setSelectedItem(user.getJenisKelamin());
		cmbTahun.setSelectedItem(Integer.parseInt(new SimpleDateFormat("yyyy").format(user.getTglLahir())));
		cmbTanggal.setSelectedItem(Integer.parseInt(new SimpleDateFormat("dd").format(user.getTglLahir())));
	}

	private void Update(String username) {
		user.setNip(txtNip.getText());
		user.setNama(txtNama.getText());
		user.setNoTelp(txtNoTelp.getText());
		user.setTempatLahir(txtTempatLahir.getText());
		try {
			user.setTglLahir(new SimpleDateFormat("yyyy-MM-dd").parse(cmbTahun.getSelectedItem().toString() + "-"
						+ cmbBulan.getSelectedItem().toString() + "-"
						+ cmbTanggal.getSelectedItem().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setAlamat(txtAlamat.getText());
		user.setJenisKelamin(cmbJenisKelamin.getSelectedItem().toString());
		userDaoImpl.updateMyAccount(user, username);
	}
}
