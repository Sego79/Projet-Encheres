package fr.eni.encheres;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utilitaires {
	private Utilitaires() {}

	/**
	 * Convertit une date envoyée depuis un formulaire en LocalDate
	 * Depuis https://stackoverflow.com/questions/43153424/parsing-html-datetime-local-type-input-into-java-localdatetime
	 * @return LocalDateTime
	 */
	public static LocalDateTime fromHTMLDateTimeLocal(String htmlDateTimeLocal) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		Date date = new Date();
		try {
			date = formatter.parse(htmlDateTimeLocal);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDateTime fromHTMLDateAndTime(String htmlDateLocal, String htmlTimeLocal) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		Date date = new Date();
		try {
			date = formatter.parse(htmlDateLocal+'T'+htmlTimeLocal);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}


}
