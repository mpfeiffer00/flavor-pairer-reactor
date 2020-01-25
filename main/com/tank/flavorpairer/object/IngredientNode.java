package com.tank.flavorpairer.object;

import java.util.Objects;
import java.util.Set;

public class IngredientNode {
	private String name;
	private Ingredient ingredient;
	private IngredientNode leftNode;
	private IngredientNode rightNode;
	private Set<Ingredient> pairings;

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
		return pairings;
	}

	public void setPairings(Set<Ingredient> pairings) {
		this.pairings = pairings;
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
				+ rightNode + ", pairings=" + pairings + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredient, leftNode, name, pairings, rightNode);
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
		return ingredient == other.ingredient && Objects.equals(leftNode, other.leftNode)
				&& Objects.equals(name, other.name) && Objects.equals(pairings, other.pairings)
				&& Objects.equals(rightNode, other.rightNode);
	}
}
