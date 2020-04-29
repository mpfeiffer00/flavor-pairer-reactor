package com.tank.flavorpairer.importer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;

// https://github.com/itext/itext7
// https://itextpdf.com/en/resources/examples/itext-7/parsing-pdfs
public class FlavorBibleChunkTextExtractor {
	public static final String DEST = "./target/txt/the_flavor_bible.txt";
	public static final String SRC = "./target/txt/the_flavor_bible.pdf";

	public static void main(String[] args) throws IOException {
		final File file = new File(DEST);
		file.getParentFile().mkdirs();

		new FlavorBibleChunkTextExtractor().manipulatePdfOriginal(DEST);
	}

	public void manipulatePdfOriginal(String dest) throws IOException {
		final PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC));

		final Rectangle rect = new Rectangle(pdfDoc.getDefaultPageSize().getWidth(),
				pdfDoc.getDefaultPageSize().getHeight());
		final FilteredEventListener listener = new FilteredEventListener();

		final ChunkTextExtractionStrategy extractionStrategy = listener
				.attachEventListener(new ChunkTextExtractionStrategy());

		// Ingredients: 76-1163
		final PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
		parser.processPageContent(pdfDoc.getPage(111));
		parser.processPageContent(pdfDoc.getPage(112));
		parser.processPageContent(pdfDoc.getPage(113));
		parser.processPageContent(pdfDoc.getPage(114));

		final List<FlavorBibleIngredient> flavorBibleIngredients = extractionStrategy.getFlavorBibleIngredients();

		pdfDoc.close();

		flavorBibleIngredients.forEach(System.out::println);

//		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest)))) {
//			writer.write(actualText);
//		}
	}

	private static final class IngredientHeadingFilter extends TextRegionEventFilter {
		public IngredientHeadingFilter(Rectangle filterRect) {
			super(filterRect);
		}

		@Override
		public boolean accept(IEventData data, EventType type) {
			if (type.equals(EventType.END_TEXT)) {
				return true;
			}

			if (!type.equals(EventType.RENDER_TEXT)) {
				return false;
			}

			final TextRenderInfo renderInfo = (TextRenderInfo) data;
			final PdfFont font = renderInfo.getFont();
			if (font == null) {
				return false;
			}

			final String fontName = font.getFontProgram().getFontNames().getFontName();
			final boolean isHeading = fontName.endsWith("Bold") && renderInfo.getFontSize() == 28.0;
			if (!isHeading) {
				return false;
			}

			return true;
		}
	}

	private static final class IngredientFilter extends TextRegionEventFilter {
		public IngredientFilter(Rectangle filterRect) {
			super(filterRect);
		}

		@Override
		public boolean accept(IEventData data, EventType type) {
			if (type.equals(EventType.END_TEXT)) {
				return true;
			}

			if (!type.equals(EventType.RENDER_TEXT)) {
				return false;
			}

			final TextRenderInfo renderInfo = (TextRenderInfo) data;
			final PdfFont font = renderInfo.getFont();
			if (font == null) {
				return false;
			}

			final boolean isNormalText = renderInfo.getFontSize() == 20.0;
			if (!isNormalText) {
				return false;
			}

			return true;
		}
	}
}
