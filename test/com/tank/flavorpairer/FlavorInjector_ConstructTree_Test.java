package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientAssistant.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class FlavorInjector_ConstructTree_Test {
	@Test
	public void testNoElements() {
		final IngredientTree tree = FlavorInjector.constructIngredientTree(Lists.newArrayList());
		Assert.assertNull(tree.getRoot());
	}

	@Test
	public void testOneElement() {
		// Tree
		// A
		final IngredientTree expectedTree = createTree(createIngredientNode(Ingredient.APPLES));
		final IngredientTree tree = FlavorInjector.constructIngredientTree(Lists.newArrayList(Ingredient.APPLES));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testTwoElements_LessThanRoot() {
		// Tree
		// .B
		// A
		final IngredientTree expectedTree = createTree(
				createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLES), (IngredientNode) null));
		final IngredientTree tree = FlavorInjector
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.APPLES));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testTwoElements_GreaterThanRoot() {
		// Tree
		// B
		// .C
		final IngredientTree expectedTree = createTree(createIngredientNode(Ingredient.BACON, (IngredientNode) null,
				createIngredientNode(Ingredient.CINNAMON)));
		final IngredientTree tree = FlavorInjector
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.CINNAMON));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testEqualDepth2() {
		// Tree
		// .B
		// A C
		final IngredientTree expectedTree = createTree(createIngredientNode(Ingredient.BACON,
				createIngredientNode(Ingredient.APPLES), createIngredientNode(Ingredient.CINNAMON)));
		final IngredientTree tree = FlavorInjector
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.APPLES, Ingredient.CINNAMON));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft2_Right1() {
		// Tree
		// ...C
		// .A...Z
		// ..B
		final IngredientTree expectedTree = createTree(createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.APPLES, (IngredientNode) null, createIngredientNode(Ingredient.BACON)),
				createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientTree tree = FlavorInjector.constructIngredientTree(
				Lists.newArrayList(Ingredient.CINNAMON, Ingredient.ZUCCHINI, Ingredient.BACON, Ingredient.APPLES));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft3_Right1() {
		// Tree
		// ....C
		// .Bas..Z
		// A
		// .Bac

		final IngredientNode leftIngredientNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNode(Ingredient.APPLES, (IngredientNode) null, createIngredientNode(Ingredient.BACON)),
				(IngredientNode) null);

		final IngredientTree expectedTree = createTree(createIngredientNode(Ingredient.CINNAMON, leftIngredientNode,
				createIngredientNode(Ingredient.ZUCCHINI)));

		final IngredientTree tree = FlavorInjector.constructIngredientTree(Lists.newArrayList(Ingredient.CINNAMON,
				Ingredient.ZUCCHINI, Ingredient.BACON, Ingredient.APPLES, Ingredient.BASIL));
		Assert.assertEquals(expectedTree, tree);
	}

	private static IngredientTree createTree(IngredientNode ingredientNode) {
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(ingredientNode);
		return tree;
	}
}
