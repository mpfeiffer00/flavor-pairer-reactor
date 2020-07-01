package com.tank.flavorpairer.importer.object;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Overrider to assist in cases where an End of Text signal is after a comma.
 * <br>
 * Example: <br>
 * <ul>
 * <li>Page:</li>
 * <li>Heading:</li>
 * <li>Text: rice, basmati"</li>
 * <li>Issue: A EoT is after the comma, causing the remaining of the line to be
 * interpreted as new ingredient pairings.</li>
 * </ul>
 * 
 * @author tank
 */
public class MisformatCommaEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (!text.trim().startsWith(",")) {
			return false;
		}

		final String[] splitLine = text.split(",");
		endOfTextStateCriteria.ingredientPairings.get(endOfTextStateCriteria.ingredientPairings.size() - 1)
				.setEspecially(Stream.of(splitLine[1]).map(x -> x.trim()).collect(Collectors.toSet()));
		endOfTextStateCriteria.hasUpdatedState = true;
		return true;
	}
}
