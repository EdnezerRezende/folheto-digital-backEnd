package br.com.pic.folheto.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataUtil {

	public static String obterDataGeracaoBoletim(LocalDate dataHoje) {
		final DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy").withLocale(new Locale("pt", "br"));
		
		final DayOfWeek dayOfWeek = dataHoje.getDayOfWeek();
		
		dataHoje = validarEObterDataDomingo(dataHoje, dayOfWeek);
		
		return dataHoje.format(parser);
	}
	
	public static LocalDate obterDataInicialBoletimSemana() {
		final DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy").withLocale(new Locale("pt", "br"));
		final String dataBoletimString = obterDataGeracaoBoletim(LocalDate.now());
		
		return LocalDate.parse(dataBoletimString,parser); 
	}
	
	public static LocalDate validarEObterDataDomingo( final LocalDate dataHoje, final DayOfWeek dayOfWeek) {
		if(dayOfWeek== DayOfWeek.MONDAY) {
			return LocalDate.now().minusDays(1);
		} else if(dayOfWeek== DayOfWeek.TUESDAY) {
			return  dataHoje.minusDays(2);
		} else if(dayOfWeek== DayOfWeek.WEDNESDAY) {
			return  dataHoje.minusDays(3);
		} else if(dayOfWeek== DayOfWeek.THURSDAY) {
			return  dataHoje.minusDays(4);
		} else if(dayOfWeek== DayOfWeek.FRIDAY) {
			return  dataHoje.minusDays(5);
		} else if(dayOfWeek== DayOfWeek.SATURDAY) {
			return  dataHoje.minusDays(6);
		}

		return dataHoje;
	}
	
	public static LocalDate converterStringParaLocalDate(final String data) {
		return LocalDate.parse(data);
	}

	public static String converterLocalDateForStringWithFormatter(final LocalDate data, final String formato){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
		return data.format(formatter);
	}
	
	public static LocalDate converterStringParaLocalDateFormatado(final String data) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(data, formatter);
	}

	public static LocalDate converterStringLocalDate(final String data, final String formato){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
		return LocalDate.parse(data, formatter);
	}


	public static LocalDateTime converterStringLocalDateTime(final String data, final String formato){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato).withLocale(new Locale("pt", "br"));
		return LocalDateTime.parse(data, formatter);
	}

	public static LocalDateTime converterStringParaLocalDateTime(final String data) {
		return LocalDateTime.parse(data);
	}

	public static LocalDateTime converterStringParaLocalDateTimeFormatado(final String data) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.parse(data, formatter);
	}

	public static LocalTime converterStringParaLocalTimeFormatado(final String hora) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return LocalTime.parse(hora, formatter);
	}




}
