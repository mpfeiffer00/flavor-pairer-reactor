package com.tank.flavorpairer.object;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum Ingredient {
	APPLE("apple", "bacon", "cinnamon", "coriander", "fennel", "madeira", "nutmeg"),

	// cheese (e.g., Brie, Reblochon, ricotta)
	APRICOT("apricot", "apple", "apricot", "cinnamon", "nutmeg", "rosemary"),

	BACON("bacon", "mushroom"),

	// bell peppers, esp. red, roasted
	BASIL("basil", "apricot, bell pepper", "chive", "cinnamon", "fennel", "rosemary", "zucchini"),

	// CHEESE, esp. feta, Fontina, goat, mozzarella, Parmesan
	BELL_PEPPER("bell pepper", "bacon", "basil", "bell pepper", "cheese goat", "chive", "fennel", "rosemary", "thyme",
			"zucchini"),

	// what do about CHEESE—IN GENERAL
	CHEESE_CHEDDAR("cheese cheddar", "apple", "bacon", "fennel", "thyme"),

	// apples, esp. green
	CHEESE_GOAT("cheese goat", "apple", "apricot", "basil", "bell pepper", "chive", "cinnamon", "fennel", "nutmeg",
			"rosemary", "thyme"),

	CHEESE_GRUYERE("cheese gruyere", "apple", "thyme"),

	CHIVE("chive", "basil", "fennel", "thyme", "zucchini"),

	CINNAMON("cinnamon", "apple", "apricot", "bell pepper", "coriander", "fennel", "nutmeg", "zucchini"),

	CORIANDER("coriander", "apple", "basil", "cinnamon", "fennel", "nutmeg"),

	FENNEL("fennel", "apple", "basil", "bell pepper", "chive", "coriander", "cheese goat", "cheese gruyere", "nutmeg",
			"rosemary", "thyem", "zucchini"),

	MADEIRA("madeira"),

	MUSHROOM("mushroom", "bacon", "basil", "bell pepper", "cheese gruyere", "chive", "coriander", "fennel", "madeira",
			"nutmeg", "rosemary", "thyme"),

	NUTMEG("nutmeg", "apple", "cinnamon", "coriander", "mushroom", "thyme"),

	ROSEMARY("rosemary", "apple", "apricto", "bacon", "bell pepper", "chive", "fennel", "mushroom", "thyme",
			"zucchini"),

	THYME("thyme", "apple", "bacon", "basil", "bell pepper", "chive", "fennel", "mushroom", "nutmeg", "rosemary",
			"zucchini"),

	ZUCCHINI("zucchini", "basil", "bell pepper", "cheese cheddar", "cheese gruyere", "cheese goat", "cinnamon",
			"coriander", "thyme");

	private String name;
	private String[] pairings;

	private static final Map<String, Ingredient> ingredientsByName = new HashMap<>();

	static {
		for (final Ingredient ingredient : Ingredient.values()) {
			ingredientsByName.put(ingredient.getName(), ingredient);
		}
	}

	private Ingredient(String name, String... pairings) {
		this.name = name;
		this.pairings = pairings;
	}

	public String getName() {
		return name;
	}

	public EnumSet<Ingredient> getPairings() {
		final EnumSet<Ingredient> ingredients = EnumSet.noneOf(Ingredient.class);
		ingredients.addAll(Arrays.asList(pairings).stream().map(e -> ingredientsByName.get(e)).collect(Collectors.toList()));
		return ingredients;
	}
}
