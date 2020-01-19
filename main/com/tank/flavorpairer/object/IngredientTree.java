package com.tank.flavorpairer.object;

public class IngredientTree {
	private IngredientNode root;

	public IngredientNode getRoot() {
		return root;
	}

	public void setRoot(IngredientNode root) {
		this.root = root;
	}

	@Override
	public String toString() {
		return "IngredientTree [root=" + root + "]";
	}
}
