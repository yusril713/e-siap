package views.panel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
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

import config.Database;
import config.ImagePanel;
import daoimpl.WilayahDaoImpl;
import entities.Wilayah;
import views.frame.FMain;
import views.frame.FTambahWilayah;

public class PWilayah extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JXTable table;
	private static WilayahDaoImpl regionDaoImpl;

	public PWilayah() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				new Database();
				new Wilayah();
				regionDaoImpl = new WilayahDaoImpl();
				Find("");
			}
		});
		setBackground(SystemColor.text);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 227, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 18, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblDataWilayah = new JLabel("Data Wilayah");
		lblDataWilayah.setFont(new Font("Segoe UI", Font.BOLD, 22));
		GridBagConstraints gbc_lblDataWilayah = new GridBagConstraints();
		gbc_lblDataWilayah.gridwidth = 7;
		gbc_lblDataWilayah.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataWilayah.gridx = 1;
		gbc_lblDataWilayah.gridy = 1;
		add(lblDataWilayah, gbc_lblDataWilayah);

		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.controlText);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 7;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);

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
				Find(searchField.getText());
			}
		});
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 3;
		add(searchField, gbc_searchField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
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
				FTambahWilayah.main(null);
				FMain.window.frame.setEnabled(false);
			}
		});
		btnTambah.setIcon(new ImageIcon(".\\icon\\add.png"));
		btnTambah.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnTambah = new GridBagConstraints();
		gbc_btnTambah.insets = new Insets(0, 0, 5, 5);
		gbc_btnTambah.gridx = 5;
		gbc_btnTambah.gridy = 6;
		add(btnTambah, gbc_btnTambah);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FTambahWilayah.main(null);
				FTambahWilayah.edit = true;
				FTambahWilayah.nip = table.getValueAt(table.getSelectedRow(), 0).toString();
				FTambahWilayah.wilayah = table.getValueAt(table.getSelectedRow(), 2).toString();
				FMain.window.frame.setEnabled(false);
			}
		});
		btnEdit.setIcon(new ImageIcon(".\\icon\\edit.png"));
		btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 6;
		gbc_btnEdit.gridy = 6;
		add(btnEdit, gbc_btnEdit);

		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Hapus(table.getValueAt(table.getSelectedRow(), 0).toString(), table.getValueAt(table.getSelectedRow(), 2).toString());
					Find("");
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(".\\icon\\delete.png"));
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 7;
		gbc_btnHapus.gridy = 6;
		add(btnHapus, gbc_btnHapus);
//
		ImagePanel lblNewLabel = new ImagePanel(".\\icon\\BG menu.png");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		gbc_lblNewLabel.gridheight = 10;
		gbc_lblNewLabel.gridwidth = 9;
		add(lblNewLabel, gbc_lblNewLabel);
	}

	public static void Find(String key) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Nip");
		model.addColumn("Nama Jurusita/Jurusita Pengganti");
		model.addColumn("Wilayah Kecamatan");
		model.addColumn("Hari");
		model.addColumn("Keterangan");

		List<Wilayah> regions = regionDaoImpl.get(key);
		try {
			for(Wilayah region : regions) {
				model.addRow(new Object[] {
						region.getNip(),
						region.getNama(),
						region.getWilayah(),
						region.getHari(),
						region.getKeterangan()
				});
			}
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			table.getColumnModel().getColumn(4).setPreferredWidth(200);
			table.setRowHeight(25);
			table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Hapus(String nip, String wilayah) {
		regionDaoImpl.delete(nip, wilayah);
	}
}
