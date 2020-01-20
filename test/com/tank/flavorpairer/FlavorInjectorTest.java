package com.tank.flavorpairer;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;
import com.tank.flavorpairer.object.IngredientTree;

public class FlavorInjectorTest {
	@Test
	public void testDepth_Empty() {

		final int depth = FlavorInjector.getDepth(new IngredientTree().getRoot());
		Assert.assertEquals("depth:not equals", 0, depth);
	}

	@Test
	public void testDepth_OnlyRoot() {
		// Tree
		// .A
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON));

		final int depth = FlavorInjector.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 1, depth);
	}

	@Test
	public void testDepth_Simple_Balanced() {
		// Tree
		// .A
		// B C

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.APPLES),
				createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = FlavorInjector.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testDepth_RightNoNodes_Left1() {
		// Tree
		// .A
		// B
		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, createIngredientNode(Ingredient.APPLES),
				(IngredientNode) null));

		final int depth = FlavorInjector.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 2, depth);
	}

	@Test
	public void testDepth_Simple_Left2_Right1() {
		// Tree
		// ..A
		// .B C
		// D

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLES),
				(IngredientNode) null);

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = FlavorInjector.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 3, depth);
	}

	@Test
	public void testDepth_Simple_Left3_Right1() {
		// Tree
		// ...C
		// .B...Z
		// A B

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLES),
				createIngredientNode(Ingredient.BELL_PEPPER));

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = FlavorInjector.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 3, depth);
	}

	@Test
	public void testDepth_Simple_Left4_Right1() {
		// Tree
		// ....C
		// ..B...Z
		// A..B
		// ..B

		final IngredientNode leftNode = createIngredientNode(Ingredient.BACON, createIngredientNode(Ingredient.APPLES),
				createIngredientNode(Ingredient.BELL_PEPPER, createIngredientNode(Ingredient.BASIL),
						(IngredientNode) null));

		final IngredientTree tree = new IngredientTree();
		tree.setRoot(createIngredientNode(Ingredient.CINNAMON, leftNode, createIngredientNode(Ingredient.ZUCCHINI)));

		final int depth = FlavorInjector.getDepth(tree.getRoot());
		Assert.assertEquals("depth:not equals", 4, depth);
	}

	private static IngredientNode createIngredientNode(Ingredient ingredient) {
		return new IngredientNode(ingredient);
	}

	private static IngredientNode createIngredientNode(Ingredient ingredient, IngredientNode leftNode,
			IngredientNode rightNode) {
		final IngredientNode node = createIngredientNode(ingredient);
		if (leftNode != null) {
			node.setLeftNode(leftNode);
		}
		if (rightNode != null) {
			node.setRightNode(rightNode);
		}
		return node;
	}
}
