package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientTreeUtil_CountNumberOfChildren_Test {
	@Test
	public void testOnlyRoot() {
		// Tree
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE);
		Assert.assertEquals(1, IngredientTreeUtil.countNumberOfChildren(rootNode));
	}

	@Test
	public void test1LeftOfRoot() {
		// Tree
		// .B
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);
		Assert.assertEquals(2, IngredientTreeUtil.countNumberOfChildren(rootNode));
	}

	@Test
	public void test1RightOfRoot() {
		// Tree
		// .A
		// ..B
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE, (IngredientNode) null,
				createIngredientNode(Ingredient.BASIL));
		Assert.assertEquals(2, IngredientTreeUtil.countNumberOfChildren(rootNode));
	}

	@Test
	public void testBothLeafsOfRoot() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		Assert.assertEquals(3, IngredientTreeUtil.countNumberOfChildren(rootNode));
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
		Assert.assertEquals(4, IngredientTreeUtil.countNumberOfChildren(rootNode));
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
		Assert.assertEquals(4, IngredientTreeUtil.countNumberOfChildren(rootNode));
	}

	@Test
	public void testRightFull_Balanced() {
		// Tree
		// ...C
		// .B..F
		// ...D.Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI)));
		Assert.assertEquals(5, IngredientTreeUtil.countNumberOfChildren(rootNode));
	}
}
