package views.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.HorizontalLayout;

import com.formdev.flatlaf.FlatIntelliJLaf;

import daoimpl.AuthDaoImpl;
import views.panel.PDataJS;
import views.panel.PListDataPanggilan;
import views.panel.PMain;
import views.panel.PPilihTahun;
import views.panel.PSettingDb;
import views.panel.PSidang;
import views.panel.PUser;
import views.panel.PWilayah;

public class FMain {

	public JFrame frame;
	public static FMain window;
	public static String user;
	public static int level;
	public static String nama;
	public static List<String> kecamatan = new ArrayList<>();
	JTabbedPane tabbedPane;
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
					window = new FMain();
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
	public FMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1366, 495);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setExtendedState(frame.getExtendedState()| Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		JLabel lblHome = new JLabel("Home");
		lblHome.setIcon(new ImageIcon(".\\icon\\home.png"));
		lblHome.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHome.setVerticalTextPosition(SwingConstants.BOTTOM);

		JLabel lblDataServer = new JLabel("Database Server");
		lblDataServer.setIcon(new ImageIcon(".\\icon\\database.png"));
		lblDataServer.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDataServer.setVerticalTextPosition(SwingConstants.BOTTOM);

		JLabel lblJs = new JLabel("Data Pengguna");
		lblJs.setIcon(new ImageIcon(".\\icon\\profile.png"));
		lblJs.setHorizontalTextPosition(SwingConstants.CENTER);
		lblJs.setVerticalTextPosition(SwingConstants.BOTTOM);

		JLabel lblWilayah = new JLabel("Wilayah Kecamatan");
		lblWilayah.setIcon(new ImageIcon(".\\icon\\arrival.png"));
		lblWilayah.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWilayah.setVerticalTextPosition(SwingConstants.BOTTOM);

		JLabel lblAkun = new JLabel("Akun");
		lblAkun.setIcon(new ImageIcon(".\\icon\\user(2).png"));
		lblAkun.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAkun.setVerticalTextPosition(SwingConstants.BOTTOM);

		JLabel lblSidang = new JLabel("Sidang");
		lblSidang.setIcon(new ImageIcon(".\\icon\\document.png"));
		lblSidang.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSidang.setVerticalTextPosition(SwingConstants.BOTTOM);

		JLabel lblLaporan = new JLabel("Data Panggilan");
		lblLaporan.setIcon(new ImageIcon(".\\icon\\seo-report.png"));
		lblLaporan.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLaporan.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		JLabel lblTahun= new JLabel("Pilih Tahun");
		lblTahun.setIcon(new ImageIcon(".\\icon\\calendar.png"));
		lblTahun.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTahun.setVerticalTextPosition(SwingConstants.BOTTOM);

		frame.getContentPane().setLayout(new HorizontalLayout());
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Logout akun?", "Konfirmasi", 0);
				if (konfirm == 0) {
					window.frame.dispose();
					FLogin.main(null);
				}
			}
		});
		btnLogout.setOpaque(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorderPainted(false);
		btnLogout.setIcon(new ImageIcon(".\\icon\\logout(1).png"));
		btnLogout.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogout.setVerticalTextPosition(SwingConstants.BOTTOM);

		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		frame.getContentPane().add(tabbedPane);

		tabbedPane.addTab("", new PMain());
		tabbedPane.addTab("", new PPilihTahun());
		tabbedPane.addTab("", new PUser());
		tabbedPane.addTab("", new PSettingDb());
		tabbedPane.addTab("", new PDataJS());
		tabbedPane.addTab("", new PWilayah());
		tabbedPane.addTab("", new PSidang());
		tabbedPane.addTab("", new PListDataPanggilan());
		tabbedPane.addTab("", null);

		tabbedPane.setTabComponentAt(0, lblHome);
		tabbedPane.setTabComponentAt(1, lblTahun);
		tabbedPane.setTabComponentAt(2, lblAkun);
		tabbedPane.setTabComponentAt(3, lblDataServer);
		tabbedPane.setTabComponentAt(4, lblJs);
		tabbedPane.setTabComponentAt(5, lblWilayah);
		tabbedPane.setTabComponentAt(6, lblSidang);
		tabbedPane.setTabComponentAt(7, lblLaporan);
		tabbedPane.setTabComponentAt(8, btnLogout);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				SetAkses();
				if (level == 4) {
					AuthDaoImpl auth = new AuthDaoImpl();
					kecamatan = auth.getKecamatan(user);
					System.out.println(level);
				}
			}
		});
	}

	private void SetAkses() {
		if (level == 2) {
			tabbedPane.remove(5);
			tabbedPane.remove(4);
			tabbedPane.remove(3);
		} else if (level == 3 || level == 4) {
			tabbedPane.remove(6);
			tabbedPane.remove(5);
			tabbedPane.remove(4);
			tabbedPane.remove(3);
			tabbedPane.remove(2);
		}
	}
}
