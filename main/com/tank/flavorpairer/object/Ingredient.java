package com.tank.flavorpairer.object;

import java.util.Set;

import com.google.common.collect.Sets;

public enum Ingredient {
	SHIT("shit", Sets.newHashSet()),
	APPLES("apple", Sets.newHashSet(Ingredient.SHIT)),
	BACON("bacon", Sets.newHashSet(Ingredient.APPLES)),
	BASIL("basil", Sets.newHashSet(Ingredient.SHIT)),
	BELL_PEPPER("bell pepper", Sets.newHashSet(Ingredient.SHIT)),
	CINNAMON("cinnamon", Sets.newHashSet(Ingredient.SHIT)),
	CORIANDER("coriander", Sets.newHashSet(Ingredient.SHIT)),
	MADEIRA("madeira", Sets.newHashSet(Ingredient.SHIT)),
	MUSHROOM("mushroom", Sets.newHashSet(Ingredient.SHIT)),
	APRICOT("apricot", Sets.newHashSet(Ingredient.SHIT)),
	CHIVE("chive", Sets.newHashSet(Ingredient.SHIT)),
	NUTMEG("nutmeg", Sets.newHashSet(Ingredient.SHIT)),
	ROSEMARY("rosemary", Sets.newHashSet(Ingredient.SHIT)),
	FENNEL("fennel", Sets.newHashSet(Ingredient.SHIT)),
	CHEDDAR_CHEESE("cheddar cheese", Sets.newHashSet(Ingredient.SHIT)),
	GOAT_CHEESE("goat cheese", Sets.newHashSet(Ingredient.SHIT)),
	GRUYERE_CHEESE("gruyere cheese", Sets.newHashSet(Ingredient.SHIT)),
	THYME("thyme", Sets.newHashSet(Ingredient.APPLES, Ingredient.BASIL, Ingredient.BELL_PEPPER)),
	ZUCCHINI("zucchini", Sets.newHashSet(Ingredient.BASIL, Ingredient.BELL_PEPPER, Ingredient.CHEDDAR_CHEESE,
			Ingredient.GRUYERE_CHEESE, Ingredient.GOAT_CHEESE));

	private String name;
	private Set<Ingredient> pairings;

	private Ingredient(String name, Set<Ingredient> pairings) {
		this.name = name;
		this.pairings = pairings;
	}

	public String getName() {
		return name;
	}

	public Set<Ingredient> getPairings() {
		return pairings;
	}
}
