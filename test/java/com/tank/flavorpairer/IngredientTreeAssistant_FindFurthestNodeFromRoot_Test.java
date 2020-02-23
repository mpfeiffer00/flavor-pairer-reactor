package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientTreeAssistant_FindFurthestNodeFromRoot_Test {
	@Test
	public void testEmpty() {
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(null);
		Assert.assertEquals(null, ingredientNode);
	}

	@Test
	public void testOneElement_Root() {
		// Tree
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE);
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.APPLE, ingredientNode.getIngredient());
	}

	@Test
	public void testTwoElementsLeft_Root() {
		// Tree
		// .B
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.APPLE, ingredientNode.getIngredient());
	}

	@Test
	public void testTwoElementsRight_Root() {
		// Tree
		// .B
		// ..C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, (IngredientNode) null,
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftRightDepthEquals_Root() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftOffBy1_Root() {
		// Tree
		// ..C
		// .B Z
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.APPLE, ingredientNode.getIngredient());
	}

	@Test
	public void testRightOffBy1_Root() {
		// Tree
		// ..C
		// .B F
		// ....Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.ZUCCHINI, ingredientNode.getIngredient());
	}

	@Test
	public void testMiddle() {
		// Tree
		// ...C
		// .B..F
		// ...D.Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findFurthestNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.ZUCCHINI, ingredientNode.getIngredient());
	}
}
