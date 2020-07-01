package com.tank.flavorpairer.importer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import com.tank.flavorpairer.importer.object.EndOfTextCriteria;
import com.tank.flavorpairer.importer.object.EndOfTextOverrideProcessor;
import com.tank.flavorpairer.importer.object.EndOfTextStateCriteria;
import com.tank.flavorpairer.importer.util.DishAttribute;
import com.tank.flavorpairer.importer.util.RenderInfoTextAssistant;

public class ChunkTextExtractionStrategy extends SimpleTextExtractionStrategy {

	private final EndOfTextOverrideProcessor endOfTextOverrideProcessor = new EndOfTextOverrideProcessor();
	private final EndOfTextStateCriteria endOfTextStateCriteria = new EndOfTextStateCriteria();
	private Vector lastStart;
	private Vector lastEnd;
	private final StringBuilder result = new StringBuilder();

	// State to span PDF reading. TODO: re-design.
	private final List<FlavorBibleIngredient> flavorBibleIngredients = new ArrayList<>();
	private final List<FlavorBibleIngredient> ingredientPairings = new ArrayList<>();
	private FlavorBibleIngredient currentFlavorBibleIngredientHeading = null;

	private boolean isHeading = false;
	private boolean isQuote = false;
	private boolean isAuthor = false;
	private DishAttribute dishAttribute = null;
	private PairingLevel pairingLevel = null;

	@Override
	public void eventOccurred(IEventData data, EventType type) {
		if (type.equals(EventType.RENDER_TEXT)) {
			final TextRenderInfo renderInfo = (TextRenderInfo) data;
			final boolean firstRender = result.length() == 0;
			boolean hardReturn = false;
			isHeading = RenderInfoTextAssistant.isHeading(renderInfo);
			isQuote = RenderInfoTextAssistant.isQuoteText(renderInfo);
			isAuthor = RenderInfoTextAssistant.isAuthorText(renderInfo);
			dishAttribute = RenderInfoTextAssistant.getDishAttribute(renderInfo);

			// We want the pairing level from the first charcter after the return.
			// ie: "MEATS, esp. roasted" which we want it to be 'marriage' and not
			// 'recommended'.
			pairingLevel = pairingLevel == null ? RenderInfoTextAssistant.determinePairingLevel(renderInfo)
					: pairingLevel;

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

		if (currentFlavorBibleIngredientHeading == null) {
			if (!getResultantText().startsWith("A")) {
				result.replace(0, result.length(), "");
				return;
			}

			if (type.equals(EventType.END_TEXT) && !getResultantText().equals("ARTICHOKES")) {
				result.replace(0, result.length(), "");
				return;
			}
		}

		if (type.equals(EventType.END_TEXT) && !getResultantText().isBlank()) {
			final EndOfTextCriteria endOfTextCriteria = EndOfTextCriteria.builder().withText(getResultantText())
					.withIsHeading(isHeading).withDishAttribute(dishAttribute).withIsAuthor(isAuthor)
					.withIsQuote(isQuote).withPairingLevel(pairingLevel).build();

			if (isHeading) {
				// First heading of file will not have any paired ingredients, can only add at
				// end of heading resolution. We need to keep this to keep track of transitions.
				if (currentFlavorBibleIngredientHeading != null) {
					currentFlavorBibleIngredientHeading.addFlavorBibleIngredient(Sets.newHashSet(ingredientPairings));
					flavorBibleIngredients.add(currentFlavorBibleIngredientHeading);
				}

				currentFlavorBibleIngredientHeading = new FlavorBibleIngredient();
				currentFlavorBibleIngredientHeading.setIngredientName(getResultantText());

				endOfTextStateCriteria.currentFlavorBibleIngredientHeading = currentFlavorBibleIngredientHeading;
				ingredientPairings.clear();
				endOfTextStateCriteria.ingredientPairings = new ArrayList<>();
				endOfTextStateCriteria.isFlavorAffinityEntries = false;
			} else if (endOfTextOverrideProcessor.process(endOfTextCriteria, endOfTextStateCriteria)) {
				if (endOfTextStateCriteria.hasUpdatedState) {
					currentFlavorBibleIngredientHeading = endOfTextStateCriteria.currentFlavorBibleIngredientHeading;
					if (endOfTextStateCriteria.ingredientPairings != null
							&& !endOfTextStateCriteria.ingredientPairings.isEmpty()) {
						ingredientPairings.addAll(endOfTextStateCriteria.ingredientPairings);
					}
				}
				endOfTextStateCriteria.hasUpdatedState = false;
			} else {
				final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
				ingredient.setIngredientName(getResultantText());
				ingredient.setPairingLevel(pairingLevel);
				ingredientPairings.add(ingredient);
			}

			result.replace(0, result.length(), "");
			isHeading = false;
			pairingLevel = null;
			isQuote = false;
			isAuthor = false;
			dishAttribute = null;
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
