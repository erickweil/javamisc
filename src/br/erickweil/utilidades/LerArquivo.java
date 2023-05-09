package br.erickweil.utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class LerArquivo {
	
	@FunctionalInterface
	public static interface LerArquivoListener {
		/**
		 * Retorne falso para parar de ler o arquivo
		*/
		boolean leuLinha(String linha);
	}

	// https://www.baeldung.com/java-read-lines-large-file
	public static void linhaPorLinha(String caminho,LerArquivoListener listener) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(caminho);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        
		        boolean terminar = listener.leuLinha(line);
		        if(terminar) break;
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
}
