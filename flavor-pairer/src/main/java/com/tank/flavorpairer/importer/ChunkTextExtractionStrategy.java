package com.tank.flavorpairer.importer;

import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

public class ChunkTextExtractionStrategy extends SimpleTextExtractionStrategy {
	private Vector lastStart;
	private Vector lastEnd;
	private final StringBuilder result = new StringBuilder();

	@Override
	public void eventOccurred(IEventData data, EventType type) {
		if (type.equals(EventType.RENDER_TEXT)) {
			final TextRenderInfo renderInfo = (TextRenderInfo) data;
			final boolean firstRender = result.length() == 0;
			boolean hardReturn = false;

			final LineSegment segment = renderInfo.getBaseline();
			final Vector start = segment.getStartPoint();
			final Vector end = segment.getEndPoint();

			if (!firstRender) {
				final Vector x1 = lastStart;
				final Vector x2 = lastEnd;

				// see http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
				final float dist = (x2.subtract(x1)).cross((x1.subtract(start))).lengthSquared()
						/ x2.subtract(x1).lengthSquared();

				// we should probably base this on the current font metrics, but 1 pt seems to
				// be sufficient for the time being
				final float sameLineThreshold = 1f;
				if (dist > sameLineThreshold) {
					hardReturn = true;
				}

				// Note: Technically, we should check both the start and end positions, in case
				// the angle of the text changed without any displacement
				// but this sort of thing probably doesn't happen much in reality, so we'll
				// leave it alone for now
			}

			if (hardReturn) {
				// System.out.println("<< Hard Return >>");
				appendTextChunkz("\n");
			} else if (!firstRender) {
				// we only insert a blank space if the trailing character of the previous string
				// wasn't a space, and the leading character of the current string isn't a space
				if (result.charAt(result.length() - 1) != ' ' && renderInfo.getText().length() > 0
						&& renderInfo.getText().charAt(0) != ' ') {
					final float spacing = lastEnd.subtract(start).length();
					if (spacing > renderInfo.getSingleSpaceWidth() / 2f) {
						appendTextChunkz(" ");
						// System.out.println("Inserting implied space before '" + renderInfo.getText()
						// + "'");
					}
				}
			} else {
				// System.out.println("Displaying first string of content '" + text + "' :: x1 =
				// " + x1);
			}

			// System.out.println("[" + renderInfo.getStartPoint() + "]->[" +
			// renderInfo.getEndPoint() + "] " + renderInfo.getText());
			appendTextChunkz(renderInfo.getText());

			lastStart = start;
			lastEnd = end;
		}
	}

	/**
	 * Returns the result so far.
	 * 
	 * @return a String with the resulting text.
	 */
	@Override
	public String getResultantText() {
		return result.toString();
	}

	/**
	 * Used to actually append text to the text results. Subclasses can use this to
	 * insert text that wouldn't normally be included in text parsing (e.g. result
	 * of OCR performed against image content)
	 * 
	 * @param text the text to append to the text results accumulated so far
	 */
	protected final void appendTextChunkz(CharSequence text) {
		result.append(text);
	}
}
