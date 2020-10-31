package br.com.igrejadecristo.folhetodigital.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataUtil {

	public static String obterDataGeracaoBoletim(LocalDate dataHoje) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy").withLocale(new Locale("pt", "br"));
		
		DayOfWeek dayOfWeek = dataHoje.getDayOfWeek();
		
		dataHoje = validarEObterDataDomingo(dataHoje, dayOfWeek);
		
		return dataHoje.format(parser);
	}
	
	public static LocalDate obterDataInicialBoletimSemana() {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy").withLocale(new Locale("pt", "br"));
		String dataBoletimString = obterDataGeracaoBoletim(LocalDate.now());
		
		return LocalDate.parse(dataBoletimString,parser); 
	}
	
	private static LocalDate validarEObterDataDomingo(LocalDate dataHoje, DayOfWeek dayOfWeek) {
		if(dayOfWeek== DayOfWeek.MONDAY) {
			dataHoje = LocalDate.now().minusDays(1);
		} else if(dayOfWeek== DayOfWeek.TUESDAY) {
			dataHoje = dataHoje.minusDays(2);
		} else if(dayOfWeek== DayOfWeek.WEDNESDAY) {
			dataHoje = dataHoje.minusDays(3);
		} else if(dayOfWeek== DayOfWeek.THURSDAY) {
			dataHoje = dataHoje.minusDays(4);
		} else if(dayOfWeek== DayOfWeek.FRIDAY) {
			dataHoje = dataHoje.minusDays(5);
		} else if(dayOfWeek== DayOfWeek.SATURDAY) {
			dataHoje = dataHoje.minusDays(6);
		}
		return dataHoje;
	}
	
	public static LocalDate converterStringParaLocalDate(String data) {
		return LocalDate.parse(data);
	}
	
	public static LocalDate converterStringParaLocalDateFormatado(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(data, formatter);
	}
}
