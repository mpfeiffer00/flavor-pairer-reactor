package com.tank.flavorpairer;

import java.util.Arrays;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientPairingResponse;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientPairingEngine {
	private final IngredientTree ingredientTree;

	public IngredientPairingEngine() {
		ingredientTree = IngredientTreeAssistant.constructIngredientTree(Arrays.asList(Ingredient.values()));
	}

	public IngredientPairingResponse computePairings() {

		return new IngredientPairingResponse();
	}

}
