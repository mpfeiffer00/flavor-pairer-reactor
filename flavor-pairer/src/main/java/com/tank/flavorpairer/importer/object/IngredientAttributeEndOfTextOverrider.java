package com.tank.flavorpairer.importer.object;

import com.tank.flavorpairer.importer.util.IngredientAttribute;

/**
 * Overrider to assist in cases where an End of Text signal is after a various
 * ingredient attributes. <br>
 * Example: <br>
 * <ul>
 * <li>Page: 111</li>
 * <li>Heading: Artichokes</li>
 * <li>Text: "Season: spring-early autumn"</li>
 * <li>Issue: A EoT is after the ingredient attributes, causing the remaining of
 * the line to be interpreted as new ingredient pairings.</li>
 * </ul>
 * 
 * @author tank
 */
public class IngredientAttributeEndOfTextOverrider implements EndOfTextOverrider {
	@Override
	public boolean override(EndOfTextCriteria endOfTextCriteria, EndOfTextStateCriteria endOfTextStateCriteria) {
		final String text = endOfTextCriteria.getText();
		if (text.startsWith("Season") || text.startsWith("Taste") || text.startsWith("Weight")
				|| text.startsWith("Volume") || text.startsWith("Tips") || text.startsWith("Techniques")) {
			// The attribute always comes with a colon
			endOfTextStateCriteria.ingredientAttribute = IngredientAttribute.getAttributeByText(text.replace(":", ""));
			return true;
		}

		if (endOfTextStateCriteria.ingredientAttribute == null) {
			return false;
		}

		// The season/taste/weight/volume/tips have return before the text. See ALLSPICE
		// for example
		final IngredientAttribute ingredientAttribute = endOfTextStateCriteria.ingredientAttribute;
		switch (ingredientAttribute) {
		case SEASON:
			endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setSeason(text);
			break;
		case TASTE:
			endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setTaste(text);
			break;
		case WEIGHT:
			endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setWeight(text);
			break;
		case VOLUME:
			endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setVolume(text);
			break;
		case TIPS:
			endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setTips(text);
			break;
		case TECHNIQUE:
			endOfTextStateCriteria.currentFlavorBibleIngredientHeading.setTechniques(text);
			break;
		default:
			throw new RuntimeException("idk what this previousAttribute is: " + text);
		}

		endOfTextStateCriteria.hasUpdatedState = true;
		endOfTextStateCriteria.ingredientAttribute = null;
		return true;
	}
}
