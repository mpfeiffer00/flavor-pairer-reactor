package com.tank.flavorpairer.importer.object;

/**
 * Overrider to assist in cases where an End of Text signal is after an Author
 * of a quote.
 * 
 * @author tank
 */
public class AuthorEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		if (!endOfTextCriteria.isAuthor()) {
			return false;
		}

		return true;
	}
}
