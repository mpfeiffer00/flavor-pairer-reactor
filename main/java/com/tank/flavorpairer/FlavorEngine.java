package com.tank.flavorpairer;

import java.util.Arrays;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientTree;

public class FlavorEngine {

	public static void main(String[] args) {
		final IngredientTree ingredientTree = IngredientTreeAssistant
				.constructIngredientTree(Arrays.asList(Ingredient.values()));
		System.out.println(ingredientTree);
	}
}
