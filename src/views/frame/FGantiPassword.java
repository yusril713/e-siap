package views.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

import config.Database;
import config.ImagePanel;
import daoimpl.AuthDaoImpl;

public class FGantiPassword {

	JFrame frame;
	static FGantiPassword window;
	private JPasswordField pwdPassword;
	private JPasswordField pwdKonfirmasi;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new FGantiPassword();
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
	public FGantiPassword() {
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
				pwdPassword.setText("");
				pwdKonfirmasi.setText("");
			}
		});
		frame.setBounds(100, 100, 295, 186);
		frame.getContentPane().setBackground(new Color(245, 222, 179));
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPasswordBaru = new JLabel("Password baru");
		lblPasswordBaru.setBounds(20, 42, 93, 16);
		frame.getContentPane().add(lblPasswordBaru);

		pwdPassword = new JPasswordField();
		pwdPassword.setBorder(null);
		pwdPassword.setOpaque(false);
		pwdPassword.setText("password");
		pwdPassword.setBounds(20, 57, 245, 26);
		frame.getContentPane().add(pwdPassword);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(20, 80, 245, 9);
		frame.getContentPane().add(separator);

		JLabel lblKonfirmasiPassword = new JLabel("Konfirmasi Password");
		lblKonfirmasiPassword.setBounds(20, 101, 132, 16);
		frame.getContentPane().add(lblKonfirmasiPassword);

		pwdKonfirmasi = new JPasswordField();
		pwdKonfirmasi.setBorder(null);
		pwdKonfirmasi.setText("konfirmasi ");
		pwdKonfirmasi.setOpaque(false);
		pwdKonfirmasi.setBounds(20, 116, 245, 26);
		frame.getContentPane().add(pwdKonfirmasi);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(20, 139, 245, 9);
		frame.getContentPane().add(separator_1);

		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setIcon(new ImageIcon(".\\icon\\cancel.png"));
		btnBatal.setBounds(176, 149, 97, 26);
		frame.getContentPane().add(btnBatal);

		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah password?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (pwdPassword.getText().equals(pwdKonfirmasi.getText())) {
						Update(FMain.user);
						FMain.window.frame.setEnabled(true);
						window.frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Pastikan password dan password konfirmasi sama!");
					}
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(".\\icon\\floppy-disk.png"));
		btnSimpan.setBounds(69, 149, 97, 26);
		frame.getContentPane().add(btnSimpan);


		ImagePanel lblNewLabel1 = new ImagePanel(new ImageIcon(".\\icon\\BG Password(1).png").getImage());
		frame.getContentPane().add(lblNewLabel1);
	}

	@SuppressWarnings("deprecation")
	private void Update(String username) {
		AuthDaoImpl auth = new AuthDaoImpl();
		auth.changePassword(username, pwdPassword.getText());
	}
}
