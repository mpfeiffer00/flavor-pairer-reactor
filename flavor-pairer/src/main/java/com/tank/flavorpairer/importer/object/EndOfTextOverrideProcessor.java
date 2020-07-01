package com.tank.flavorpairer.importer.object;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Processes line overrides at an End of Text signal.
 * 
 * @author tank
 *
 */
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
	 * @throws IllegalArgumentException if {@code endOfTextCriteria} or
	 *                                  {@code endOfTextStateCriteria} is null.
	 */
	public boolean process(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		Preconditions.checkNotNull(endOfTextCriteria, "endOfTextCriteria cannot be null");
		Preconditions.checkNotNull(endOfTextStateCriteria, "endOfTextStateCriteria cannot be null");

		for (final EndOfTextOverrider endOfTextOverrider : getEndOfTextOverriders()) {
			final boolean wasProcessed = endOfTextOverrider.override(endOfTextCriteria, endOfTextStateCriteria);
			if (wasProcessed) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return The non-empty List of {@link EndOfTextOverrider}s.
	 */
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
