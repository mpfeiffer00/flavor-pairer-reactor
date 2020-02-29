package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientTreeUtil_FindMiddleNode_Test {
	@Test
	public void testOneElement_Root() {
		// Tree
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE);
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.APPLE, ingredientNode.getIngredient());
	}

	@Test
	public void testTwoElementsLeft_Root() {
		// Tree
		// .B
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testTwoElementsRight_Root() {
		// Tree
		// .B
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftRightDepthEquals_Root() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
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
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
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
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
	}

	@Test
	public void testMiddle_RightChild() {
		// Tree
		// C
		// .F
		// ..Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON, (IngredientNode) null,
				createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.FENNEL, ingredientNode.getIngredient());
	}

	@Test
	public void testMiddle_LeftChild() {
		// Tree
		// B
		// .F
		// C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, (IngredientNode) null,
				createIngredientNode(Ingredient.FENNEL, createIngredientNode(Ingredient.CINNAMON),
						(IngredientNode) null));
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
	}

	@Test
	public void testMiddle_Left3Right1() {
		// Tree
		// ...C
		// .B..Z
		// A C

		final IngredientNode leftNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode rootNode = createIngredientNode(Ingredient.CORIANDER, leftNode,
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testMiddle_Left1Right3() {
		// Tree
		// ...C
		// .B..F
		// ...C Z

		final IngredientNode rightNode = createIngredientNode(Ingredient.FENNEL,
				createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), rightNode);
		final IngredientNode ingredientNode = IngredientTreeUtil.findMiddleNode(rootNode);
		Assert.assertEquals(Ingredient.FENNEL, ingredientNode.getIngredient());
	}
}
