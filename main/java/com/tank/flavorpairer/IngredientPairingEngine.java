package com.tank.flavorpairer;

import java.util.Arrays;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientPairingResponse;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientPairingEngine {
	private final IngredientTree ingredientTree;

	public IngredientPairingEngine() {
		ingredientTree = IngredientTreeAssistant.constructIngredientTree(Arrays.asList(Ingredient.values()));
	}

	public IngredientPairingResponse computePairings(Ingredient ingredient) {
		ingredientTree.getRoot().print();
		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(ingredient,
				ingredientTree.getRoot());
		return ingredientNode == null ? null : buildResponse(ingredientNode);
	}

	private IngredientPairingResponse buildResponse(IngredientNode ingredientNode) {
		final IngredientPairingResponse response = new IngredientPairingResponse(ingredientNode.getIngredient());
		response.setFirstLevelIngredientPairings(ingredientNode.getPairings());
		response.setSecondLevelIngredientPairings(
				IngredientTreeAssistant.computeSecondLevelPairings(ingredientNode.getIngredient(), ingredientTree));
		response.setThirdLevelIngredientPairings(
				IngredientTreeAssistant.computeThirdLevelPairings(ingredientNode.getIngredient(), ingredientTree));

		System.out.println("First Level Pairings: ");
		response.getFirstLevelIngredientPairings().stream().forEach(System.out::println);

		System.out.println("-------------------");
		System.out.println("Second Level Pairings: ");
		response.getSecondLevelIngredientPairings().stream().forEach(System.out::println);

		System.out.println("-------------------");
		System.out.println("Third Level Pairings: ");
		response.getThirdLevelIngredientPairings().stream().forEach(System.out::println);

		return response;
	}
}
