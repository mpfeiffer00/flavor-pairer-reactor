package com.tank.flavorpairer.importer.object;

/**
 * Overrider to assist in cases where an End of Text signal is after a "Flavor
 * Affinities". <br>
 * Example: <br>
 * <ul>
 * <li>Page: 113</li>
 * <li>Heading: Artichokes</li>
 * <li>Text: "Flavor Affinities"</li>
 * <li>Issue: A EoT is after the "Flavor Affinities", causing the remaining of
 * the line to be interpreted as new ingredient pairings.</li>
 * </ul>
 * 
 * @author tank
 */
public class FlavorAffinitiesEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if ("Flavor Affinities".equals(text)) {
			endOfTextStateCriteria.isFlavorAffinityEntries = true;
			return true;
		}

		if (!endOfTextStateCriteria.isFlavorAffinityEntries) {
			return false;
		}

		endOfTextStateCriteria.currentFlavorBibleIngredientHeading.addFlavorAffinity(text);
		endOfTextStateCriteria.hasUpdatedState = true;
		// NOTE: we do not reset the isFlavorAffinityEntries to false because there may
		// be a list. The Header change will flip the value.
		return true;
	}
}
