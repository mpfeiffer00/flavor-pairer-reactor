package com.tank.flavorpairer;

import org.assertj.core.api.Assertions;

import com.tank.flavorpairer.object.IngredientNode;

public class IngredientAssertionUtil {

	public static void containsAllIngredients(final IngredientNode expectedIngredientNode,
			final IngredientNode actualIngredientNode) {
		Assertions.assertThat(IngredientTreeUtil.getIngredients(actualIngredientNode))
				.containsAll(IngredientTreeUtil.getIngredients(expectedIngredientNode));
	}
}
