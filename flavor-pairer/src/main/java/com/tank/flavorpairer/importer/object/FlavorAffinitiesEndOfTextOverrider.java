package com.tank.flavorpairer.importer.object;

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
