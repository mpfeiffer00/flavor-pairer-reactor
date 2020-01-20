package com.tank.flavorpairer;

import com.tank.flavorpairer.object.IngredientTree;

public class FlavorEngine {

	public static void main(String[] args) {
		final IngredientTree ingredientTree = FlavorInjector.constructIngredientTree();
		System.out.println(ingredientTree);
	}
}
