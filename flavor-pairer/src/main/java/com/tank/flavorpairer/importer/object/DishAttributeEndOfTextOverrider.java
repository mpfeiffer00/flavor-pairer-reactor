package com.tank.flavorpairer.importer.object;

/**
 * Overrider to assist in cases where an End of Text signal is after a Dish
 * attribute.
 * 
 * @author tank
 */
public class DishAttributeEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		if (endOfTextCriteria.getDishAttribute() == null) {
			return false;
		}

		return true;
	}
}
