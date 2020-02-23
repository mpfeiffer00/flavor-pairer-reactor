package com.tank.flavorpairer;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

/**
 * Contains assistant methods for {@link IngredientTree}.
 */
public class IngredientTreeAssistant {
	private IngredientTreeAssistant() {
	}

	/**
	 * Constructs a balanced {@link IngredientTree} with the given
	 * {@link Ingredient}s.
	 * 
	 * @param ingredients The List of {@link Ingredient}s to be placed in the tree.
	 * @return A non-null {@link IngredientTree}.
	 */
	public static IngredientTree constructIngredientTree(List<Ingredient> ingredients) {
		if (ingredients == null || ingredients.isEmpty()) {
			return new IngredientTree();
		}

		IngredientNode rootIngredientNode = null;
		for (final IngredientNode ingredientNode : createIngredients(ingredients)) {
			rootIngredientNode = addIngredientToTree(rootIngredientNode, ingredientNode);
		}
		final IngredientTree ingredientTree = new IngredientTree();
		ingredientTree.setRoot(rootIngredientNode);
		return ingredientTree;
	}

	/**
	 * Finds the given {@link Ingredient} in the {@link IngredientTree}.
	 * 
	 * @param ingredient     The {@link Ingredient} to find.
	 * @param ingredientTree The {@link IngredientTree} to inspect.
	 * @return The {@link IngredientNode} for the given {@link Ingredient}. Will be
	 *         null if not found or given {@link IngredientNode} is null.
	 */
	public static IngredientNode findIngredient(Ingredient ingredient, IngredientNode ingredientNode) {
		if (ingredient == null || ingredientNode == null) {
			return null;
		}

		if (ingredient.equals(ingredientNode.getIngredient())) {
			return ingredientNode;
		}

		final IngredientNode leftNode = findIngredient(ingredient, ingredientNode.getLeftNode());
		return leftNode != null ? leftNode : findIngredient(ingredient, ingredientNode.getRightNode());
	}

	/**
	 * Computes the second level pairings for the given {@link Ingredient} using the
	 * {@link IngredientTree}. <br>
	 * TODO: Add examples.
	 * 
	 * @param ingredient     The {@link Ingredient} to construct pairings for.
	 * @param ingredientTree The {@link IngredientTree} of ingredients to inspect.
	 * @return The second level pairing Set of {@link Ingredient}s. Will be null if
	 *         {@link Ingredient} or {@link IngredientNode} is null. Will be empty
	 *         if no pairings are found.
	 */
	public static Set<Ingredient> computeSecondLevelPairings(Ingredient ingredient, IngredientTree ingredientTree) {
		if (ingredient == null || ingredientTree == null || ingredientTree.getRoot() == null) {
			return null;
		}

		final IngredientNode ingredientNode = findIngredient(ingredient, ingredientTree.getRoot());
		if (ingredientNode == null) {
			return Collections.emptySet();
		}

		final Set<Ingredient> secondLevelPairings = new HashSet<>();
		for (final Ingredient pairedIngredient : ingredientNode.getPairings()) {
			final IngredientNode pairedIngredientNode = findIngredient(pairedIngredient, ingredientTree.getRoot());
			if (pairedIngredientNode == null) {
				continue;
			}
			secondLevelPairings.addAll(pairedIngredientNode.getPairings());
		}

		return secondLevelPairings;
	}

	/**
	 * Computes the third level pairings for the given {@link Ingredient} using the
	 * {@link IngredientTree}. <br>
	 * TODO: Add examples.
	 * 
	 * @param ingredient     The {@link Ingredient} to construct pairings for.
	 * @param ingredientTree The {@link IngredientTree} of ingredients to inspect.
	 * @return The third level pairing Set of {@link Ingredient}s. Will be null if
	 *         {@link Ingredient} or {@link IngredientNode} is null. Will be empty
	 *         if no pairings are found.
	 */
	public static Set<Ingredient> computeThirdLevelPairings(Ingredient ingredient, IngredientTree ingredientTree) {
		if (ingredient == null || ingredientTree == null || ingredientTree.getRoot() == null) {
			return null;
		}

		final Set<Ingredient> secondLevelPairings = computeSecondLevelPairings(ingredient, ingredientTree);
		if (secondLevelPairings == null || secondLevelPairings.isEmpty()) {
			return Collections.emptySet();
		}

		final Set<Ingredient> thirdLevelPairings = new HashSet<>();
		thirdLevelPairings.addAll(secondLevelPairings);
		for (final Ingredient pairedIngredient : secondLevelPairings) {
			final IngredientNode pairedNode = findIngredient(pairedIngredient, ingredientTree.getRoot());
			if (pairedNode == null) {
				System.out.println("null: " + pairedIngredient);
				continue;
			}

			if (pairedNode.getPairings().contains(ingredient)) {
				System.out.println("back to the source: " + pairedNode.getName());
			}
			thirdLevelPairings.addAll(pairedNode.getPairings());
		}

		return thirdLevelPairings;
	}

	/**
	 * Add the given node to the tree.
	 * 
	 * @param root                   The root {@link IngredientNode}.
	 * @param ingredientNodeToInsert The {@link IngredientNode} to insert into the
	 *                               tree.
	 * @return The non-null root {@link IngredientNode}.
	 */
	private static IngredientNode addIngredientToTree(IngredientNode root, IngredientNode ingredientNodeToInsert) {
		if (root == null) {
			return ingredientNodeToInsert;
		}

		if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
			if (root.getLeftNode() == null) {
				root.setLeftNode(ingredientNodeToInsert);
			} else {
				root.setLeftNode(addIngredientToTree(root.getLeftNode(), ingredientNodeToInsert));
			}

			if ((IngredientTreeUtil.getDepth(root.getLeftNode())
					- IngredientTreeUtil.getDepth(root.getRightNode())) > 1) {
				if (root.getLeftNode().getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
					return rotateToRight(root);
				} else {
					return doubleRotateToRight(root);
				}
			}
		} else if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
			if (root.getRightNode() == null) {
				root.setRightNode(ingredientNodeToInsert);
			} else {
				root.setRightNode(addIngredientToTree(root.getRightNode(), ingredientNodeToInsert));
			}

			if ((IngredientTreeUtil.getDepth(root.getRightNode())
					- IngredientTreeUtil.getDepth(root.getLeftNode())) > 1) {
				if (root.getRightNode().getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
					return rotateToLeft(root);
				} else {
					return doubleRotateToLeft(root);
				}
			}
		} else {
			throw new RuntimeException("duplicate ingredient: " + ingredientNodeToInsert.getName());
		}
		return root;
	}

	/**
	 * Rotates the {@link IngredientNode} to the right.
	 * 
	 * @param node The {@link IngredientNode} to rotate.
	 * @return The non-null rotated {@link IngredientNode}.
	 */
	private static IngredientNode rotateToRight(IngredientNode node) {
		final IngredientNode leftNode = node.getLeftNode();
		node.setLeftNode(leftNode.getRightNode());
		leftNode.setRightNode(node);
		return leftNode;
	}

	/**
	 * Rotates the {@link IngredientNode} to the left.
	 * 
	 * @param node The {@link IngredientNode} to rotate.
	 * @return The non-null rotated {@link IngredientNode}.
	 */
	private static IngredientNode rotateToLeft(IngredientNode node) {
		final IngredientNode rightNode = node.getRightNode();
		node.setRightNode(rightNode.getLeftNode());
		rightNode.setLeftNode(node);
		return rightNode;
	}

	private static IngredientNode doubleRotateToRight(IngredientNode node) {
		node.setLeftNode(rotateToLeft(node.getLeftNode()));
		return rotateToRight(node);
	}

	private static IngredientNode doubleRotateToLeft(IngredientNode node) {
		node.setRightNode(rotateToRight(node.getRightNode()));
		return rotateToLeft(node);
	}

	/**
	 * Deletes the ingredient from the tree.
	 * 
	 * @param root       The {@link IngredientNode} to inspect.
	 * @param ingredient The {@link Ingredient} to delete.
	 * @return The new {@link IngredientNode} without the given {@link Ingredient}.
	 *         Will be null if no ingredients remain.
	 */
	public static IngredientNode deleteNodeFromTree(IngredientNode root, final Ingredient ingredient) {
		Preconditions.checkArgument(ingredient != null);
		final List<Ingredient> ingredients = IngredientTreeUtil.getIngredients(root);
		ingredients.remove(ingredient);

		if (ingredients.isEmpty()) {
			return null;
		}

		return constructIngredientTree(ingredients).getRoot();
	}

	private static List<IngredientNode> createIngredients(List<Ingredient> ingredients) {
		final List<IngredientNode> nodes = ingredients.stream().map(i -> {
			final IngredientNode node = new IngredientNode(i);
			node.setPairings(i.getPairings());
			return node;
		}).collect(Collectors.toList());
		return nodes;
	}
}
