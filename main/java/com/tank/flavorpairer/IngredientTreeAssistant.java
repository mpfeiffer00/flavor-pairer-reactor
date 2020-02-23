package com.tank.flavorpairer;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

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

			if ((getDepth(root.getLeftNode()) - getDepth(root.getRightNode())) > 1) {
				if (root.getLeftNode().getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
					return rotateWithLeftChild(root);
				} else {
					return doubleWithLeftChild(root);
				}
			}
		} else if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
			if (root.getRightNode() == null) {
				root.setRightNode(ingredientNodeToInsert);
			} else {
				root.setRightNode(addIngredientToTree(root.getRightNode(), ingredientNodeToInsert));
			}

			if ((getDepth(root.getRightNode()) - getDepth(root.getLeftNode())) > 1) {
				if (root.getRightNode().getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
					return rotateWithRightChild(root);
				} else {
					return doubleWithRightChild(root);
				}
			}
		} else {
			throw new RuntimeException("duplicate ingredient: " + ingredientNodeToInsert.getName());
		}
		return root;
	}

	private static IngredientNode rotateWithLeftChild(IngredientNode node) {
		final IngredientNode leftRootNode = node.getLeftNode();
		node.setLeftNode(leftRootNode.getRightNode());
		leftRootNode.setRightNode(node);
		return leftRootNode;
	}

	private static IngredientNode rotateWithRightChild(IngredientNode node) {
		final IngredientNode rightRootNode = node.getRightNode();
		node.setRightNode(rightRootNode.getLeftNode());
		rightRootNode.setLeftNode(node);
		return rightRootNode;
	}

	private static IngredientNode doubleWithLeftChild(IngredientNode node) {
		node.setLeftNode(rotateWithRightChild(node.getLeftNode()));
		return rotateWithLeftChild(node);
	}

	private static IngredientNode doubleWithRightChild(IngredientNode node) {
		node.setRightNode(rotateWithLeftChild(node.getRightNode()));
		return rotateWithRightChild(node);
	}

	public static IngredientNode deleteNodeFromTree(IngredientNode root, final Ingredient ingredient) {
		Preconditions.checkArgument(ingredient != null);
		final List<Ingredient> ingredients = getIngredients(root);
		ingredients.remove(ingredient);

		if (ingredients.isEmpty()) {
			return null;
		}

		return constructIngredientTree(ingredients).getRoot();
	}

	/**
	 * Determines if the given node is balanced. The depth of the sides cannot be
	 * greater or equal to 2.
	 * 
	 * @param node The root {@link IngredientNode} to evaluate.
	 * @return True if the tree is balanced, false otherwise.
	 */
	private static boolean isTreeBalanced(final IngredientNode node) {
		return Math.abs(getDepth(node.getLeftNode()) - getDepth(node.getRightNode())) < 2;
	}

	private static IngredientNode getMostLeftNode(final IngredientNode parentNode, final IngredientNode leftNode) {
		return leftNode == null ? parentNode : getMostLeftNode(leftNode, leftNode.getLeftNode());
	}

	private static IngredientNode getMostRightNode(final IngredientNode parentNode, final IngredientNode rightNode) {
		return rightNode == null ? parentNode : getMostRightNode(rightNode, rightNode.getRightNode());
	}

	@VisibleForTesting
	public static IngredientNode findMiddleNode(final IngredientNode rootNode) {
		final int leftTreeDepth = countNumberOfChildren(rootNode.getLeftNode());
		final int rightTreeDepth = countNumberOfChildren(rootNode.getRightNode());
		if (Math.abs(leftTreeDepth - rightTreeDepth) <= 1) {
			// Since we have an odd number (or same depth), default to root
			return rootNode;
		}

		clearAllMarkNodes(rootNode);

		if (leftTreeDepth > rightTreeDepth) {
			// Since L>R, we will add the root node to R since it will be marked before
			// starting to mark the other side of L
			final IngredientNode startingLeftNode = rootNode.getLeftNode();
			final int roundOfMarks = Math.floorDiv(leftTreeDepth - rightTreeDepth, 2);
			for (int i = 0; i < roundOfMarks; i++) {
				final IngredientNode farthestUnmarkedLeft = findFurthestUnmarkedNodeFromRoot(startingLeftNode);
				farthestUnmarkedLeft.markAsVisited();

				if (rootNode.isVisited()) {
					// Start marking top of left
					final IngredientNode closestUnmarkedNode = findClosestUnmarkedNodeFromRoot(startingLeftNode);
					closestUnmarkedNode.markAsVisited();
				}

				final IngredientNode farthestUnmarkedRight = findFurthestUnmarkedNodeFromRoot(rootNode.getRightNode());
				if (farthestUnmarkedRight == null) {
					rootNode.markAsVisited();
				} else {
					farthestUnmarkedRight.markAsVisited();
				}
			}

			return findClosestUnmarkedNodeFromRoot(rootNode.getLeftNode());
		}

		// Since R>L, we will add the root node to L since it will be marked before
		// starting to mark the other side of R
		// Mark 2 nodes per round
		final int roundOfMarks = Math.floorDiv(rightTreeDepth - leftTreeDepth, 2);
		for (int i = 0; i < roundOfMarks; i++) {
			final IngredientNode farthestUnmarkedRight = findLargestUnmarkedNode(rootNode);
			farthestUnmarkedRight.markAsVisited();

			if (rootNode.isVisited()) {
				// Start marking top of left
				final IngredientNode closestUnmarkedNode = findClosestUnmarkedNodeFromRoot(rootNode);
				closestUnmarkedNode.markAsVisited();
			}

			final IngredientNode farthestUnmarkedLeft = findLargestUnmarkedNode(rootNode.getLeftNode());
			if (farthestUnmarkedLeft == null) {
				rootNode.markAsVisited();
			} else {
				farthestUnmarkedLeft.markAsVisited();
			}
		}

		return findClosestUnmarkedNodeFromRoot(rootNode.getRightNode());
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
	public static IngredientNode findFurthestNodeFromRoot(final IngredientNode ingredientNode) {
		if ((ingredientNode == null)
				|| (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null)) {
			return ingredientNode;
		}

		final int sizeOfLeft = countNumberOfChildren(ingredientNode.getLeftNode());
		final int sizeOfRight = countNumberOfChildren(ingredientNode.getRightNode());
		if (sizeOfLeft == sizeOfRight) {
			final int depthOfLeft = getDepth(ingredientNode.getLeftNode());
			final int depthOfRight = getDepth(ingredientNode.getRightNode());
			if (depthOfLeft == depthOfRight) {
				return findFurthestNodeFromRoot(ingredientNode.getRightNode());
			} else if (depthOfLeft > depthOfRight) {
				return findFurthestNodeFromRoot(ingredientNode.getLeftNode());
			}
			return findFurthestNodeFromRoot(ingredientNode.getRightNode());
		} else if (sizeOfLeft > sizeOfRight) {
			return findFurthestNodeFromRoot(ingredientNode.getLeftNode());
		}
		return findFurthestNodeFromRoot(ingredientNode.getRightNode());
	}

	/**
	 * Finds the farthest unvisited node from the given node.
	 * 
	 * @param ingredientNode The root {@link IngredientNode} used to determine the
	 *                       farthest unvisited node.
	 * @return The unvisited {@link IngredientNode} that is farthest from the root
	 *         node. Will be null if all nodes, including root, are visited.
	 */
	@VisibleForTesting
	public static IngredientNode findFurthestUnmarkedNodeFromRoot(final IngredientNode ingredientNode) {
		if ((ingredientNode == null)
				|| (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null)) {
			return ingredientNode == null ? null : ingredientNode.isVisited() ? null : ingredientNode;
		}

		final int sizeOfLeft = countNumberOfUnmarkedChildren(ingredientNode.getLeftNode());
		final int sizeOfRight = countNumberOfUnmarkedChildren(ingredientNode.getRightNode());
		if (sizeOfLeft == 0 && sizeOfRight == 0) {
			return ingredientNode.isVisited() ? null : ingredientNode;
		}

		if (sizeOfLeft == sizeOfRight) {
			final int depthOfLeft = getUnmarkedDepth(ingredientNode.getLeftNode());
			final int depthOfRight = getUnmarkedDepth(ingredientNode.getRightNode());
			if (depthOfLeft == depthOfRight) {
				return findFurthestUnmarkedNodeFromRoot(ingredientNode.getRightNode());
			} else if (depthOfLeft > depthOfRight) {
				return findFurthestUnmarkedNodeFromRoot(ingredientNode.getLeftNode());
			}
			return findFurthestUnmarkedNodeFromRoot(ingredientNode.getRightNode());
		} else if (sizeOfLeft > sizeOfRight) {
			return findFurthestUnmarkedNodeFromRoot(ingredientNode.getLeftNode());
		}
		return findFurthestUnmarkedNodeFromRoot(ingredientNode.getRightNode());
	}

	@VisibleForTesting
	public static IngredientNode findLargestUnmarkedNode(final IngredientNode ingredientNode) {
		if ((ingredientNode == null)
				|| (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null)) {
			return ingredientNode == null ? null : ingredientNode.isVisited() ? null : ingredientNode;
		}

		final int sizeOfLeft = countNumberOfUnmarkedChildren(ingredientNode.getLeftNode());
		final int sizeOfRight = countNumberOfUnmarkedChildren(ingredientNode.getRightNode());
		if (sizeOfLeft == 0 && sizeOfRight == 0) {
			return ingredientNode.isVisited() ? null : ingredientNode;
		}

		if (sizeOfRight > 0) {
			return findLargestUnmarkedNode(ingredientNode.getRightNode());
		} else if (!ingredientNode.isVisited()) {
			return ingredientNode;
		} else {
			return findLargestUnmarkedNode(ingredientNode.getLeftNode());
		}
	}

	@VisibleForTesting
	public static IngredientNode findClosestUnmarkedNodeFromRoot(final IngredientNode ingredientNode) {
		if ((ingredientNode == null)
				|| (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null)) {
			return ingredientNode == null ? null : ingredientNode.isVisited() ? null : ingredientNode;
		}

		if (!ingredientNode.isVisited()) {
			return ingredientNode;
		}

		final int sizeOfLeft = countNumberOfUnmarkedChildren(ingredientNode.getLeftNode());
		final int sizeOfRight = countNumberOfUnmarkedChildren(ingredientNode.getRightNode());
		if (sizeOfLeft == 0 && sizeOfRight == 0) {
			return ingredientNode.isVisited() ? null : ingredientNode;
		}

		final int depthOfLeft = getUnmarkedDepth(ingredientNode.getLeftNode());
		final int depthOfRight = getUnmarkedDepth(ingredientNode.getRightNode());

		if (sizeOfLeft == sizeOfRight) {
			if (depthOfLeft == depthOfRight) {
				return findClosestUnmarkedNodeFromRoot(ingredientNode.getRightNode());
			} else if (depthOfLeft > depthOfRight) {
				return findClosestUnmarkedNodeFromRoot(ingredientNode.getLeftNode());
			}
			return findClosestUnmarkedNodeFromRoot(ingredientNode.getRightNode());
		} else if (sizeOfLeft > sizeOfRight) {
			return findClosestUnmarkedNodeFromRoot(ingredientNode.getLeftNode());
		}
		return findClosestUnmarkedNodeFromRoot(ingredientNode.getRightNode());
	}

	@VisibleForTesting
	public static int countNumberOfChildren(IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return 0;
		}

		if (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null) {
			return 1;
		}

		final int sizeOfLeft = countNumberOfChildren(ingredientNode.getLeftNode());
		final int sizeOfRight = countNumberOfChildren(ingredientNode.getRightNode());
		return 1 + sizeOfLeft + sizeOfRight;
	}

	@VisibleForTesting
	public static int countNumberOfUnmarkedChildren(IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return 0;
		}

		if (ingredientNode.isVisited() && ingredientNode.getLeftNode() == null
				&& ingredientNode.getRightNode() == null) {
			return 0;
		}

		if (!ingredientNode.isVisited() && ingredientNode.getLeftNode() == null
				&& ingredientNode.getRightNode() == null) {
			return 1;
		}

		final int sizeOfLeft = countNumberOfUnmarkedChildren(ingredientNode.getLeftNode());
		final int sizeOfRight = countNumberOfUnmarkedChildren(ingredientNode.getRightNode());
		return (ingredientNode.isVisited() ? 0 : 1) + sizeOfLeft + sizeOfRight;
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

	@VisibleForTesting
	public static int getUnmarkedDepth(IngredientNode ingredientNode) {
		if (ingredientNode == null || ingredientNode.isVisited()) {
			return 0;
		}

		// May not need anymore?
//		final boolean isLeftDead = ingredientNode.getLeftNode() == null || ingredientNode.getLeftNode().isVisited();
//		final boolean isRightDead = ingredientNode.getRightNode() == null || ingredientNode.getRightNode().isVisited();
//		if (ingredientNode.isVisited() && isLeftDead && isRightDead) {
//			return 0;
//		}

		final int initialStartingDepth = ingredientNode.isVisited() ? 0 : 1;
		final int sizeOfLeft = getUnmarkedDepth(ingredientNode.getLeftNode(), initialStartingDepth);
		final int sizeOfRight = getUnmarkedDepth(ingredientNode.getRightNode(), initialStartingDepth);
		return Integer.max(sizeOfLeft, sizeOfRight);
	}

	private static int getUnmarkedDepth(IngredientNode ingredientNode, int size) {
		if (ingredientNode == null) {
			return size;
		}

		if (!ingredientNode.isVisited()) {
			++size;
		}

		final int sizeOfLeft = getUnmarkedDepth(ingredientNode.getLeftNode(), size);
		final int sizeOfRight = getUnmarkedDepth(ingredientNode.getRightNode(), size);
		return Integer.max(sizeOfLeft, sizeOfRight);
	}

	public static List<Ingredient> getIngredients(IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return Lists.emptyList();
		}

		final List<Ingredient> ingredients = Lists.newArrayList();
		ingredients.add(ingredientNode.getIngredient());
		ingredients.addAll(getIngredients(ingredientNode.getLeftNode()));
		ingredients.addAll(getIngredients(ingredientNode.getRightNode()));
		return ingredients;
	}

	private static void clearAllMarkNodes(IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return;
		}

		ingredientNode.unmarkAsVisited();
		clearAllMarkNodes(ingredientNode.getLeftNode());
		clearAllMarkNodes(ingredientNode.getRightNode());
	}
}
