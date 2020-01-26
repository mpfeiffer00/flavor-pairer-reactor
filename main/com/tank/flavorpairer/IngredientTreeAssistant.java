package com.tank.flavorpairer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientTreeAssistant {
	private IngredientTreeAssistant() {
	}

	public static IngredientTree constructIngredientTree(List<Ingredient> ingredients) {
		IngredientNode rootIngredientNode = null;
		for (final IngredientNode ingredientNode : createIngredients(ingredients)) {
			rootIngredientNode = addIngredientToTree(rootIngredientNode, ingredientNode);
		}
		final IngredientTree ingredientTree = new IngredientTree();
		ingredientTree.setRoot(rootIngredientNode);
		return ingredientTree;
	}

	public static IngredientNode findIngredient(Ingredient ingredient, IngredientTree ingredientTree) {
		return findNode(ingredient, ingredientTree.getRoot());
	}

	private static IngredientNode findNode(Ingredient ingredient, IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return ingredientNode;
		} else if (ingredient.equals(ingredientNode.getIngredient())) {
			return ingredientNode;
		}

		final IngredientNode leftNode = findNode(ingredient, ingredientNode.getLeftNode());
		return leftNode != null ? leftNode : findNode(ingredient, ingredientNode.getRightNode());
	}

	public static Set<Ingredient> computeSecondLevelPairings(Ingredient ingredient, IngredientTree ingredientTree) {
		final IngredientNode ingredientNode = findIngredient(ingredient, ingredientTree);
		if (ingredientNode == null) {
			return null;
		}

		final Set<Ingredient> pairings = new HashSet<>();
		for (final Ingredient ingredientPairing : ingredientNode.getPairings()) {
			final IngredientNode ingredientPairingNode = findIngredient(ingredientPairing, ingredientTree);
			if (ingredientPairingNode == null) {
				continue;
			}
			pairings.addAll(ingredientPairingNode.getPairings());
		}

		return pairings;
	}

	private static IngredientNode addIngredientToTree(IngredientNode root, IngredientNode ingredientNodeToInsert) {
		if (root == null) {
			return ingredientNodeToInsert;
		}

		if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
			// node comes after ingredient name
			if ((getDepth(root.getLeftNode()) - getDepth(root.getRightNode())) > 0) {
				// Rotating root to right
				final IngredientNode rootToRightNode = new IngredientNode(root.getIngredient());
				rootToRightNode.setRightNode(root.getRightNode());
				root.setRightNode(rootToRightNode);

				// Rotating left node to root
				final IngredientNode leftRootNode = root.getLeftNode();
				root.setName(leftRootNode.getName());
				root.setPairings(leftRootNode.getPairings());
				root.setIngredient(leftRootNode.getIngredient());
				root.setLeftNode(leftRootNode.getLeftNode());
			}

			if (root.getLeftNode() == null) {
				root.setLeftNode(ingredientNodeToInsert);
			} else {
				addIngredientToTree(root.getLeftNode(), ingredientNodeToInsert);
			}
		} else if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
			// node comes before ingredient name
			if ((getDepth(root.getRightNode()) - getDepth(root.getLeftNode())) > 0) {
				// Rotating root to left
				final IngredientNode rootToLeftNode = new IngredientNode(root.getIngredient());
				rootToLeftNode.setLeftNode(root.getLeftNode());
				root.setLeftNode(rootToLeftNode);

				// Rotating right node to root
				final IngredientNode rightRootNode = root.getRightNode();
				root.setName(rightRootNode.getName());
				root.setPairings(rightRootNode.getPairings());
				root.setIngredient(rightRootNode.getIngredient());
				root.setRightNode(rightRootNode.getRightNode());
			}

			if (root.getRightNode() == null) {
				root.setRightNode(ingredientNodeToInsert);
			} else {
				addIngredientToTree(root.getRightNode(), ingredientNodeToInsert);
			}
		} else {
			throw new RuntimeException("duplicate ingredient: " + ingredientNodeToInsert.getName());
		}
		return root;
	}

	private static List<IngredientNode> createIngredients(List<Ingredient> ingredients) {
		final List<IngredientNode> nodes = ingredients.stream().map(i -> {
			final IngredientNode node = new IngredientNode(i);
			node.setPairings(i.getPairings());
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
