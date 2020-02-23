package com.tank.flavorpairer.object;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class IngredientNode {
	private String name;
	private Ingredient ingredient;
	private IngredientNode leftNode;
	private IngredientNode rightNode;
	private IngredientNode parentNode;
	private Set<Ingredient> pairings;
	private boolean isVisited;

	public IngredientNode(Ingredient ingredient) {
		this.ingredient = ingredient;
		this.name = ingredient.getName();
		this.pairings = ingredient.getPairings();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public IngredientNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(IngredientNode leftNode) {
		this.leftNode = leftNode;
	}

	public IngredientNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(IngredientNode rightNode) {
		this.rightNode = rightNode;
	}

	public Set<Ingredient> getPairings() {
		return pairings == null ? new HashSet<>() : pairings;
	}

	public void setPairings(Set<Ingredient> pairings) {
		this.pairings = pairings;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void markAsVisited() {
		this.isVisited = true;
	}

	public void unmarkAsVisited() {
		this.isVisited = false;
	}

	public IngredientNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(IngredientNode parentNode) {
		this.parentNode = parentNode;
	}

	public int size() {
		final int leftNodeSize = size(leftNode, 0);
		final int rightNodeSize = size(rightNode, 0);

		// Adding 1 since this node is the root
		return leftNodeSize + rightNodeSize + 1;
	}

	public int count() {
		final int sizeOfLeft = count(this.getLeftNode());
		final int sizeOfRight = count(this.getRightNode());
		return 1 + sizeOfLeft + sizeOfRight;
	}

	private int count(IngredientNode ingredientNode) {
		if (ingredientNode == null) {
			return 0;
		}

		if (ingredientNode.getLeftNode() == null && ingredientNode.getRightNode() == null) {
			return 1;
		}

		final int sizeOfLeft = count(ingredientNode.getLeftNode());
		final int sizeOfRight = count(ingredientNode.getRightNode());
		return 1 + sizeOfLeft + sizeOfRight;
	}

	private int size(IngredientNode node, int size) {
		if (node == null) {
			return size;
		}

		if (node.getLeftNode() == null && node.getRightNode() == null) {
			return 1;
		}

		size++;
		final int leftNodeSize = size(node.getLeftNode(), size);
		final int rightNodeSize = size(node.getRightNode(), size);

		// Adding 1 since this node is the root
		return leftNodeSize + rightNodeSize;
	}

	public void print() {
		System.out.println(getName());
		print(getRightNode(), "   ", "R");
		print(getLeftNode(), "   ", "L");
	}

	private void print(IngredientNode node, String indentation, String position) {
		if (node == null) {
			return;
		}
		System.out.println(indentation + position + ": " + node.getName());
		print(node.getRightNode(), indentation + "   ", "R");
		print(node.getLeftNode(), indentation + "   ", "L");
	}

	@Override
	public String toString() {
		return "IngredientNode [name=" + name + ", ingredient=" + ingredient + ", leftNode=" + leftNode + ", rightNode="
				+ rightNode + ", pairings=" + pairings + ", isVisited=" + isVisited + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredient, isVisited, leftNode, name, pairings, parentNode, rightNode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final IngredientNode other = (IngredientNode) obj;
		return ingredient == other.ingredient && isVisited == other.isVisited
				&& Objects.equals(leftNode, other.leftNode) && Objects.equals(name, other.name)
				&& Objects.equals(pairings, other.pairings) && Objects.equals(parentNode, other.parentNode)
				&& Objects.equals(rightNode, other.rightNode);
	}
}
