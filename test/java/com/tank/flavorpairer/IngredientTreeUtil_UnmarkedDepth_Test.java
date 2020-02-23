package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;
import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNodeAndMark;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class IngredientTreeUtil_UnmarkedDepth_Test {
	@Test
	public void testOnlyRoot_Marked() {
		// Tree
		// .A*
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNodeAndMark(Ingredient.CINNAMON));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 0, depth);
	}

	@Test
	public void testSimple_Balanced_LeftMarked() {
		// Tree
		// .c
		// A*Z

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNodeAndMark(Ingredient.APPLE),
				createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testSimple_Balanced_RightMarked() {
		// Tree
		// .C
		// A Z*

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.APPLE),
				createIngredientNodeAndMark(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testSimple_Balanced_BothChildrenMarked() {
		// Tree
		// .C
		// A*Z*

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNodeAndMark(Ingredient.APPLE),
				createIngredientNodeAndMark(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 1, depth);
	}

	@Test
	public void testSimple_Balanced_AllMarked() {
		// Tree
		// .C*
		// A*Z*

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNodeAndMark(Ingredient.CINNAMON, createIngredientNodeAndMark(Ingredient.APPLE),
				createIngredientNodeAndMark(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 0, depth);
	}

	@Test
	public void testSimple_Balanced_RootMarked() {
		// Tree
		// .C*
		// A Z

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNodeAndMark(Ingredient.CINNAMON, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 0, depth);
	}

	@Test
	public void testRightNoNodes_Left1_AllMarked() {
		// Tree
		// .A*
		// B*
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNodeAndMark(Ingredient.CINNAMON, createIngredientNodeAndMark(Ingredient.APPLE),
				(IngredientNode) null));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 0, depth);
	}

	@Test
	public void testRightNoNodes_Left1_Marked() {
		// Tree
		// .C
		// A*
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNodeAndMark(Ingredient.APPLE),
				(IngredientNode) null));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 1, depth);
	}

	@Test
	public void testSimple_Left2_Right1_Marked() {
		// Tree
		// ..C
		// .B Z
		// A*

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON,
				createIngredientNodeAndMark(Ingredient.APPLE), (IngredientNode) null);

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testSimple_Left2_Right1_RightMarked() {
		// Tree
		// ..C
		// .B Z*
		// A

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(
				createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNodeAndMark(Ingredient.ZUCCHINI)));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 3, depth);
	}

	@Test
	public void testSimple_Left4_Right1_Marked() {
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

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 4, depth);
	}

	@Test
	public void testLeft2_Right0_Marked() {
		// Tree
		// .B
		// A C
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.CINNAMON, Ingredient.BACON, Ingredient.APPLE));
		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
		Assert.assertEquals("depth:not equals", 1, IngredientTreeUtil.getUnmarkedDepth(tree.getRoot().getLeftNode()));
		Assert.assertEquals("depth:not equals", 1, IngredientTreeUtil.getUnmarkedDepth(tree.getRoot().getRightNode()));
	}

	@Test
	public void testEmpty() {
		Assert.assertEquals("depth:not equals", 0, IngredientTreeUtil.getUnmarkedDepth(null));
	}

	@Test
	public void testOnlyRoot() {
		// Tree
		// .A
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON));

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
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

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
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

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
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

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
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

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
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

		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 4, depth);
	}

	@Test
	public void testLeft2_Right0() {
		// Tree
		// .B
		// A C
		final IngredientTree tree = IngredientTreeAssistant
				.constructIngredientTree(Lists.newArrayList(Ingredient.CINNAMON, Ingredient.BACON, Ingredient.APPLE));
		final int depth = IngredientTreeUtil.getUnmarkedDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
		Assert.assertEquals("depth:not equals", 1, IngredientTreeUtil.getUnmarkedDepth(tree.getRoot().getLeftNode()));
		Assert.assertEquals("depth:not equals", 1, IngredientTreeUtil.getUnmarkedDepth(tree.getRoot().getRightNode()));
	}
}
