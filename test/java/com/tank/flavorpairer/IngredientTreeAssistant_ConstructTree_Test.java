package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientTreeAssistant_ConstructTree_Test {
	@Test
	public void testNoElements() {
		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(Lists.newArrayList());
		Assert.assertNull(tree.getRoot());
	}

	@Test
	public void testOneElement() {
		// Tree
		// A
		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.APPLE));
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.APPLE));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testTwoElements_LessThanRoot() {
		// Tree
		// .B
		// A
		final IngredientTree expectedTree = IngredientTestUtil.createTree(
				createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE), (IngredientNode) null));
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.APPLE));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testTwoElements_GreaterThanRoot() {
		// Tree
		// B
		// .C
		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.BACON,
				(IngredientNode) null, createIngredientNode(Ingredient.CINNAMON)));
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.CINNAMON));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testEqualDepth2() {
		// Tree
		// .B
		// A C
		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.BACON,
				createIngredientNode(Ingredient.APPLE), createIngredientNode(Ingredient.CINNAMON)));
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.APPLE, Ingredient.CINNAMON));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft2_Right0() {
		// Tree
		// ..C
		// .B
		// A
		// ->
		// .B
		// A.C

		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.BACON,
				createIngredientNode(Ingredient.APPLE), createIngredientNode(Ingredient.CINNAMON)));
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.CINNAMON, Ingredient.BACON, Ingredient.APPLE));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft2_Right1() {
		// Tree
		// ...C
		// .B...Z
		// A
		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(
				Lists.newArrayList(Ingredient.CINNAMON, Ingredient.ZUCCHINI, Ingredient.BACON, Ingredient.APPLE));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft3_Right1() {
		// Tree
		// ...BAS
		// ..BAC.C
		// A......Z

		final IngredientNode leftIngredientNode = createIngredientNode(Ingredient.BACON,
				createIngredientNode(Ingredient.APPLE), (IngredientNode) null);

		final IngredientNode rightIngredientNode = createIngredientNode(Ingredient.CINNAMON, (IngredientNode) null,
				createIngredientNode(Ingredient.ZUCCHINI));

		final IngredientTree expectedTree = IngredientTestUtil
				.createTree(createIngredientNode(Ingredient.BASIL, leftIngredientNode, rightIngredientNode));

		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(Lists.newArrayList(
				Ingredient.CINNAMON, Ingredient.ZUCCHINI, Ingredient.BACON, Ingredient.APPLE, Ingredient.BASIL));

		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft0_Right2() {
		// Tree
		// .B
		// ..C
		// ...Z
		// ->
		// .C
		// B.Z

		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BACON), createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(
				Lists.newArrayList(Ingredient.BACON, Ingredient.CINNAMON, Ingredient.ZUCCHINI));

		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft1_Right2() {
		// Tree
		// ...C
		// .B...T
		// .......Z
		final IngredientTree expectedTree = IngredientTestUtil.createTree(
				createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.BACON), createIngredientNode(
						Ingredient.THYME, (IngredientNode) null, createIngredientNode(Ingredient.ZUCCHINI))));
		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(
				Lists.newArrayList(Ingredient.CINNAMON, Ingredient.THYME, Ingredient.BACON, Ingredient.ZUCCHINI));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testLeft1_Right3() {
		// Tree
		// ...C
		// A....T
		// .Bas...Z
		// ->
		// ...C
		// .B...T
		// A.....Z

		final IngredientNode leftIngredientNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNode(Ingredient.APPLE), (IngredientNode) null);

		final IngredientNode rightIngredientNode = createIngredientNode(Ingredient.THYME, (IngredientNode) null,
				createIngredientNode(Ingredient.ZUCCHINI));

		final IngredientTree expectedTree = IngredientTestUtil
				.createTree(createIngredientNode(Ingredient.CINNAMON, leftIngredientNode, rightIngredientNode));

		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(Lists.newArrayList(
				Ingredient.CINNAMON, Ingredient.THYME, Ingredient.ZUCCHINI, Ingredient.APPLE, Ingredient.BASIL));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testRightRotate_Sorted() {
		// Tree
		// B
		// .F
		// C
		// ->
		// Tree
		// ..C
		// B..F

		final IngredientTree expectedTree = IngredientTestUtil.createTree(createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BACON), createIngredientNode(Ingredient.FENNEL)));
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.BACON, Ingredient.FENNEL, Ingredient.CINNAMON));
		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testRightRotate_RightNodeRotate() {
		// Tree
		// .B
		// ...F
		// ..C.Z
		// ->
		// Tree
		// ..F
		// B..Z
		// .C

		final IngredientNode rightNode = createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientTree expectedTree = IngredientTestUtil.createTree(
				createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.BACON), rightNode));

		final IngredientTree tree = IngredientTreeAssistant.constructIngredientTree(
				Lists.newArrayList(Ingredient.BACON, Ingredient.FENNEL, Ingredient.CINNAMON, Ingredient.ZUCCHINI));

		Assert.assertEquals(expectedTree, tree);
	}

	@Test
	public void testFullTree() {
		final IngredientTree ingredientTree = IngredientTreeAssistant
				.constructIngredientTree(Arrays.asList(Ingredient.values()));
		Assert.assertEquals(Ingredient.values().length, ingredientTree.getRoot().count());
	}
}
