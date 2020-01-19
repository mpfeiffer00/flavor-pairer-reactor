package com.tank.flavorpairer;

import com.tank.flavorpairer.object.IngredientNode;

public class FlavorEngine {

	public static void main(String[] args) {
		IngredientNode rootIngredientNode = null;
		for (final IngredientNode ingredientNode : FlavorInjector.createIngredients()) {
			if (rootIngredientNode == null) {
				rootIngredientNode = ingredientNode;
				continue;
			}
			FlavorInjector.addIngredientToTree(rootIngredientNode, ingredientNode);
		}
	}
}
