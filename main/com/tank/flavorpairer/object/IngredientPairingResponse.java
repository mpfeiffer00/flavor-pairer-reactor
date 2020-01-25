package com.tank.flavorpairer.object;

import java.util.EnumSet;

public class IngredientPairingResponse {
	private String ingredientName;
	private EnumSet<Ingredient> firstLevelIngredientPairings;
	private EnumSet<Ingredient> secondLevelIngredientPairings;
	private EnumSet<Ingredient> thirdLevelIngredientPairings;

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public EnumSet<Ingredient> getFirstLevelIngredientPairings() {
		return firstLevelIngredientPairings;
	}

	public void setFirstLevelIngredientPairings(EnumSet<Ingredient> firstLevelIngredientPairings) {
		this.firstLevelIngredientPairings = firstLevelIngredientPairings;
	}

	public EnumSet<Ingredient> getSecondLevelIngredientPairings() {
		return secondLevelIngredientPairings;
	}

	public void setSecondLevelIngredientPairings(EnumSet<Ingredient> secondLevelIngredientPairings) {
		this.secondLevelIngredientPairings = secondLevelIngredientPairings;
	}

	public EnumSet<Ingredient> getThirdLevelIngredientPairings() {
		return thirdLevelIngredientPairings;
	}

	public void setThirdLevelIngredientPairings(EnumSet<Ingredient> thirdLevelIngredientPairings) {
		this.thirdLevelIngredientPairings = thirdLevelIngredientPairings;
	}

}
