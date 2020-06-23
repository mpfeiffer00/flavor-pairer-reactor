package com.tank.flavorpairer.importer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private final List<FlavorBibleIngredient> ingredientPairings = new ArrayList<>();
	private FlavorBibleIngredient currentFlavorBibleIngredientHeading = null;

	private boolean isHeading = false;
	private boolean isFlavorAffinityEntries = false;
	private String previousAttribute = "";
	private boolean isQuote = false;
	private boolean isAuthor = false;
	private boolean isDishesHeading = false;
	private boolean isDishName = false;
	private boolean isDishAuthor = false;
	private PairingLevel pairingLevel = null;
	private boolean isEndingWithColon = false;

	@Override
	public void eventOccurred(IEventData data, EventType type) {
		if (type.equals(EventType.RENDER_TEXT)) {
			final TextRenderInfo renderInfo = (TextRenderInfo) data;
			final boolean firstRender = result.length() == 0;
			boolean hardReturn = false;
			isHeading = RenderInfoTextAssistant.isHeading(renderInfo);
			isQuote = RenderInfoTextAssistant.isQuoteText(renderInfo);
			isAuthor = RenderInfoTextAssistant.isAuthorText(renderInfo);
			isDishesHeading = RenderInfoTextAssistant.isDishesHeading(renderInfo);
			isDishName = RenderInfoTextAssistant.isDishName(renderInfo);
			isDishAuthor = RenderInfoTextAssistant.isDishAuthor(renderInfo);
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
			if (isHeading) {
				// First heading of file will not have any paired ingredients, can only add at
				// end of heading resolution
				if (currentFlavorBibleIngredientHeading != null) {
					currentFlavorBibleIngredientHeading.addFlavorBibleIngredient(Sets.newHashSet(ingredientPairings));
					flavorBibleIngredients.add(currentFlavorBibleIngredientHeading);
				}

				currentFlavorBibleIngredientHeading = new FlavorBibleIngredient();
				currentFlavorBibleIngredientHeading.setIngredientName(getResultantText());
				ingredientPairings.clear();
				isFlavorAffinityEntries = false;
			} else if (isQuote || isAuthor) {
				// Ignore for now.
			} else if (isDishesHeading || isDishName || isDishAuthor) {
				// Ignore for now.
			} else if (!previousAttribute.isBlank()) {
				// The season/taste/weight/volume/tips have return before the text. See ALLSPICE
				// for example
				switch (previousAttribute) {
				case "Season:":
					currentFlavorBibleIngredientHeading.setSeason(getResultantText());
					break;
				case "Taste:":
					currentFlavorBibleIngredientHeading.setTaste(getResultantText());
					break;
				case "Weight:":
					currentFlavorBibleIngredientHeading.setWeight(getResultantText());
					break;
				case "Volume:":
					currentFlavorBibleIngredientHeading.setVolume(getResultantText());
					break;
				case "Tips:":
					currentFlavorBibleIngredientHeading.setTips(getResultantText());
					break;
				case "Techniques:":
					currentFlavorBibleIngredientHeading.setTechniques(getResultantText());
					break;
				default:
					throw new RuntimeException("idk what this previousAttribute is: " + getResultantText());
				}
				previousAttribute = "";
			} else if (getResultantText().startsWith("Season") || getResultantText().startsWith("Taste")
					|| getResultantText().startsWith("Weight") || getResultantText().startsWith("Volume")
					|| getResultantText().startsWith("Tips") || getResultantText().startsWith("Techniques")) {
				previousAttribute = getResultantText();
			} else if ("Flavor Affinities".equals(getResultantText())) {
				isFlavorAffinityEntries = true;
			} else if (isFlavorAffinityEntries) {
				currentFlavorBibleIngredientHeading.addFlavorAffinity(getResultantText());
			} else if (getResultantText().contains(":") || isEndingWithColon) {
				if (getResultantText().endsWith(":")) {
					// Edge case: Page 112, under 'Artichokes', 'LEMON:' has an END_OF_TEXT.
					// Need to combine with next like of 'confit, juice, zest'
					final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
					ingredient.setIngredientName(getResultantText().replace(":", "").trim());
					ingredient.setPairingLevel(pairingLevel);
					ingredientPairings.add(ingredient);
					isEndingWithColon = true;
				} else if (isEndingWithColon) {
					final Set<String> examples = Stream.of(getResultantText().split(",")).map(x -> x.trim())
							.filter(x -> !x.isBlank()).collect(Collectors.toSet());
					ingredientPairings.get(ingredientPairings.size() - 1).setExamples(examples);
					isEndingWithColon = false;
				} else {
					final String[] splitLine = getResultantText().split(":");
					final Set<String> examples = splitLine.length == 1 ? Collections.emptySet()
							: Stream.of(splitLine[1].split(",")).map(x -> x.trim()).filter(x -> !x.isBlank())
									.collect(Collectors.toSet());

					final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
					ingredient.setExamples(examples);
					ingredient.setIngredientName(splitLine[0].trim());
					ingredient.setPairingLevel(pairingLevel);
					ingredientPairings.add(ingredient);
					isEndingWithColon = false;
				}
			} else if (getResultantText().toLowerCase().contains("esp.")) {
				// Ugh. "esp." is sometimes on a new line. Trying to detect if it
				// is a newline if "esp" is at beginning of text
				if (getResultantText().replaceFirst(",", "").trim().startsWith("esp.")) {
					final Set<String> especiallies = Stream
							.of(getResultantText().replaceFirst(", esp.", "").trim().split(","))
							.collect(Collectors.toSet());
					ingredientPairings.get(ingredientPairings.size() - 1)
							.setEspecially(especiallies.stream().map(x -> x.trim()).collect(Collectors.toSet()));
				} else {
					final String[] especiallies = getResultantText().replaceFirst("esp.", "").split(",");
					final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
					ingredient.setIngredientName(especiallies[0].trim());
					ingredient.setPairingLevel(pairingLevel);
					ingredient.setEspecially(Stream.of(especiallies[1]).map(x -> x.trim()).collect(Collectors.toSet()));
					ingredientPairings.add(ingredient);
				}
			} else if (getResultantText().toLowerCase().contains("e.g.")) {
				final String[] splitLine = getResultantText().replaceFirst(",", "").trim().replace("(", "")
						.replace(")", "").split("e.g.");
				final Set<String> examples = Stream.of(splitLine[1].split(",")).map(x -> x.trim())
						.filter(x -> !x.isBlank()).collect(Collectors.toSet());

				final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
				ingredient.setExamples(examples);
				ingredient.setIngredientName(splitLine[0].trim());
				ingredient.setPairingLevel(pairingLevel);
				ingredientPairings.add(ingredient);
			} else if (getResultantText().contains("See")) {
				final String[] splitLine = getResultantText().replaceFirst("See", "").replaceFirst("also", "")
						.replace("(", "").replace(")", "").split(",");
				final Set<String> similarities = Stream.of(splitLine).map(x -> x.trim()).filter(x -> !x.isBlank())
						.collect(Collectors.toSet());
				currentFlavorBibleIngredientHeading.setSimilarities(similarities);
			} else if (getResultantText().trim().startsWith(",")) {
				// Qualifies on the case "rice, basmati", except with the new line misformat.
				// Treat it as "esp."
				final String[] splitLine = getResultantText().split(",");
				ingredientPairings.get(ingredientPairings.size() - 1)
						.setEspecially(Stream.of(splitLine[1]).map(x -> x.trim()).collect(Collectors.toSet()));
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
			isDishesHeading = false;
			isDishName = false;
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
