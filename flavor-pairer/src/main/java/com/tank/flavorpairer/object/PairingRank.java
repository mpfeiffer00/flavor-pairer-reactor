package com.tank.flavorpairer.object;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PairingRank {
	private Ingredient ingredient;
	private int rank;
	private Set<Ingredient> pairings;

	public PairingRank(Ingredient ingredient) {
		this.ingredient = ingredient;
		this.rank = 1;
		this.pairings = new HashSet<>();
	}

	public PairingRank(Ingredient ingredient, Set<Ingredient> pairings) {
		this.ingredient = ingredient;
		this.rank = 1;
		this.pairings = pairings;
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

	public Set<Ingredient> getPairings() {
		return pairings;
	}

	public void setPairings(Set<Ingredient> pairings) {
		this.pairings = pairings;
	}

	public void addPairing(Ingredient ingredient) {
		this.pairings.add(ingredient);
	}

	public void addPairings(Set<Ingredient> ingredients) {
		this.pairings.addAll(ingredients);
	}

	@Override
	public String toString() {
		return "PairingRank [ingredient=" + ingredient + ", rank=" + rank + ", pairings=" + pairings + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredient, pairings, rank);
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
		final PairingRank other = (PairingRank) obj;
		return ingredient == other.ingredient && Objects.equals(pairings, other.pairings) && rank == other.rank;
	}
}
