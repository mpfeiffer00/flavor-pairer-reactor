package com.tank.flavorpairer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class FlavorInjector {

	public static void addIngredientToTree(IngredientNode root, IngredientNode ingredientNodeToInsert) {
		if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
			// root comes after ingredient name
			if (root.getLeftNode() == null) {
				root.setLeftNode(ingredientNodeToInsert);
			} else {
				final IngredientNode previousNode = root.getLeftNode();
				root.setLeftNode(ingredientNodeToInsert);
				addIngredientToTree(ingredientNodeToInsert, previousNode);
			}
		} else if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
			// root comes before ingredient name
			if (root.getRightNode() == null) {
				root.setRightNode(ingredientNodeToInsert);
			} else {
				final IngredientNode previousNode = root.getRightNode();
				root.setRightNode(ingredientNodeToInsert);
				addIngredientToTree(ingredientNodeToInsert, previousNode);
			}
		} else {
			throw new RuntimeException("duplicate ingredient: " + ingredientNodeToInsert.getName());
		}
	}

	public static List<IngredientNode> createIngredients() {
		final List<IngredientNode> nodes = Arrays.asList(Ingredient.values()).stream().map(i -> {
			final IngredientNode node = new IngredientNode(i);
			// node.setPairings(i.getPairings());
			return node;
		}).collect(Collectors.toList());
		return nodes;
	}

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
