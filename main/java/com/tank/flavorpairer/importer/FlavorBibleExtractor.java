package com.tank.flavorpairer.importer;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class FlavorBibleExtractor {

	public static void main(String[] args) throws IOException {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		final File file = new File("C:\\Users\\tank\\Documents\\Cook Books\\The Flavor Bible.pdf");
		final PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
		parser.parse();
		try (COSDocument cosDoc = parser.getDocument()) {
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(76);
			pdfStripper.setEndPage(76);
			final String parsedText = pdfStripper.getText(pdDoc);
			System.out.println(parsedText);
		}

	}

}
