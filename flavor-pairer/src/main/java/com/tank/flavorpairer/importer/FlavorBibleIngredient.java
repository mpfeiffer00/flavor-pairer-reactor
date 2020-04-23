package com.tank.flavorpairer.importer;

import java.util.HashSet;
import java.util.Set;

public class FlavorBibleIngredient {
	private String ingredientName;
	private final Set<FlavorBibleIngredient> ingredients = new HashSet<>();
	private PairingLevel pairingLevel;
	private final Set<String> flavorAffinities = new HashSet<>();

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

	public void addFlavorBibleIngredient(FlavorBibleIngredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void addFlavorBibleIngredient(Set<FlavorBibleIngredient> ingredientPairings) {
		this.ingredients.addAll(ingredientPairings);
	}

	public PairingLevel getPairingLevel() {
		return pairingLevel;
	}

	public void setPairingLevel(PairingLevel pairingLevel) {
		this.pairingLevel = pairingLevel;
	}

	public void addFlavorAffinity(String flavorAffinity) {
		flavorAffinities.add(flavorAffinity);
	}

	public void addFlavorAffinities(Set<String> flavorAffinities) {
		this.flavorAffinities.addAll(flavorAffinities);
	}

	public Set<String> getFlavorAffinities() {
		return flavorAffinities;
	}

	@Override
	public String toString() {
		return "FlavorBibleIngredient [ingredientName=" + ingredientName + ", ingredients=" + ingredients
				+ ", pairingLevel=" + pairingLevel + ", flavorAffinities=" + flavorAffinities + "]";
	}
}
