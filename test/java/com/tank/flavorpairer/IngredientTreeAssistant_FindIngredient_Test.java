package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientTreeAssistant_FindIngredient_Test {
	@Test
	public void testOneElement_Found() {
		// Tree
		// A
		final IngredientNode appleNode = createIngredientNode(Ingredient.APPLE);
		final IngredientTree ingredientTree = IngredientTestUtil.createTree(appleNode);

		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.APPLE,
				ingredientTree.getRoot());

		Assert.assertEquals(appleNode, ingredientNode);
	}

	@Test
	public void testOneElement_NotFound() {
		// Tree
		// A
		final IngredientTree ingredientTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.APPLE));

		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.ZUCCHINI,
				ingredientTree.getRoot());

		Assert.assertEquals(null, ingredientNode);
	}

	@Test
	public void testTwoElements_Left_Found() {
		// Tree
		// .B
		// A
		final IngredientTree ingredientTree = IngredientTestUtil.createTree(
				createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE), (IngredientNode) null));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.APPLE,
				ingredientTree.getRoot());
		Assert.assertEquals(createIngredientNode(Ingredient.APPLE), ingredientNode);
	}

	@Test
	public void testTwoElements_Left_NotFound() {
		// Tree
		// .B
		// A
		final IngredientTree ingredientTree = IngredientTestUtil.createTree(
				createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE), (IngredientNode) null));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.ZUCCHINI,
				ingredientTree.getRoot());
		Assert.assertEquals(null, ingredientNode);
	}

	@Test
	public void testTwoElements_Right_Found() {
		// Tree
		// .B
		// ..C
		final IngredientTree ingredientTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.BACON,
				(IngredientNode) null, createIngredientNode(Ingredient.CINNAMON)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.CINNAMON,
				ingredientTree.getRoot());
		Assert.assertEquals(createIngredientNode(Ingredient.CINNAMON), ingredientNode);
	}

	@Test
	public void testTwoElements_Right_NotFound() {
		// Tree
		// .B
		// ..C
		final IngredientTree ingredientTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.BACON,
				(IngredientNode) null, createIngredientNode(Ingredient.CINNAMON)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.ZUCCHINI,
				ingredientTree.getRoot());
		Assert.assertEquals(null, ingredientNode);
	}

	@Test
	public void testLeft3_Right1_Found() {
		// Tree
		// ...Bac
		// .A.....C
		// ..Bas...Z

		final IngredientNode leftIngredientNode = createIngredientNode(Ingredient.APPLE, (IngredientNode) null,
				createIngredientNode(Ingredient.BASIL));

		final IngredientNode rightIngredientNode = createIngredientNode(Ingredient.CINNAMON, (IngredientNode) null,
				createIngredientNode(Ingredient.ZUCCHINI));

		final IngredientTree ingredientTree = IngredientTestUtil
				.createTree(createIngredientNode(Ingredient.BACON, leftIngredientNode, rightIngredientNode));

		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.ZUCCHINI,
				ingredientTree.getRoot());
		Assert.assertEquals(createIngredientNode(Ingredient.ZUCCHINI), ingredientNode);
	}

	@Test
	public void testLeft3_Right1_NotFound() {
		// Tree
		// ...Bac
		// .A.....C
		// ..Bas...Z

		final IngredientNode leftIngredientNode = createIngredientNode(Ingredient.APPLE, (IngredientNode) null,
				createIngredientNode(Ingredient.BASIL));

		final IngredientNode rightIngredientNode = createIngredientNode(Ingredient.CINNAMON, (IngredientNode) null,
				createIngredientNode(Ingredient.ZUCCHINI));

		final IngredientTree ingredientTree = IngredientTestUtil
				.createTree(createIngredientNode(Ingredient.BACON, leftIngredientNode, rightIngredientNode));

		final IngredientNode ingredientNode = IngredientTreeAssistant.findIngredient(Ingredient.THYME,
				ingredientTree.getRoot());
		Assert.assertEquals(null, ingredientNode);
	}
}
