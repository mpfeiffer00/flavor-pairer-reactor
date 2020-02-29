package com.tank.flavorpairer.object;

import java.util.Objects;

public class IngredientTree {
	private IngredientNode root;

	/**
	 * @return The root {@link IngredientNode}. Will be null if no ingredients are
	 *         present.
	 */
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

	@Override
	public int hashCode() {
		return Objects.hash(root);
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
		final IngredientTree other = (IngredientTree) obj;
		return Objects.equals(root, other.root);
	}
}
