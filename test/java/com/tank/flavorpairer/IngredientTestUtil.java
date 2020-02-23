package com.tank.flavorpairer;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientTestUtil {
	private IngredientTestUtil() {
	}

	public static IngredientTree createTree(IngredientNode ingredientNode) {
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(ingredientNode);
		return tree;
	}

	public static IngredientNode createIngredientNode(Ingredient ingredient) {
		return new IngredientNode(ingredient);
	}

	public static IngredientNode createIngredientNodeAndMark(Ingredient ingredient) {
		final IngredientNode node = createIngredientNode(ingredient);
		node.markAsVisited();
		return node;
	}

	public static IngredientNode createIngredientNodeAndMark(Ingredient ingredient, IngredientNode leftNode,
			IngredientNode rightNode) {
		final IngredientNode node = createIngredientNodeAndMark(ingredient);
		if (leftNode != null) {
			node.setLeftNode(leftNode);
		}
		if (rightNode != null) {
			node.setRightNode(rightNode);
		}
		return node;
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
