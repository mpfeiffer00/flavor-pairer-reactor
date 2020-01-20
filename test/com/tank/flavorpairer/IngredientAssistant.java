package com.tank.flavorpairer;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientAssistant {
	private IngredientAssistant() {
	}

	public static IngredientNode createIngredientNode(Ingredient ingredient) {
		return new IngredientNode(ingredient);
	}

	public static IngredientNode createIngredientNode(Ingredient ingredient, IngredientNode leftNode,
			IngredientNode rightNode) {
		final IngredientNode node = createIngredientNode(ingredient);
		if (leftNode != null) {
			node.setLeftNode(leftNode);
		}
		if (rightNode != null) {
			node.setRightNode(rightNode);
		}
		return node;
	}
}
