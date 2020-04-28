package com.tank.flavorpairer.importer.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.tank.flavorpairer.importer.PairingLevel;

public class RenderInfoTextAssistant {

	public static PairingLevel determinePairingLevel(TextRenderInfo renderInfo) {
		if (isBold(renderInfo)) {
			if (isAllCapitalized(renderInfo)) {
				return PairingLevel.MARRIAGE;
			}
			return PairingLevel.RECOMMENDED;
		}
		return PairingLevel.NORMAL;
	}

	public static boolean isHeading(TextRenderInfo renderInfo) {
		final PdfFont font = renderInfo.getFont();
		if (font == null) {
			return false;
		}

		return isBold(renderInfo) && renderInfo.getFontSize() == 28.0;
	}

	public static boolean isNormalText(TextRenderInfo renderInfo) {
		final PdfFont font = renderInfo.getFont();
		if (font == null) {
			return false;
		}

		return renderInfo.getFontSize() == 20.0;
	}

	public static boolean isBold(TextRenderInfo renderInfo) {
		final PdfFont font = renderInfo.getFont();
		if (font == null) {
			return false;
		}

		final String fontName = font.getFontProgram().getFontNames().getFontName();
		return fontName.endsWith("Bold");
	}

	private static boolean isAllCapitalized(TextRenderInfo renderInfo) {
		return renderInfo.getText().equals(renderInfo.getText().toUpperCase())
				&& Character.isAlphabetic(renderInfo.getText().charAt(0));
	}
}
