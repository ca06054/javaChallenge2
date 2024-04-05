package com.davivienda.sv.challenge;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class EntryPoint {
	private static final Logger logger = Logger.getLogger(EntryPoint.class.getName());

	public static void main(String[] args) {

		FileCounter counter = getFileCounter();

		int chapterCount = getChapterCount(counter);
		logger.info("Cantidad de capitulos en el libro: " + chapterCount + "\n");

		int draculaMentions = getDraculaMentionCount(counter);
		logger.info("Dracula  fue encontrado en el libro: " + draculaMentions + " veces\n");

		int biggestChapter = getBiggestChapter(counter);
		logger.info("El capitulo mas grande es el capitulo numero: " + biggestChapter + "\n");

		List<String> letterDates = getLetterDates(counter);
		logger.info("fechas encontradas:\n" + letterDates + "\n");

		List<String> remitentes = getLetterRecipients(counter);
		logger.info("Remitentes encontrados:\n" + remitentes + "\n");

	}

	private static FileCounter getFileCounter() {
		String bookText = null;
		FileCounter counter = null;
		try {
			bookText = FileManager.loadFile();
			counter = new FileCounter(bookText);
		} catch (IOException e) {
	        logger.severe("Error de tipo IOException al cargar el contenido del libro: " + e.getMessage());
		} catch (InterruptedException e) {
			 logger.severe("Error de tipo InterruptedException al cargar el contenido del libro: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			 logger.severe("Error de tipo IllegalArgumentException al inicializar el objeto counter: " + e.getMessage());
		}
		return counter;

	}

	private static int getChapterCount(FileCounter fileCounter) {
		return fileCounter.getChapterCount();
	}

	private static int getDraculaMentionCount(FileCounter fileCounter) {
		return fileCounter.getDraculaMentionCount();
	}

	private static int getBiggestChapter(FileCounter fileCounter) {
		return fileCounter.getBiggestChapter();
	}

	private static List<String> getLetterDates(FileCounter fileCounter) {
		return fileCounter.getLetterDates();
	}

	private static List<String> getLetterRecipients(FileCounter fileCounter) {
		return fileCounter.getLetterRecipients();
	}
}
