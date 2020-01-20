package com.tank.flavorpairer;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class FlavorInjector {
	private FlavorInjector() {
	}

	public static IngredientTree constructIngredientTree(List<Ingredient> ingredients) {
		final IngredientTree ingredientTree = new IngredientTree();
		for (final IngredientNode ingredientNode : createIngredients(ingredients)) {
			if (ingredientTree.getRoot() == null) {
				ingredientTree.setRoot(ingredientNode);
				continue;
			}
			addIngredientToTree(ingredientTree.getRoot(), ingredientNode);
		}
		return ingredientTree;
	}

	private static void addIngredientToTree(IngredientNode node, IngredientNode ingredientNodeToInsert) {
		if (node.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
			// node comes after ingredient name
			if (node.getLeftNode() == null) {
				node.setLeftNode(ingredientNodeToInsert);
			} else {
				final IngredientNode previousNode = node.getLeftNode();
				node.setLeftNode(ingredientNodeToInsert);
				addIngredientToTree(ingredientNodeToInsert, previousNode);
			}
		} else if (node.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
			// node comes before ingredient name
			if (node.getRightNode() == null) {
				node.setRightNode(ingredientNodeToInsert);
			} else {
				final IngredientNode previousNode = node.getRightNode();
				node.setRightNode(ingredientNodeToInsert);
				addIngredientToTree(ingredientNodeToInsert, previousNode);
			}
		} else {
			throw new RuntimeException("duplicate ingredient: " + ingredientNodeToInsert.getName());
		}
	}

	private static List<IngredientNode> createIngredients(List<Ingredient> ingredients) {
		final List<IngredientNode> nodes = ingredients.stream().map(i -> {
			final IngredientNode node = new IngredientNode(i);
			// node.setPairings(i.getPairings());
			return node;
		}).collect(Collectors.toList());
		return nodes;
	}

	@VisibleForTesting
	public static int getDepth(IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return 0;
		}

		if (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null) {
			return 1;
		}

		final int sizeOfLeft = getDepth(ingredientNode.getLeftNode(), 1);
		final int sizeOfRight = getDepth(ingredientNode.getRightNode(), 1);
		return Integer.max(sizeOfLeft, sizeOfRight);
	}

	private static int getDepth(IngredientNode ingredientNode, int size) {
		if (ingredientNode == null) {
			return size;
		}

		++size;
		final int sizeOfLeft = getDepth(ingredientNode.getLeftNode(), size);
		final int sizeOfRight = getDepth(ingredientNode.getRightNode(), size);
		return Integer.max(sizeOfLeft, sizeOfRight);
	}
}
