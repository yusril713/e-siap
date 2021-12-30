package config;

import java.text.DecimalFormat;

public class NumberFormat {
	public static String FormatAngka(int Angka){
		java.text.NumberFormat numberFormatter = new DecimalFormat("#,##0");
		return numberFormatter.format(Angka);
	}
}
