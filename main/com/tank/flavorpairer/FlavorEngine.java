package com.tank.flavorpairer;

import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class FlavorEngine {

	public static void main(String[] args) {
		IngredientTree ingredientTree = new IngredientTree();
		for (final IngredientNode ingredientNode : FlavorInjector.createIngredients()) {
			if (ingredientTree.getRoot() == null) {
				ingredientTree.setRoot(ingredientNode);
				continue;
			}
			FlavorInjector.addIngredientToTree(ingredientTree.getRoot(), ingredientNode);
		}
		System.out.println(ingredientTree);
	}
}
