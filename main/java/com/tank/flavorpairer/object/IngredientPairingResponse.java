package com.tank.flavorpairer.object;

import java.util.Set;

public class IngredientPairingResponse {
	private Ingredient ingredient;
	private Set<Ingredient> firstLevelIngredientPairings;
	private Set<Ingredient> secondLevelIngredientPairings;
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

	public Set<Ingredient> getSecondLevelIngredientPairings() {
		return secondLevelIngredientPairings;
	}

	public void setSecondLevelIngredientPairings(Set<Ingredient> secondLevelIngredientPairings) {
		this.secondLevelIngredientPairings = secondLevelIngredientPairings;
	}

	public Set<Ingredient> getThirdLevelIngredientPairings() {
		return thirdLevelIngredientPairings;
	}

	public void setThirdLevelIngredientPairings(Set<Ingredient> thirdLevelIngredientPairings) {
		this.thirdLevelIngredientPairings = thirdLevelIngredientPairings;
	}
}
