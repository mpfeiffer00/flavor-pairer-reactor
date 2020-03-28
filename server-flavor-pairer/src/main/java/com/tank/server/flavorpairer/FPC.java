package com.tank.server.flavorpairer;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.tank.flavorpairer.object.Ingredient;

@Service
public class FPC {
	public Set<String> index() {
		return Ingredient.getIngredientNames();
	}
}
