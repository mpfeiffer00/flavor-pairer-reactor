package com.tank.flavorpairer.importer.object;

public class DishAttributeEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		if (endOfTextCriteria.getDishAttribute() == null) {
			return false;
		}

		return true;
	}
}
