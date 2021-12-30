package views.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import config.ImagePanel;
import config.NumberFormat;
import daoimpl.PanggilanDaoImpl;
import entities.Panggilan;
import views.frame.FMain;
import views.frame.FPanggilan;

public class PSidang extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JXTable table;
	private JScrollPane scrollPane;
	private JXSearchField searchField;

	/**
	 * Create the panel.
	 */
	public PSidang() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				TampilData("");
			}
		});
		setForeground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 249, 0, 0, 0, 0, 20};
		gridBagLayout.rowHeights = new int[]{0, 0, 19, 0, 82, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
		setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Data Panggilan Sidang");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 5;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);

		searchField = new JXSearchField();
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
		gbc_searchField.gridy = 3;
		add(searchField, gbc_searchField);

		scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);

		table = new JXTable();
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		scrollPane.setViewportView(table);

		JButton btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FPanggilan.main(null);
				FMain.window.frame.setEnabled(false);
			}
		});
		btnTambah.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnTambah.setIcon(new ImageIcon(".\\icon\\add.png"));
		GridBagConstraints gbc_btnTambah = new GridBagConstraints();
		gbc_btnTambah.insets = new Insets(0, 0, 5, 5);
		gbc_btnTambah.gridx = 3;
		gbc_btnTambah.gridy = 5;
		add(btnTambah, gbc_btnTambah);

		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut", "Konfirmasi", 0);
					if (konfirm == 0) {
						Hapus(table.getValueAt(table.getSelectedRow(), 0).toString());
						TampilData("");
					}
				}
			}
		});
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnHapus.setIcon(new ImageIcon(".\\icon\\delete.png"));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 5;
		gbc_btnHapus.gridy = 5;
		add(btnHapus, gbc_btnHapus);

		ImagePanel lblNewLabel1 = new ImagePanel(".\\icon\\BG menu.png");
		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.gridx = 0;
		gbc_lblNewLabel1.gridy = 0;
		gbc_lblNewLabel1.gridheight = 7;
		gbc_lblNewLabel1.gridwidth = 8;
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
		model.addColumn("Jurusita");
		model.addColumn("Kecamatan");
		model.addColumn("Biaya Pbt/Pgl");

		PanggilanDaoImpl pDaoImpl = new PanggilanDaoImpl();
		List<Panggilan> p = pDaoImpl.get(key);
		for (Panggilan i : p) {
			model.addRow(new Object [] {
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
					NumberFormat.FormatAngka(i.getBiaya())
			});
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
		table.getColumnModel().getColumn(10).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);

		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 16));
	}

	private void Hapus(String id) {
		PanggilanDaoImpl pDaoImpl = new PanggilanDaoImpl();
		pDaoImpl.destroy(id);
	}
}
