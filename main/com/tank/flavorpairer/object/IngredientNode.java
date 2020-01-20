package com.tank.flavorpairer.object;

import java.util.Objects;
import java.util.Set;

public class IngredientNode {
	private String name;
	private IngredientNode leftNode;
	private IngredientNode rightNode;
	private Set<Ingredient> pairings;

	public IngredientNode(Ingredient ingredient) {
		this.name = ingredient.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "IngredientNode [name=" + name + ", leftNode=" + leftNode + ", rightNode=" + rightNode + ", pairings="
				+ pairings + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(leftNode, name, pairings, rightNode);
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
		return Objects.equals(leftNode, other.leftNode) && Objects.equals(name, other.name)
				&& Objects.equals(pairings, other.pairings) && Objects.equals(rightNode, other.rightNode);
	}
}
