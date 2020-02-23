package com.tank.flavorpairer;

import java.util.List;

import org.assertj.core.util.Lists;

import com.google.common.annotations.VisibleForTesting;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientTreeUtil {
	/**
	 * Finds the middle node of the {@link IngredientNode}.
	 * 
	 * @param rootNode The {@link IngredientNode} to inspect.
	 * @return The {@link IngredientNode} that is the middle node. Will be null if
	 *         rootNode is null
	 */
	@VisibleForTesting
	public static IngredientNode findMiddleNode(final IngredientNode rootNode) {
		if (rootNode == null) {
			return null;
		}

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
	public static int getUnmarkedDepth(IngredientNode ingredientNode) {
		if (ingredientNode == null || ingredientNode.isVisited()) {
			return 0;
		}

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
