package com.tank.flavorpairer.importer.object;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MisformatCommaEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (!text.trim().startsWith(",")) {
			return false;
		}

		// Qualifies on the case "rice, basmati", except with the new line misformat.
		// Treat it as "esp."
		final String[] splitLine = text.split(",");
		endOfTextStateCriteria.ingredientPairings.get(endOfTextStateCriteria.ingredientPairings.size() - 1)
				.setEspecially(Stream.of(splitLine[1]).map(x -> x.trim()).collect(Collectors.toSet()));
		endOfTextStateCriteria.hasUpdatedState = true;
		return true;
	}
}
