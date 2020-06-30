package com.tank.flavorpairer.importer.object;

public class AuthorEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		if (!endOfTextCriteria.isAuthor()) {
			return false;
		}

		return true;
	}
}
