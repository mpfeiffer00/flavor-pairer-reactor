package com.tank.flavorpairer.importer;

import java.util.HashSet;
import java.util.Set;

public class FlavorBibleIngredient {
	private String ingredientName;
	private String season;
	private String taste;
	private String weight;
	private String volume;
	private String tips;
	private final Set<FlavorBibleIngredient> ingredients = new HashSet<>();
	private PairingLevel pairingLevel;
	private final Set<String> flavorAffinities = new HashSet<>();
	private Set<String> examples;
	private Set<String> especially;
	private Set<String> similarities;
	private Set<String> quotes;

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

	public Set<String> getExamples() {
		return examples;
	}

	public void setExamples(Set<String> examples) {
		this.examples = examples;
	}

	public Set<String> getEspecially() {
		return especially;
	}

	public void setEspecially(Set<String> especiallies) {
		this.especially = especiallies;
	}

	public Set<String> getSimilarities() {
		return similarities;
	}

	public void setSimilarities(Set<String> similarities) {
		this.similarities = similarities;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Set<String> getQuotes() {
		return quotes;
	}

	public void setQuotes(Set<String> quotes) {
		this.quotes = quotes;
	}

	@Override
	public String toString() {
		return "FlavorBibleIngredient [ingredientName=" + ingredientName + ", season=" + season + ", taste=" + taste
				+ ", weight=" + weight + ", volume=" + volume + ", tips=" + tips + ", ingredients=" + ingredients
				+ ", pairingLevel=" + pairingLevel + ", flavorAffinities=" + flavorAffinities + ", examples=" + examples
				+ ", especially=" + especially + ", similarities=" + similarities + ", quotes=" + quotes + "]";
	}
}
