package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientTreeUtil_Depth_Test {
	@Test
	public void testEmpty() {

		final int depth = IngredientTreeUtil.getDepth(new IngredientTree().getRoot());
		Assert.assertEquals("depth:not equals", 0, depth);
	}

	@Test
	public void testOnlyRoot() {
		// Tree
		// .A
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON));

		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 1, depth);
	}

	@Test
	public void testSimple_Balanced() {
		// Tree
		// .A
		// B C

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testRightNoNodes_Left1() {
		// Tree
		// .A
		// B
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null));

		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testSimple_Left2_Right1() {
		// Tree
		// ..A
		// .B C
		// D

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 3, depth);
	}

	@Test
	public void testSimple_Left3_Right1() {
		// Tree
		// ...C
		// .B...Z
		// A B

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.BELL_PEPPER));

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 3, depth);
	}

	@Test
	public void testSimple_Left4_Right1() {
		// Tree
		// ....C
		// ..B...Z
		// A..B
		// ..B

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.BELL_PEPPER, createIngredientNode(Ingredient.BASIL),
						(IngredientNode) null));

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 4, depth);
	}

	@Test
	public void testLeft2_Right0() {
		// Tree
		// .B
		// A C
		final IngredientTree tree = IngredientTreeProcessor
				.constructIngredientTree(Lists.newArrayList(Ingredient.CINNAMON, Ingredient.BACON, Ingredient.APPLE));
		final int depth = IngredientTreeUtil.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
		Assert.assertEquals("depth:not equals", 1, IngredientTreeUtil.getDepth(tree.getRoot().getLeftNode()));
		Assert.assertEquals("depth:not equals", 1, IngredientTreeUtil.getDepth(tree.getRoot().getRightNode()));
	}
}
