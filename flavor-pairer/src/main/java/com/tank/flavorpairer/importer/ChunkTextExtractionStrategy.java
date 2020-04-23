package com.tank.flavorpairer.importer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import com.tank.flavorpairer.importer.util.RenderInfoTextAssistant;

public class ChunkTextExtractionStrategy extends SimpleTextExtractionStrategy {
	private Vector lastStart;
	private Vector lastEnd;
	private final StringBuilder result = new StringBuilder();
	private final List<FlavorBibleIngredient> flavorBibleIngredients = new ArrayList<>();
	private final Set<FlavorBibleIngredient> ingredientPairings = new HashSet<>();
	private final FlavorBibleIngredient currentFlavorBibleIngredient = null;
	private FlavorBibleIngredient currentFlavorBibleIngredientHeading = null;

	private boolean isHeading = false;
	private boolean isFlavorAffinityEntries = false;
	private PairingLevel pairingLevel = PairingLevel.NORMAL;

	@Override
	public void eventOccurred(IEventData data, EventType type) {
		if (type.equals(EventType.RENDER_TEXT)) {
			final TextRenderInfo renderInfo = (TextRenderInfo) data;
			final boolean firstRender = result.length() == 0;
			boolean hardReturn = false;
			isHeading = RenderInfoTextAssistant.isHeading(renderInfo);
			pairingLevel = RenderInfoTextAssistant.determinePairingLevel(renderInfo);

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

		if (type.equals(EventType.END_TEXT) && !getResultantText().isBlank()) {
			if (isHeading) {
				// First heading of file will not have any paired ingredients, can only add at
				// end of heading resolution
				if (currentFlavorBibleIngredientHeading != null) {
					currentFlavorBibleIngredientHeading.addFlavorBibleIngredient(ingredientPairings);
					flavorBibleIngredients.add(currentFlavorBibleIngredientHeading);
				}

				currentFlavorBibleIngredientHeading = new FlavorBibleIngredient();
				currentFlavorBibleIngredientHeading.setIngredientName(getResultantText());
				ingredientPairings.clear();
				isFlavorAffinityEntries = false;
			} else if ("Flavor Affinities".equals(getResultantText())) {
				isFlavorAffinityEntries = true;
			} else if (isFlavorAffinityEntries) {
				currentFlavorBibleIngredientHeading.addFlavorAffinity(getResultantText());
			} else {
				final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
				ingredient.setIngredientName(getResultantText());
				ingredient.setPairingLevel(pairingLevel);
				ingredientPairings.add(ingredient);
			}

			result.replace(0, result.length(), "");
			isHeading = false;
		}

		// TODO: GOING TO LOSE THE LAST HEADING BECAUSE WE DIDN'T FIND THE NEXT ONE
	}

	/**
	 * Returns the result of the chunk in process.
	 * 
	 * @return a String with the current chunk.
	 */
	@Override
	public String getResultantText() {
		return result.toString().replaceAll("\\s", " ");
	}

	public List<FlavorBibleIngredient> getFlavorBibleIngredients() {
		return flavorBibleIngredients;
	}

	@Override
	public Set<EventType> getSupportedEvents() {
		return Collections.unmodifiableSet(Sets.newHashSet(EventType.RENDER_TEXT, EventType.END_TEXT));
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
