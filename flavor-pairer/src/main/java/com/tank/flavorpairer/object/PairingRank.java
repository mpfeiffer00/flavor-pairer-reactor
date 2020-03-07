package com.tank.flavorpairer.object;

public class PairingRank {
	private Ingredient ingredient;
	private int rank;

	public PairingRank(Ingredient ingredient) {
		this.ingredient = ingredient;
		this.rank = 1;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "PairingRank [ingredient=" + ingredient + ", rank=" + rank + "]";
	}
}
