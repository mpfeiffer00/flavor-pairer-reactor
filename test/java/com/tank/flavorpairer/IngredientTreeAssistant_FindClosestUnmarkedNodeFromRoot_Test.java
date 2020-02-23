package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;
import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNodeAndMark;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientTreeAssistant_FindClosestUnmarkedNodeFromRoot_Test {
	@Test
	public void testEmpty() {
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(null);
		Assert.assertEquals(null, ingredientNode);
	}

	@Test
	public void testOneElement_Root_marked() {
		// Tree
		// A
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.APPLE);
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(null, ingredientNode);
	}

	@Test
	public void testTwoElementsLeft_Root_marked() {
		// Tree
		// .B
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNodeAndMark(Ingredient.APPLE), (IngredientNode) null);
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testTwoElementsRight_Root_marked() {
		// Tree
		// .B
		// ..C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, (IngredientNode) null,
				createIngredientNodeAndMark(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftRightDepthEquals_Root_marked() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNodeAndMark(Ingredient.APPLE), createIngredientNodeAndMark(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftRightDepthEquals_Root_markedRight() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNodeAndMark(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftRightDepthEquals_Root_markedLeft() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNodeAndMark(Ingredient.APPLE), createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftRightDepthEquals_markedRoot_markedLeft() {
		// Tree
		// .B
		// A C
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.BASIL,
				createIngredientNodeAndMark(Ingredient.APPLE), createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftOffBy1_unmarkedRoot_markedRight() {
		// Tree
		// ..C
		// .B Z
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNodeAndMark(Ingredient.ZUCCHINI));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.CINNAMON, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftOffBy1_Root_markedRight() {
		// Tree
		// ..C*
		// .B Z*
		// A
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNodeAndMark(Ingredient.ZUCCHINI));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftOffBy1_Root_markedLeft() {
		// Tree
		// ..C*
		// .B Z
		// A*
		final IngredientNode rootNode = createIngredientNodeAndMark(
				Ingredient.CINNAMON, createIngredientNode(Ingredient.BASIL,
						createIngredientNodeAndMark(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.ZUCCHINI, ingredientNode.getIngredient());
	}

	@Test
	public void testLeftOffBy1_MiddleUnmarked() {
		// Tree
		// ..C*
		// .B Z*
		// A*
		final IngredientNode rootNode = createIngredientNodeAndMark(
				Ingredient.CINNAMON, createIngredientNode(Ingredient.BASIL,
						createIngredientNodeAndMark(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNodeAndMark(Ingredient.ZUCCHINI));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testRightOffBy1_markedRoot() {
		// Tree
		// ..C*
		// .B F
		// ....Z
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.FENNEL, ingredientNode.getIngredient());
	}

	@Test
	public void testRightOffBy1_markedRoot_markedRight() {
		// Tree
		// ..C*
		// .B F*
		// ....Z
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNodeAndMark(Ingredient.FENNEL,
						(IngredientNode) null, createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.BASIL, ingredientNode.getIngredient());
	}

	@Test
	public void testRightOffBy1_markedRoot_markedLeft() {
		// Tree
		// ...C*
		// .B* F
		// .....Z
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.CINNAMON,
				createIngredientNodeAndMark(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						(IngredientNode) null, createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.FENNEL, ingredientNode.getIngredient());
	}

	@Test
	public void testRightOffBy1_markedRoot_markedDepth2() {
		// Tree
		// ...C*
		// .B* F*
		// .....Z
		final IngredientNode rootNode = createIngredientNodeAndMark(Ingredient.CINNAMON,
				createIngredientNodeAndMark(Ingredient.BASIL), createIngredientNodeAndMark(Ingredient.FENNEL,
						(IngredientNode) null, createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.ZUCCHINI, ingredientNode.getIngredient());
	}

	// TODO: @Test
	public void testMiddle_marked() {
		// Tree
		// ...C
		// .B..F
		// ...D.Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode ingredientNode = IngredientTreeAssistant.findClosestUnmarkedNodeFromRoot(rootNode);
		Assert.assertEquals(Ingredient.ZUCCHINI, ingredientNode.getIngredient());
	}
}
