package com.tank.flavorpairer.importer.object;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tank.flavorpairer.importer.FlavorBibleIngredient;

public class EspeciallyEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (!text.toLowerCase().contains("esp.")) {
			return false;
		}

		// Ugh. "esp." is sometimes on a new line. Trying to detect if it
		// is a newline if "esp" is at beginning of text
		if (text.replaceFirst(",", "").trim().startsWith("esp.")) {
			final Set<String> especiallies = Stream.of(text.replaceFirst(", esp.", "").trim().split(","))
					.collect(Collectors.toSet());
			endOfTextStateCriteria.ingredientPairings.get(endOfTextStateCriteria.ingredientPairings.size() - 1)
					.setEspecially(especiallies);
		} else {
			final String[] especiallies = text.replaceFirst("esp.", "").split(",");
			final FlavorBibleIngredient ingredient = new FlavorBibleIngredient();
			ingredient.setIngredientName(especiallies[0].trim());
			ingredient.setPairingLevel(endOfTextCriteria.getPairingLevel());
			ingredient.setEspecially(Stream.of(especiallies[1]).map(x -> x.trim()).collect(Collectors.toSet()));
			endOfTextStateCriteria.ingredientPairings.add(ingredient);
		}
		endOfTextStateCriteria.hasUpdatedState = true;
		return true;
	}
}
