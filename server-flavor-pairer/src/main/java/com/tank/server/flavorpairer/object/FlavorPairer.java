package com.tank.server.flavorpairer.object;

import java.util.Arrays;
import java.util.List;

public class FlavorPairer {
	private String ingredient;
	private List<String> pairings;

	public FlavorPairer(String ingredient, String... pairings) {
		this.ingredient = ingredient;
		this.pairings = Arrays.asList(pairings);
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public List<String> getPairings() {
		return pairings;
	}

	public void setPairings(List<String> pairings) {
		this.pairings = pairings;
	}
}
