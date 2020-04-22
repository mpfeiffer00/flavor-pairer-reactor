package com.tank.flavorpairer.importer.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;

public class RenderInfoTextAssistant {

	public static boolean isHeading(TextRenderInfo renderInfo) {
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
