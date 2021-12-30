package views.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import config.Database;
import config.ImagePanel;
import config.JPlaceholderTextField;
import daoimpl.UserDaoImpl;
import daoimpl.WilayahDaoImpl;
import entities.User;
import entities.Wilayah;
import views.panel.PWilayah;

public class FTambahWilayah {

	public JFrame frame;
	static FTambahWilayah window;
	public static boolean edit = false;
	public static String nip = null;
	public static String wilayah = null;
	private JTextField txtWilayah;
	private JTextField txtKeterangan;
	private JComboBox<String> cmbHari;
	private JComboBox<String> cmbWilayah;
	private JComboBox<String> cmbJurusita;
	private Wilayah region;
	private WilayahDaoImpl regionDaoImpl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new FTambahWilayah();
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
	public FTambahWilayah() {
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
				new Database();
				region = new Wilayah();
				regionDaoImpl = new WilayahDaoImpl();
				SetJurusita();
				SetWilayah();
				SetHari();

				if (edit) {
					Find(nip, wilayah);
				}
			}
		});
		frame.setBounds(100, 100, 311, 300);
		frame.getContentPane().setLayout(null);

		JLabel lblJurusitajurisataPengganti = new JLabel("Jurusita/Jurisata Pengganti");
		lblJurusitajurisataPengganti.setBounds(26, 25, 148, 16);
		frame.getContentPane().add(lblJurusitajurisataPengganti);

		cmbJurusita = new JComboBox<>();
		cmbJurusita.setBorder(null);
		cmbJurusita.setBounds(26, 40, 255, 26);
		frame.getContentPane().add(cmbJurusita);

		JLabel lblWilayah = new JLabel("Wilayah");
		lblWilayah.setBounds(26, 78, 52, 16);
		frame.getContentPane().add(lblWilayah);

		txtWilayah = new JPlaceholderTextField("");
		txtWilayah.setEditable(false);
		txtWilayah.setBorder(null);
		txtWilayah.setOpaque(false);
		txtWilayah.setBounds(26, 92, 239, 26);
		frame.getContentPane().add(txtWilayah);
		txtWilayah.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.desktop);
		separator.setBounds(26, 115, 255, 8);
		frame.getContentPane().add(separator);

		cmbWilayah = new JComboBox<>();
		cmbWilayah.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbWilayah.getSelectedIndex() > 0) {
					if (!edit) {
						if (txtWilayah.getText().equals("")) {
							txtWilayah.setText(cmbWilayah.getSelectedItem().toString());
						} else {
							txtWilayah.setText(txtWilayah.getText() + " " + cmbWilayah.getSelectedItem().toString());
						}
					} else {
						txtWilayah.setText(cmbWilayah.getSelectedItem().toString());
					}
				}
			}
		});
		cmbWilayah.setBorder(null);
		cmbWilayah.setBounds(26, 118, 255, 26);
		frame.getContentPane().add(cmbWilayah);

		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut", "Konfirmasi", 0);
				if (!edit) {
					if (konfirm == 0) {
						Store();
					}
					FMain.window.frame.setEnabled(true);
					PWilayah.Find("");
					window.frame.dispose();
				} else {
					if (konfirm == 0) {
						Update(nip, wilayah);
					}
					FMain.window.frame.setEnabled(true);
					PWilayah.Find("");
					window.frame.dispose();
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(".\\icon\\floppy-disk.png"));
		btnSimpan.setBounds(81, 261, 97, 26);
		frame.getContentPane().add(btnSimpan);

		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Kosongkan wilayah?", "Konfirmasi", 0);
				if (konfirm == 0)
					txtWilayah.setText("");
			}
		});
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setIcon(new ImageIcon(".\\icon\\delete.png"));
		label.setBounds(253, 97, 28, 16);
		frame.getContentPane().add(label);

		JLabel lblHari = new JLabel("Hari");
		lblHari.setBounds(26, 156, 52, 16);
		frame.getContentPane().add(lblHari);

		cmbHari = new JComboBox<>();
		cmbHari.setBorder(null);
		cmbHari.setBounds(26, 170, 255, 26);
		frame.getContentPane().add(cmbHari);

		JLabel lblKeterangan = new JLabel("Keterangan");
		lblKeterangan.setBounds(26, 208, 79, 16);
		frame.getContentPane().add(lblKeterangan);

		txtKeterangan = new JPlaceholderTextField("Masukkan keterangan...");
		txtKeterangan.setBorder(null);
		txtKeterangan.setOpaque(false);
		txtKeterangan.setBounds(26, 223, 255, 26);
		frame.getContentPane().add(txtKeterangan);
		txtKeterangan.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(26, 246, 255, 8);
		frame.getContentPane().add(separator_1);

		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setIcon(new ImageIcon(".\\icon\\cancel.png"));
		btnTutup.setBounds(184, 261, 97, 26);
		frame.getContentPane().add(btnTutup);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		ImagePanel lblNewLabel1 = new ImagePanel(".\\icon\\BG Wilayah(1).png");
		frame.getContentPane().add(lblNewLabel1);
	}

	private void SetJurusita() {
		UserDaoImpl uDaoImpl = new UserDaoImpl();
		List<User> users = uDaoImpl.get("");
		cmbJurusita.removeAllItems();
		cmbJurusita.addItem("Pilih Jurusita/Jurusita Pengganti");
		for (User user : users) {
			cmbJurusita.addItem(user.getNip() + " " + user.getNama());
		}
	}

	private void SetWilayah() {
		List<Wilayah> regions = regionDaoImpl.all();
		cmbWilayah.removeAllItems();
		cmbWilayah.addItem("Pilih Wilayah Kecamatan");
		for (Wilayah region : regions) {
			cmbWilayah.addItem(region.getWilayah());
		}
	}

	private void SetHari() {
		cmbHari.removeAllItems();
		cmbHari.addItem("Pilih Hari");
		cmbHari.addItem("Senin");
		cmbHari.addItem("Selasa");
		cmbHari.addItem("Rabu");
		cmbHari.addItem("Kamis");
		cmbHari.addItem("Jumat");
	}

	private String[] StringSplit(String teks) {
		return teks.split(" ");
	}

	private void Find(String nip, String wilayah) {
		region = regionDaoImpl.find(nip, wilayah);
		txtKeterangan.setText(region.getKeterangan());
		txtWilayah.setText(region.getWilayah());
		cmbJurusita.setSelectedItem(region.getNip() + " " + region.getNama());
		cmbWilayah.setSelectedItem(region.getWilayah());
		cmbHari.setSelectedItem(region.getHari());
	}

	private void Store() {
		region.setNip(StringSplit(cmbJurusita.getSelectedItem().toString())[0]);
		region.setHari(cmbHari.getSelectedItem().toString());
		region.setKeterangan(txtKeterangan.getText());
		if (txtWilayah.getText().contains(" ")) {
			regionDaoImpl.insert(region, StringSplit(txtWilayah.getText()));
		} else {
			region.setWilayah(txtWilayah.getText());
			regionDaoImpl.insert(region);
		}
	}

	private void Update(String nip, String wilayah) {
		region.setNip(StringSplit(cmbJurusita.getSelectedItem().toString())[0]);
		region.setHari(cmbHari.getSelectedItem().toString());
		region.setKeterangan(txtKeterangan.getText());
		region.setWilayah(txtWilayah.getText());
		regionDaoImpl.update(region, nip, wilayah);
	}
}
