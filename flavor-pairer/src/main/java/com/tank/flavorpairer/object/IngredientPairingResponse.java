package com.tank.flavorpairer.object;

import java.util.List;
import java.util.Set;

public class IngredientPairingResponse {
	private Ingredient ingredient;
	private Set<Ingredient> firstLevelIngredientPairings;
	private List<PairingRank> secondLevelIngredientPairingRanks;
	private Set<Ingredient> thirdLevelIngredientPairings;

	public IngredientPairingResponse(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Set<Ingredient> getFirstLevelIngredientPairings() {
		return firstLevelIngredientPairings;
	}

	public void setFirstLevelIngredientPairings(Set<Ingredient> firstLevelIngredientPairings) {
		this.firstLevelIngredientPairings = firstLevelIngredientPairings;
	}

	public List<PairingRank> getSecondLevelIngredientPairingRanks() {
		return secondLevelIngredientPairingRanks;
	}

	public void setSecondLevelIngredientPairingRanks(List<PairingRank> secondLevelIngredientPairingRanks) {
		this.secondLevelIngredientPairingRanks = secondLevelIngredientPairingRanks;
	}

	public Set<Ingredient> getThirdLevelIngredientPairings() {
		return thirdLevelIngredientPairings;
	}

	public void setThirdLevelIngredientPairings(Set<Ingredient> thirdLevelIngredientPairings) {
		this.thirdLevelIngredientPairings = thirdLevelIngredientPairings;
	}
}
