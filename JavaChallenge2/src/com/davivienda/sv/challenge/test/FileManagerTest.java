package com.davivienda.sv.challenge.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.davivienda.sv.challenge.FileManager;

class FileManagerTest {

	@Test
	void test() {
	try {
		String content = FileManager.loadFile();
        assertNotNull(content);
	} catch (IOException | InterruptedException e) {
		 fail("Se produjo una excepción al cargar el contenido del archivo desde la URL configurada: " + e.getMessage());
	}
	}

}
