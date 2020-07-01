package com.tank.flavorpairer.importer.object;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tank.flavorpairer.importer.FlavorBibleIngredient;

/**
 * Overrider to assist in cases where an End of Text signal is after a "e.g.".
 * <br>
 * Example: <br>
 * <ul>
 * <li>Page: 76</li>
 * <li>Heading: Achiote Seeds</li>
 * <li>Text: TODO</li>
 * <li>Issue: A EoT is after the "e.g.", causing the remaining of the line to be
 * interpreted as new ingredient pairings.</li>
 * </ul>
 * 
 * @author tank
 */
public class ExampleEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (!text.toLowerCase().contains("e.g.")) {
			return false;
		}

		final String[] splitLine = text.replaceFirst(",", "").trim().replace("(", "").replace(")", "").split("e.g.");
		final Set<String> examples = Stream.of(splitLine[1].split(",")).map(x -> x.trim()).filter(x -> !x.isBlank())
				.collect(Collectors.toSet());

		final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
		ingredient.setExamples(examples);
		ingredient.setIngredientName(splitLine[0].trim());
		ingredient.setPairingLevel(endOfTextCriteria.getPairingLevel());
		endOfTextStateCriteria.ingredientPairings.add(ingredient);
		endOfTextStateCriteria.hasUpdatedState = true;
		return true;
	}
}
