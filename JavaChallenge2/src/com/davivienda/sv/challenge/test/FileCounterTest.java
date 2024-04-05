package com.davivienda.sv.challenge.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.davivienda.sv.challenge.FileCounter;

class FileCounterTest {
	private String fileContent = "LIBRO DE DRACULA PARA TESTS\nTexto autogenerado por extension Lorem Ipsum en vscode\n\nCHAPTER I\nOfficia cupidatat dolor qui quis laborum minim Dracula et nisi eu ipsum nisi ea Dracula enim amet.\nCHAPTER II\nNulla laborum dolor incididunt sit._24 February Irure ipsum fugiat anim esse.\n_Dr. Seward’s Diary._\nPariatur cupidatat ad incididunt cillum ad enim ea ea. Dolor adipisicing minim anim laborum aute id esse sint ea id esse. Anim incididunt adipisicing tempor ea aute qui eu dolore.\n\nTHE END\n";

	/**
	 * Test para validar el funcionamiento del metodo para contar los capitulos en caso de que el contido del libro sea vacio
	 */
	@Test
	public void testNullContentInitialization() {
		try {
			FileCounter fileCounter = new FileCounter(null);
		} catch (IllegalArgumentException e) {
			assertEquals("El content no puede ser null", e.getMessage());
		}
	}
	
	/**
	 * Test para validar el conteo de capitulos en el fileContent de prueba
	 */
	@Test
	void testGetChapterCount() {
		FileCounter fileCounter = new FileCounter(this.fileContent);
		assertEquals(2, fileCounter.getChapterCount());
	}

	/**
	 * Test para validar el funcionamiento del metodo para contar la cantidad de coincidencia de la palabra Dracula en el libro de prueba
	 */
	@Test
	public void testGetDraculaMentionCount() {
		FileCounter fileCounter = new FileCounter(this.fileContent);
		assertEquals(3, fileCounter.getDraculaMentionCount());
	}

	/**
	 * Test para validar el funcionamiento del metodo para obtener el capitulo mas grande en el libro de prueba
	 */
	@Test
	public void testGetBiggestChapter() {
		FileCounter fileCounter = new FileCounter(this.fileContent);
		assertEquals(2, fileCounter.getBiggestChapter());
	}

	/**
	 * Test para validar el funcionamiento del metodo para obtener las fechas en el libro de prueba
	 */
	@Test
	public void testGetLetterDates() {
		FileCounter fileCounter = new FileCounter(this.fileContent);
		List<String> dates = fileCounter.getLetterDates();
		assertNotNull(dates);
		assertEquals(1, dates.size());
		assertTrue(dates.contains("24 February"));
	}

	/**
	 * Test para validar el funcionamiento del metodo para obtener los remitentes en el libro de prueba
	 */
	@Test
	public void testGetLetterRecipients() {
		FileCounter fileCounter = new FileCounter(this.fileContent);
		List<String> recipients = fileCounter.getLetterRecipients();
		assertNotNull(recipients);
		assertEquals(1, recipients.size());
		assertTrue(recipients.contains("Dr. Seward’s Diary"));
	}

	/**
	 * Test para validar el funcionamiento del metodo para contar los capitulos en caso de que el contido del libro sea vacio
	 */
	@Test
	public void testGetChapterCountEmptyBookContent() {
		FileCounter fileCounter = new FileCounter("");
		assertEquals(0, fileCounter.getChapterCount());
	}
}
