package com.tank.flavorpairer.object;

import java.util.Set;

import com.google.common.collect.Sets;

public enum Ingredient {
	BASE("base", Sets.newHashSet()),
	APPLE("apple", Sets.newHashSet(Ingredient.BASE)),
	BACON("bacon", Sets.newHashSet(Ingredient.APPLE)),
	BASIL("basil", Sets.newHashSet(Ingredient.BASE)),
	BELL_PEPPER("bell pepper", Sets.newHashSet(Ingredient.BASE)),
	CINNAMON("cinnamon", Sets.newHashSet(Ingredient.BASE)),
	CORIANDER("coriander", Sets.newHashSet(Ingredient.BASE)),
	MADEIRA("madeira", Sets.newHashSet(Ingredient.BASE)),
	MUSHROOM("mushroom", Sets.newHashSet(Ingredient.BASE)),
	APRICOT("apricot", Sets.newHashSet(Ingredient.BASE)),
	CHIVE("chive", Sets.newHashSet(Ingredient.BASE)),
	NUTMEG("nutmeg", Sets.newHashSet(Ingredient.BASE)),
	ROSEMARY("rosemary", Sets.newHashSet(Ingredient.BASE)),
	FENNEL("fennel", Sets.newHashSet(Ingredient.BASE)),
	CHEDDAR_CHEESE("cheddar cheese", Sets.newHashSet(Ingredient.BASE)),
	GOAT_CHEESE("goat cheese", Sets.newHashSet(Ingredient.BASE)),
	GRUYERE_CHEESE("gruyere cheese", Sets.newHashSet(Ingredient.BASE)),
	THYME("thyme", Sets.newHashSet(Ingredient.APPLE, Ingredient.BASIL, Ingredient.BELL_PEPPER)),
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
