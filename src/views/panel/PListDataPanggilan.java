package views.panel;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import config.Database;
import config.ImagePanel;
import config.NumberFormat;
import daoimpl.PanggilanDaoImpl;
import entities.Panggilan;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import views.frame.FEditBiaya;
import views.frame.FMain;

public class PListDataPanggilan extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static JXTable table;
	private JButton btnPrint;
	private JButton btnEdit;

	/**
	 * Create the panel.
	 */
	public PListDataPanggilan() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				btnPrint.setVisible(true);

				TampilData("");
				btnEdit.setVisible(false);
				if (FMain.level == 3) {
					btnEdit.setVisible(true);
				}
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 230, 0, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JXSearchField searchField = new JXSearchField();
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			public void EventChanged() {
				TampilData(searchField.getText());
			}
		});
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 1;
		add(searchField, gbc_searchField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);

		table = new JXTable();
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		scrollPane.setViewportView(table);

		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					if (table.getValueAt(table.getSelectedRow(), 1).toString().equals("PBT"))
						CetakPemberitahuan();
					else if (table.getValueAt(table.getSelectedRow(), 1).toString().equals("TUNDA"))
						CetakTunda();
					else if (table.getValueAt(table.getSelectedRow(), 1).toString().equals("IKRAR"))
						CetakIkrar();
					else
						CetakPanggilan();
				} else
					JOptionPane.showMessageDialog(null, "Pilih data yang akan di print...");
			}
		});
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0 ) {
					FEditBiaya.main(null);
					FEditBiaya.id = table.getValueAt(table.getSelectedRow(), 0).toString();
					FMain.window.frame.setEnabled(false);
				}
			}
		});
		btnEdit.setIcon(new ImageIcon(".\\icon\\edit.png"));
		btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 3;
		gbc_btnEdit.gridy = 3;
		add(btnEdit, gbc_btnEdit);
		btnPrint.setIcon(new ImageIcon(".\\icon\\printer.png"));
		btnPrint.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnPrint = new GridBagConstraints();
		gbc_btnPrint.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrint.gridx = 4;
		gbc_btnPrint.gridy = 3;
		add(btnPrint, gbc_btnPrint);

		ImagePanel lblNewLabel1 = new ImagePanel(".\\icon\\BG menu.png");
		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.gridx = 0;
		gbc_lblNewLabel1.gridy = 0;
		gbc_lblNewLabel1.gridheight = 5;
		gbc_lblNewLabel1.gridwidth = 5;
		add(lblNewLabel1, gbc_lblNewLabel1);
	}

	public static void TampilData(String key) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("id");
		model.addColumn("jenis");
		model.addColumn("No Perkara");
		model.addColumn("Tgl Putus");
		model.addColumn("Tgl Sidang/selanjutnya");
		model.addColumn("Pgl/Pbt Untuk");
		model.addColumn("Nama");
		model.addColumn("Alamat");
		model.addColumn("Panitera");
		if (FMain.level != 3)
			model.addColumn("Jurusita");
		model.addColumn("Kelurahan");
		model.addColumn("Biaya Pbt/Pgl");

		if (FMain.level == 1 || FMain.level == 2 || FMain.level == 3) {
			PanggilanDaoImpl pDaoImp = new PanggilanDaoImpl();
			List<Panggilan> p = pDaoImp.get(key, FMain.level);

			if (FMain.level != 3) {
				for (Panggilan i : p) {
					model.addRow(new Object[] {
							i.getId(),
							i.getJenis(),
							i.getPerkaraNo(),
							i.getTglPutus(),
							i.getTglSidang(),
							i.getPglPbt(),
							i.getNama(),
							i.getAlamat(),
							i.getNamaPp(),
							i.getJurusita(),
							i.getKecamatan(),
							i.getBiaya()
					});
				}
			} else {
				for (Panggilan i : p) {
					model.addRow(new Object[] {
							i.getId(),
							i.getJenis(),
							i.getPerkaraNo(),
							i.getTglPutus(),
							i.getTglSidang(),
							i.getPglPbt(),
							i.getNama(),
							i.getAlamat(),
							i.getNamaPp(),
							i.getKecamatan(),
							i.getBiaya()
					});
				}
			}
		} else {
			PanggilanDaoImpl pDaoImp = new PanggilanDaoImpl();
			String[] kec = new String[FMain.kecamatan.size()];
			kec = FMain.kecamatan.toArray(kec);
			List<Panggilan> p = pDaoImp.get(key, FMain.level, kec);
			for (Panggilan i : p) {
				model.addRow(new Object[] {
						i.getId(),
						i.getJenis(),
						i.getPerkaraNo(),
						i.getTglPutus(),
						i.getTglSidang(),
						i.getPglPbt(),
						i.getNama(),
						i.getAlamat(),
						i.getNamaPp(),
						i.getKecamatan(),
						NumberFormat.FormatAngka(i.getBiaya())
				});
			}
		}
		table.setModel(model);
		table.setAutoResizeMode(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);
		table.getColumnModel().getColumn(9).setPreferredWidth(120);

		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 16));
	}

	private void CetakPemberitahuan() {
		FMain.window.frame.setEnabled(false);
		Database db = new Database();
		try {
			db.con();
			Map<String, Object> param = new HashMap<>();

			String reportName = ".\\reports\\Pemberitahuan.jasper";
			param.put("paramId", table.getValueAt(table.getSelectedRow(), 0).toString());
			param.put("paramLevel", FMain.level);

			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | Frame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FMain.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);

			db.con.close();
		} catch (Exception e) {
			FMain.window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}

	private void CetakIkrar() {
		FMain.window.frame.setEnabled(false);
		Database db = new Database();
		try {
			db.con();
			Map<String, Object> param = new HashMap<>();

			String reportName = ".\\reports\\Ikrar.jasper";
			param.put("paramId", table.getValueAt(table.getSelectedRow(), 0).toString());
			param.put("paramLevel", FMain.level);

			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | Frame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FMain.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);

			db.con.close();
		} catch (Exception e) {
			FMain.window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}

	private void CetakTunda() {
		FMain.window.frame.setEnabled(false);
		Database db = new Database();
		try {
			db.con();
			Map<String, Object> param = new HashMap<>();

			String reportName = ".\\reports\\Tunda.jasper";
			param.put("paramId", table.getValueAt(table.getSelectedRow(), 0).toString());
			param.put("paramLevel", FMain.level);

			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | Frame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FMain.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);

			db.con.close();
		} catch (Exception e) {
			FMain.window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}

	private void CetakPanggilan() {
		FMain.window.frame.setEnabled(false);
		Database db = new Database();
		try {
			db.con();
			Map<String, Object> param = new HashMap<>();

			String reportName = ".\\reports\\Panggilan.jasper";
			param.put("paramId", table.getValueAt(table.getSelectedRow(), 0).toString());
			param.put("paramLevel", FMain.level);

			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | Frame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FMain.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);

			db.con.close();
		} catch (Exception e) {
			FMain.window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan " + e);
		}
	}
}
