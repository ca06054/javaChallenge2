package com.davivienda.sv.challenge;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class FileManager {
	public static final String FILE_URL = "https://robertux-words.s3.amazonaws.com/pg345.txt";

	/**
	 * Carga el texto contenido en el archivo ubicado en la URL en la constante FILE_URL
	 * 
	 * @return El contenido del arcihvo cargado
	 * @throws InterruptedException
	 * @throws IOException
	 * @author Henry Cortez
	 */
	public static String loadFile() throws IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newHttpClient();
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(FILE_URL))
				.header("Content-Type", "text/plain")
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new IOException("Error al cargar el contenido del libro. Error HTTP: " + response.statusCode());
		}
		return response.body();
	}

}
