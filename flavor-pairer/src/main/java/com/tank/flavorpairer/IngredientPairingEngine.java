package com.tank.flavorpairer;

import java.util.Arrays;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientPairingResponse;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientPairingEngine {
	private final IngredientTree ingredientTree;

	public IngredientPairingEngine() {
		ingredientTree = IngredientTreeProcessor.constructIngredientTree(Arrays.asList(Ingredient.values()));
	}

	public IngredientPairingResponse computePairings(Ingredient ingredient) {
		final IngredientNode ingredientNode = IngredientTreeUtil.findIngredient(ingredient, ingredientTree.getRoot());
		return ingredientNode == null ? null : buildResponse(ingredientNode);
	}

	private IngredientPairingResponse buildResponse(IngredientNode ingredientNode) {
		final IngredientPairingResponse response = new IngredientPairingResponse(ingredientNode.getIngredient());
		response.setFirstLevelIngredientPairings(ingredientNode.getPairings());
		response.setSecondLevelIngredientPairingRanks(IngredientTreeProcessor
				.computeIngredientPairingLevel(ingredientNode.getIngredient(), ingredientTree, 2));
		response.setThirdLevelIngredientPairingRanks(IngredientTreeProcessor
				.computeIngredientPairingLevel(ingredientNode.getIngredient(), ingredientTree, 3));
		return response;
	}
}
