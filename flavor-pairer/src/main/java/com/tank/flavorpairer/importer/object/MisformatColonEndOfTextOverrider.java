package com.tank.flavorpairer.importer.object;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tank.flavorpairer.importer.FlavorBibleIngredient;

/**
 * Overrider to assist in cases where an End of Text signal is after a colon.
 * <br>
 * Example: <br>
 * <ul>
 * <li>Page: 112</li>
 * <li>Heading: Artichokes</li>
 * <li>Text: "LEMON: confit, juice, zest"</li>
 * <li>Issue: A EoT is after the colon, causing the remaining of the line to be
 * interpreted as new ingredient pairings.</li>
 * </ul>
 * 
 * @author tank
 */
public class MisformatColonEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (!text.contains(":") && !endOfTextStateCriteria.isEndingWithColon) {
			return false;
		}

		if (text.endsWith(":")) {
			final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
			ingredient.setIngredientName(text.replace(":", "").trim());
			ingredient.setPairingLevel(endOfTextCriteria.getPairingLevel());
			endOfTextStateCriteria.ingredientPairings.add(ingredient);
			endOfTextStateCriteria.isEndingWithColon = true;
		} else if (endOfTextStateCriteria.isEndingWithColon) {
			final Set<String> examples = Stream.of(text.split(",")).map(x -> x.trim()).filter(x -> !x.isBlank())
					.collect(Collectors.toSet());
			endOfTextStateCriteria.ingredientPairings.get(endOfTextStateCriteria.ingredientPairings.size() - 1)
					.setExamples(examples);
			endOfTextStateCriteria.isEndingWithColon = false;
		} else {
			final String[] splitLine = text.split(":");
			final Set<String> examples = splitLine.length == 1 ? Collections.emptySet()
					: Stream.of(splitLine[1].split(",")).map(x -> x.trim()).filter(x -> !x.isBlank())
							.collect(Collectors.toSet());
			final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
			ingredient.setExamples(examples);
			ingredient.setIngredientName(splitLine[0].trim());
			ingredient.setPairingLevel(endOfTextCriteria.getPairingLevel());
			endOfTextStateCriteria.ingredientPairings.add(ingredient);
			endOfTextStateCriteria.isEndingWithColon = false;
		}

		endOfTextStateCriteria.hasUpdatedState = true;
		return true;
	}
}
