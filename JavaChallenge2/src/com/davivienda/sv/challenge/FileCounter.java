package com.davivienda.sv.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FileCounter {
	
	final static String CHAPTER_SEARCH_EXPRESSION = "(?m)(?=^CHAPTER\\s+[IVXLCDM]+\\s*$)";
	final static String NULLPOINTEREXCEPTION_MSG="Error por propiedad nula, verificar por favor";
	final static String PATTERNSYNTAXEXCEPTION_MSG="Excepcion causada al utilizar la expresion regular";
	
	private String fileContent;
	private static final Logger logger = Logger.getLogger(FileCounter.class.getName());

	public FileCounter(String content) {
		if(content==null) {
			throw new IllegalArgumentException("El content no puede ser null");
		}	 
		this.fileContent = content;
	}

	/**
	 * @return Retorna la cantidad de capitulos en el libro
	 * @author Henry Cortez
	 */
	public int getChapterCount() {
		int numCoincidencias = 0;
		try {
			Pattern pattern = Pattern.compile(CHAPTER_SEARCH_EXPRESSION);
			Matcher matcher = pattern.matcher(this.fileContent);
			while (matcher.find()) {
				numCoincidencias++;
			}
		} catch (NullPointerException e) {
			logger.severe(NULLPOINTEREXCEPTION_MSG);
			e.printStackTrace();
		} catch (PatternSyntaxException e) {
			logger.severe(PATTERNSYNTAXEXCEPTION_MSG);
			e.printStackTrace();
		} catch (Exception e) {
			logger.severe("Error generico al tratar de obtener la cantidad de capitulos en el libro");
			e.printStackTrace();
		}

		return numCoincidencias;
	}

	/**
	 * @return Retorna la cantidad de veces que es mencionado le nombre de Dracula en el libro
	 * @author Henry Cortez
	 */
	public int getDraculaMentionCount() {
		int numMatches = 0;
		String regex = "(?m)Dracula";
		Pattern patron = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		try {
			Matcher matcher = patron.matcher(this.fileContent);
			while (matcher.find()) {
				numMatches++;
			}
		} catch (NullPointerException e) {
			logger.severe(NULLPOINTEREXCEPTION_MSG);
			e.printStackTrace();
		} catch (PatternSyntaxException e) {
			logger.severe(PATTERNSYNTAXEXCEPTION_MSG);
			e.printStackTrace();
		} catch (Exception e) {
			logger.severe("Error generico al tratar de obtener las menciones de Dracula en el libro");
			e.printStackTrace();
		}
		return numMatches;
	}

	/**
	 * @return Retorna el capitulo con mas lineas de contenido en el libro
	 * @author Henry Cortez
	 */
	public int getBiggestChapter() {
		String capsContent = this.getCapsContent(this.fileContent);
		// divido los capitulos por medio del patron identificado
		String[] caps = capsContent.split(CHAPTER_SEARCH_EXPRESSION);
		int numLines = 0;
		int maxLineCap = 0;
		try {
			for (int i = 0; i < caps.length; i++) {
				// separa en captulo por salto de linea para obtener todas las lineas que contiene
				String[] capLines = caps[i].split("\n");
				StringBuilder capNoEmptyLines = new StringBuilder();
				Arrays.stream(capLines).forEach(linea -> {
					if (!linea.isEmpty()) {
						// solo agrego las lineas que tienen contenido al StringBuilder
						capNoEmptyLines.append(linea).append("\n");
					}
				});
				// separo las en lineas de nuevo para poder determinar el cap con mayor numero de lineas con contenido
				capLines = capNoEmptyLines.toString().split("\n");
				if (capLines.length > numLines) {
					numLines = capLines.length;
					maxLineCap = i;
				}
			}
			maxLineCap = maxLineCap + 1;
		} catch (NullPointerException e) {
			logger.severe(NULLPOINTEREXCEPTION_MSG);
			e.printStackTrace();
		} catch (PatternSyntaxException e) {
			logger.severe(PATTERNSYNTAXEXCEPTION_MSG);
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.severe("Error al tratar de trabajar con un arreglo favor verificar las variables de tipo Array");
			e.printStackTrace();
		} catch (Exception e) {
			logger.severe("Error generico al tratar de obtener el capitulo mas grande del libro");
			e.printStackTrace();
		}
		return (maxLineCap);
	}

	/**
	 * @return Retorna la lista de fechas en las cartas, diarios y memorandums mencionados en le libro, en formato String tal cual como aparecen en el libro. Ej. 30 October, 4 November, ...
	 * @author Henry Cortez
	 */
	public List<String> getLetterDates() {
		List<String> fechasEncontradas = new ArrayList<String>();
		String regex = "\\b(?:_)?(\\d{1,2}\\s+(?:January|February|March|April|May|June|July|August|September|October|November|December))(?:_)?\\b";
		try {
			String capsContent = this.getCapsContent(this.fileContent);
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(capsContent);
			while (matcher.find()) {
				fechasEncontradas.add(matcher.group(1));
			}
		} catch (PatternSyntaxException e) {
			logger.severe(PATTERNSYNTAXEXCEPTION_MSG);
			e.printStackTrace();
		} catch (NullPointerException e) {
			logger.severe(NULLPOINTEREXCEPTION_MSG);
			e.printStackTrace();
		} catch (Exception e) {
			logger.severe("Error generico al tratar de obtener el listado de fechas en el libro");
			e.printStackTrace();
		}
		return fechasEncontradas;
	}

	/**
	 * @return Retorna la lista de remitentes de las cartas, diarios y memorandums mencoinados en el libro, en formato String tal cual como aparece en el libro. Ej. Jonathan Harker’s Journal, Dr. Van Helsing’s Memorandum, Dr. Seward’s Diary...
	 * @author Henry Cortez
	 */
	public List<String> getLetterRecipients() {
		List<String> recipient = new ArrayList<String>();
		String regex = "(?<!\\()_(.*?(?:Journal|Diary|Memorandum))(?:\\.)?_";
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(this.fileContent);
			while (matcher.find()) {
				recipient.add(matcher.group(1));
			}
		} catch (PatternSyntaxException e) {
			logger.severe(PATTERNSYNTAXEXCEPTION_MSG);
			e.printStackTrace();
		} catch (NullPointerException e) {
			logger.severe(NULLPOINTEREXCEPTION_MSG);
			e.printStackTrace();
		} catch (Exception e) {
			logger.severe("Error generico al tratar de obtener el listado de recipients en el libro");
			e.printStackTrace();
		}
		return recipient;
	}

//	-----------------Otros metodos -------------------------------------------
	private String getCapsContent(String contenidoLibro) {
		int posicionInicial = capsInitialPosition(contenidoLibro);
		int posicionFinal = capsFinalPosition(contenidoLibro);
		return contenidoLibro.substring(posicionInicial, posicionFinal);
	}

	private int capsInitialPosition(String contenidoLibro) {
		int posicionInicial = 0;
		Pattern patron = Pattern.compile(CHAPTER_SEARCH_EXPRESSION);
		Matcher matcher = patron.matcher(contenidoLibro);
		if (matcher.find()) {
			posicionInicial = matcher.start();
		}
		return posicionInicial;
	}

	private int capsFinalPosition(String contenidoLibro) {
		int posicionFinal = 0;
		Pattern patron = Pattern.compile("\\b\\s*THE END\\s*\\b");
		Matcher matcher = patron.matcher(contenidoLibro);
		if (matcher.find()) {
			posicionFinal = matcher.end();
		}
		return posicionFinal;
	}
	

	public int getDraculaMentionCount(String term) {
		int numMatches = 0;
		String regex = "(?m)"+(term!=null? term:"");
		Pattern patron = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		try {
			Matcher matcher = patron.matcher(this.fileContent);
			while (matcher.find()) {
				numMatches++;
			}
		} catch (NullPointerException e) {
			logger.severe(NULLPOINTEREXCEPTION_MSG);
			e.printStackTrace();
		} catch (PatternSyntaxException e) {
			logger.severe(PATTERNSYNTAXEXCEPTION_MSG);
			e.printStackTrace();
		} catch (Exception e) {
			logger.severe("Error generico al tratar de obtener las menciones de Dracula en el libro");
			e.printStackTrace();
		}
		return numMatches;
	}
}
