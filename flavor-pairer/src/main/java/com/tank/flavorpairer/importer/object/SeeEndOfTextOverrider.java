package com.tank.flavorpairer.importer.object;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Overrider to assist in cases where an End of Text signal is after a 'See'.
 * <br>
 * Example: <br>
 * <ul>
 * <li>Page: 76</li>
 * <li>Heading: Acidity</li>
 * <li>Text: (See Sourness)"</li>
 * <li>Issue: A EoT is after the "See", causing the remaining of the line to be
 * interpreted as new ingredient pairings.</li>
 * </ul>
 * 
 * @author tank
 */
public class SeeEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (!text.contains("See")) {
			return false;
		}
		final String[] splitLine = text.replaceFirst("See", "").replaceFirst("also", "").replace("(", "")
				.replace(")", "").split(",");
		final Set<String> similarities = Stream.of(splitLine).map(x -> x.trim()).filter(x -> !x.isBlank())
				.collect(Collectors.toSet());
		endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setSimilarities(similarities);
		endOfTextStateCriteria.hasUpdatedState = true;
		return false;
	}
}
