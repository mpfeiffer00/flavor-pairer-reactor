package com.tank.flavorpairer.importer.object;

import java.util.ArrayList;
import java.util.List;

public class EndOfTextOverrideProcessor {

	/**
	 * Processes the end of text override exceptional cases.
	 * 
	 * @param endOfTextCriteria      The {@link EndOfTextCriteria} that contains
	 *                               general ingredient processing information.
	 * @param endOfTextStateCriteria The {@link EndOfTextStateCriteria} that
	 *                               contains state necessary to pass between
	 *                               subsequent processes.
	 * @return True if one of the override exceptions was executed, false otherwise.
	 */
	public boolean process(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		for (final EndOfTextOverrider endOfTextOverrider : getEndOfTextOverriders()) {
			final boolean wasProcessed = endOfTextOverrider.override(endOfTextCriteria, endOfTextStateCriteria);
			if (wasProcessed) {
				return true;
			}
		}
		return false;
	}

	private static List<EndOfTextOverrider> getEndOfTextOverriders() {
		final List<EndOfTextOverrider> endOfTextOverriders = new ArrayList<>();
		endOfTextOverriders.add(new QuoteEndOfTextOverrider());
		endOfTextOverriders.add(new AuthorEndOfTextOverrider());
		endOfTextOverriders.add(new DishAttributeEndOfTextOverrider());
		endOfTextOverriders.add(new IngredientAttributeEndOfTextOverrider());
		endOfTextOverriders.add(new FlavorAffinitiesEndOfTextOverrider());
		endOfTextOverriders.add(new EspeciallyEndOfTextOverrider());
		endOfTextOverriders.add(new ExampleEndOfTextOverrider());
		endOfTextOverriders.add(new SeeEndOfTextOverrider());
		endOfTextOverriders.add(new MisformatCommaEndOfTextOverrider());
		endOfTextOverriders.add(new MisformatColonEndOfTextOverrider());
		return endOfTextOverriders;
	}
}
