package views.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatIntelliJLaf;

import config.ImagePanel;
import config.JPlaceholderPasswordField;
import config.JPlaceholderTextField;
import daoimpl.AuthDaoImpl;
import entities.User;
import java.awt.Toolkit;

public class FLogin {

	JFrame frame;
	static FLogin window;
	private JTextField txtUser;
	private JPasswordField pwdPwd;
	boolean reportInitial = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(FlatIntelliJLaf.class.getName());
		} catch(Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new FLogin();
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
	public FLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(245, 222, 179));
		frame.setBounds(100, 100, 359, 198);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(".\\icon\\Logo ESIAP.ico"));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menutup aplikasi?", "Konfirmasi", 0);
				if (konfirm == 0)
					System.exit(0);
			}
		});
		label_1.setIcon(new ImageIcon(".\\icon\\close(1).png"));
		label_1.setBounds(337, 6, 16, 21);
		frame.getContentPane().add(label_1);

		txtUser = new JPlaceholderTextField("Masukkan username");
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					Login();
				}
			}
		});
		txtUser.setBorder(null);
		txtUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtUser.setBounds(33, 75, 298, 32);
		txtUser.setOpaque(false);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);

		pwdPwd = new JPlaceholderPasswordField("Masukkan password");
		pwdPwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					Login();
				}
			}
		});
		pwdPwd.setBorder(null);
		pwdPwd.setOpaque(false);
		pwdPwd.setHorizontalAlignment(SwingConstants.LEFT);
		pwdPwd.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		pwdPwd.setBounds(33, 112, 298, 32);
		frame.getContentPane().add(pwdPwd);

		JButton btnBatal = new JButton("Batal");
		btnBatal.setIcon(new ImageIcon(".\\icon\\cancel.png"));
		btnBatal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menutup aplikasi?", "Konfirmasi", 0);
				if (konfirm == 0)
					System.exit(0);
			}
		});
		btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnBatal.setBounds(234, 152, 97, 31);
		frame.getContentPane().add(btnBatal);

		JButton btnLogin = new JButton("Login");
		btnLogin.setIcon(new ImageIcon(".\\icon\\log-in.png"));
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Login();
			}
		});
		btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnLogin.setBounds(130, 152, 97, 31);
		frame.getContentPane().add(btnLogin);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.GRAY);
		separator_1.setBackground(Color.GRAY);
		separator_1.setBounds(33, 104, 298, 16);
		frame.getContentPane().add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.GRAY);
		separator_2.setBounds(33, 141, 298, 16);
		frame.getContentPane().add(separator_2);

		ImagePanel lblNewLabel1 = new ImagePanel(new ImageIcon(".\\icon\\BG Login(1).png").getImage());
		frame.getContentPane().add(lblNewLabel1);
	}

	private void DataBaru() {
		txtUser.setText("");
		pwdPwd.setText("");
	}

	@SuppressWarnings("deprecation")
	private void Login() {
		AuthDaoImpl auth = new  AuthDaoImpl();
		User user = auth.auth(txtUser.getText(), pwdPwd.getText());
		if (user.getUsername().equals("") || user.getUsername().equals(null)) {
			JOptionPane.showMessageDialog(null, "Username dan password tidak valid");
			DataBaru();
		} else {
			FMain.main(null);
			FMain.user = user.getUsername();
			FMain.level = user.getLevel();
			FMain.nama = user.getNama();
			if (!reportInitial)
				auth.initReport();
			window.frame.dispose();
		}
	}
}
