package com.tank.flavorpairer;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientPairingResponse;

public class IngredientPairingEngineTest {
	@Test
	public void testFirstLevelPairings_Zucchini() {
		final IngredientPairingResponse pairingResponse = computePairings(Ingredient.ZUCCHINI);

		Assertions.assertThat(pairingResponse).isNotNull();
		Assertions.assertThat(pairingResponse.getFirstLevelIngredientPairings()).isNotEmpty().contains(Ingredient.BASIL)
				.doesNotContain(Ingredient.BACON);
		Assertions.assertThat(pairingResponse.getSecondLevelIngredientPairings()).isNotEmpty()
				.contains(Ingredient.BACON);

	}

	private static IngredientPairingResponse computePairings(Ingredient ingredient) {
		final IngredientPairingEngine engine = new IngredientPairingEngine();
		return engine.computePairings(ingredient);
	}
}
