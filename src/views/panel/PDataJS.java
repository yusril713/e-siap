package views.panel;

import java.awt.Color;
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
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import config.ImagePanel;
import daoimpl.UserDaoImpl;
import entities.User;
import views.frame.FMain;
import views.frame.FTambahJS;

public class PDataJS extends JPanel {
	private static final long serialVersionUID = 1L;
	private JXSearchField searchField;
	private static JXTable table;
	private UserDaoImpl udaoImpl;

	/**
	 * Create the panel.
	 */
	public PDataJS() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				udaoImpl = new UserDaoImpl();
				Find("");
			}
		});
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 200, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 10, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblDataJurusita = new JLabel("DATA PENGGUNA");
		lblDataJurusita.setFont(new Font("Segoe UI", Font.BOLD, 22));
		GridBagConstraints gbc_lblDataJurusita = new GridBagConstraints();
		gbc_lblDataJurusita.gridwidth = 6;
		gbc_lblDataJurusita.insets = new Insets(0, 0, 5, 0);
		gbc_lblDataJurusita.gridx = 1;
		gbc_lblDataJurusita.gridy = 1;
		add(lblDataJurusita, gbc_lblDataJurusita);

		JSeparator separator = new JSeparator();
		separator.setBackground(UIManager.getColor("ComboBox.foreground"));
		separator.setForeground(UIManager.getColor("ComboBox.foreground"));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 6;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		add(separator, gbc_separator);

		searchField = new JXSearchField();
		searchField.setBackground(SystemColor.menu);
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
		gbc_searchField.fill = GridBagConstraints.BOTH;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 3;
		add(searchField, gbc_searchField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
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
			public void actionPerformed(ActionEvent arg0) {
				FTambahJS.main(null);
				FTambahJS.edit = false;
				FMain.window.frame.setEnabled(false);
			}
		});
		btnTambah.setIcon(new ImageIcon(".\\icon\\add.png"));
		btnTambah.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnTambah = new GridBagConstraints();
		gbc_btnTambah.insets = new Insets(0, 0, 5, 5);
		gbc_btnTambah.gridx = 3;
		gbc_btnTambah.gridy = 5;
		add(btnTambah, gbc_btnTambah);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() < 0)
					JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu");
				else {
					FTambahJS.main(null);
					FTambahJS.edit = true;
					FTambahJS.nip = table.getValueAt(table.getSelectedRow(), 0).toString();
					FMain.window.frame.setEnabled(false);
				}
			}
		});
		btnEdit.setIcon(new ImageIcon(".\\icon\\edit.png"));
		btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 4;
		gbc_btnEdit.gridy = 5;
		add(btnEdit, gbc_btnEdit);

		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Hapus(table.getValueAt(table.getSelectedRow(), 0).toString());
					Find("");
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(".\\icon\\delete.png"));
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 5;
		gbc_btnHapus.gridy = 5;
		add(btnHapus, gbc_btnHapus);

		ImagePanel lblNewLabel = new ImagePanel(".\\icon\\BG menu.png");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		gbc_lblNewLabel.gridheight = 10;
		gbc_lblNewLabel.gridwidth = 7;
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
		model.addColumn("NIP");
		model.addColumn("Nama");
		model.addColumn("Jenis Kelamin");
		model.addColumn("TTL");
		model.addColumn("No Telp");
		model.addColumn("Alamat");
		model.addColumn("Username");
		model.addColumn("Level User");

		UserDaoImpl uDaoImpl = new UserDaoImpl();
		List<User> users = uDaoImpl.get(key);
		for(User u : users) {
			model.addRow(new Object[] {
					u.getNip(),
					u.getNama(),
					u.getJenisKelamin(),
					u.getTempatLahir() + ", " + u.getTglLahir(),
					u.getNoTelp(),
					u.getAlamat(),
					u.getUsername(),
					u.getLevel()
			});
		}
		table.setModel(model);
		table.setAutoResizeMode(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 16));
	}

	private void Hapus(String nip) {
		udaoImpl.delete(nip);
	}
}
