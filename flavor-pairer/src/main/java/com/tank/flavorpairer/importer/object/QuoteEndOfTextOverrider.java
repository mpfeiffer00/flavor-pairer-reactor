package com.tank.flavorpairer.importer.object;

/**
 * Overrider to assist in cases where an End of Text signal is after a Quote.
 * 
 * @author tank
 */
public class QuoteEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		if (!endOfTextCriteria.isQuote()) {
			return false;
		}

		return true;
	}
}
