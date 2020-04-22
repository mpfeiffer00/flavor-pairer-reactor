package com.tank.flavorpairer.importer;

import java.util.HashSet;
import java.util.Set;

public class FlavorBibleIngredient {
	private String ingredientName;
	private final Set<FlavorBibleIngredient> ingredients = new HashSet<>();
	private PairingLevel pairingLevel;

	// private Set<FlavorBibleIngredient> affinities;
	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public Set<FlavorBibleIngredient> getIngredients() {
		return ingredients;
	}

	public void add(FlavorBibleIngredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public PairingLevel getPairingLevel() {
		return pairingLevel;
	}

	public void setPairingLevel(PairingLevel pairingLevel) {
		this.pairingLevel = pairingLevel;
	}
}
