package com.tank.flavorpairer.importer.object;

public class QuoteEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		if (!endOfTextCriteria.isQuote()) {
			return false;
		}

		return true;
	}
}
