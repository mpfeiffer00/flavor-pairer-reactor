package com.tank.flavorpairer.importer.object;

import java.util.List;

import org.assertj.core.util.Lists;

import com.tank.flavorpairer.importer.FlavorBibleIngredient;
import com.tank.flavorpairer.importer.util.IngredientAttribute;

public class EndOfTextStateCriteria {
	public boolean hasUpdatedState;
	public IngredientAttribute ingredientAttribute;
	public FlavorBibleIngredient currentFlavorBibleIngredientHeading;
	public boolean isFlavorAffinityEntries;
	public boolean isEndingWithColon;
	public List<FlavorBibleIngredient> ingredientPairings = Lists.newArrayList();
}
