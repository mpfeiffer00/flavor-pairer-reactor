package com.tank.flavorpairer;

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

	public static Set<Ingredient> computeThirdLevelPairings(Ingredient ingredient, IngredientTree ingredientTree) {
		final Set<Ingredient> pairings = computeSecondLevelPairings(ingredient, ingredientTree);
		if (pairings == null) {
			return null;
		}

		final Set<Ingredient> shit = new HashSet<>();
		shit.addAll(pairings);
		for (final Ingredient pairedIngredient : pairings) {
			final IngredientNode pairedNode = findIngredient(pairedIngredient, ingredientTree);
			if (pairedNode == null) {
				System.out.println("null: " + pairedIngredient);
				continue;
			}

			if (pairedNode.getPairings().contains(ingredient)) {
				System.out.println("back to the source: " + pairedNode.getName());
			}
			shit.addAll(pairedNode.getPairings());
		}

		return shit;
	}

	private static IngredientNode addIngredientToTree(IngredientNode root, IngredientNode ingredientNodeToInsert) {
		if (root == null) {
			return ingredientNodeToInsert;
		}

		if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
			// node comes after ingredient name
			if ((getDepth(root.getLeftNode()) - getDepth(root.getRightNode())) > 0) {
				if (root.getLeftNode() == null) {
					root.setLeftNode(ingredientNodeToInsert);
				} else {
					root.setLeftNode(addIngredientToTree(root.getLeftNode(), ingredientNodeToInsert));
				}

				if (root.getLeftNode().getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) > 0) {
					return rotateWithLeftChild(root);
				} else {
					return doubleWithLeftChild(root);
				}
			}

			if (root.getLeftNode() == null) {
				root.setLeftNode(ingredientNodeToInsert);
			} else {
				root.setLeftNode(addIngredientToTree(root.getLeftNode(), ingredientNodeToInsert));
			}
		} else if (root.getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
			// node comes before ingredient name
			if ((getDepth(root.getRightNode()) - getDepth(root.getLeftNode())) > 0) {
				if (root.getRightNode() == null) {
					root.setRightNode(ingredientNodeToInsert);
				} else {
					root.setRightNode(addIngredientToTree(root.getRightNode(), ingredientNodeToInsert));
				}

				if (root.getRightNode().getName().compareToIgnoreCase(ingredientNodeToInsert.getName()) < 0) {
					return rotateWithRightChild(root);
				} else {
					return doubleWithRightChild(root);
				}
			}

			if (root.getRightNode() == null) {
				root.setRightNode(ingredientNodeToInsert);
			} else {
				root.setRightNode(addIngredientToTree(root.getRightNode(), ingredientNodeToInsert));
			}
		} else {
			throw new RuntimeException("duplicate ingredient: " + ingredientNodeToInsert.getName());
		}
		return root;
	}

	private static IngredientNode rotateWithLeftChild(IngredientNode root) {
		final IngredientNode k1 = root.getLeftNode();
		root.setLeftNode(k1.getRightNode());
		k1.setRightNode(root);
		return k1;
	}

	private static IngredientNode rotateWithRightChild(IngredientNode k1) {
		final IngredientNode k2 = k1.getRightNode();
		k1.setRightNode(k2.getLeftNode());
		k2.setLeftNode(k1);
		return k2;
	}

	private static IngredientNode doubleWithLeftChild(IngredientNode k3) {
		k3.setLeftNode(rotateWithRightChild(k3.getLeftNode()));
		return rotateWithLeftChild(k3);
	}

	private static IngredientNode doubleWithRightChild(IngredientNode k1) {
		k1.setRightNode(rotateWithLeftChild(k1.getRightNode()));
		return rotateWithRightChild(k1);
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
