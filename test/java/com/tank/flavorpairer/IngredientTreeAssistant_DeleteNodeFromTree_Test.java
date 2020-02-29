package com.tank.flavorpairer;

import static com.tank.flavorpairer.IngredientTestUtil.createIngredientNode;

import org.junit.Assert;
import org.junit.Test;

import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientNode;

public class IngredientTreeAssistant_DeleteNodeFromTree_Test {
	@Test(expected = IllegalArgumentException.class)
	public void testNullTree() {
		IngredientTreeProcessor.deleteNodeFromTree(null, Ingredient.APPLE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullIngredient() {
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE);
		IngredientTreeProcessor.deleteNodeFromTree(rootNode, (Ingredient) null);
	}

	@Test
	public void testIngredientNotFound() {
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE);
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.APRICOT);
		Assert.assertEquals(createIngredientNode(Ingredient.APPLE), actualNode);
	}

	@Test
	public void testDeleteRoot() {
		// Tree
		// A*
		final IngredientNode rootNode = createIngredientNode(Ingredient.APPLE);
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.APPLE);
		Assert.assertNull(actualNode);
	}

	@Test
	public void testTwoElementsLeft_DeleteChild() {
		// Tree
		// .B
		// A*
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				(IngredientNode) null);
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.APPLE);
		IngredientAssertionUtil.containsAllIngredients(createIngredientNode(Ingredient.BASIL), actualNode);
	}

	@Test
	public void testTwoElementsRight_DeleteChild() {
		// Tree
		// .B
		// ..C*
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, (IngredientNode) null,
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.CINNAMON);
		IngredientAssertionUtil.containsAllIngredients(createIngredientNode(Ingredient.BASIL), actualNode);
	}

	@Test
	public void testLeftRightDepth2_DeleteLeft() {
		// Tree
		// .B
		// A*C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.APPLE);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.BASIL, (IngredientNode) null,
				createIngredientNode(Ingredient.CINNAMON));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testLeftRightDepth2_DeleteRight() {
		// Tree
		// .B
		// A C*
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.CINNAMON);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNode(Ingredient.APPLE), (IngredientNode) null);

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testLeftRightDepth2_DeleteRoot() {
		// Tree
		// .B
		// A*C
		final IngredientNode rootNode = createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE),
				createIngredientNode(Ingredient.CINNAMON));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.BASIL);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.APPLE, (IngredientNode) null,
				createIngredientNode(Ingredient.CINNAMON));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testLeftHasChildDepth2_DeleteChild() {
		// Tree
		// ..C
		// .B*Z
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.BASIL);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.APPLE), createIngredientNode(Ingredient.ZUCCHINI));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testLeftHasChildDepth2_DeleteGrandchild() {
		// Tree
		// ..C
		// .B Z
		// A*
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.APPLE);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.ZUCCHINI));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testLeftHasChildDepth2_DeleteRoot() {
		// Tree
		// ..C*
		// .B Z
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.CINNAMON);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNode(Ingredient.APPLE), createIngredientNode(Ingredient.ZUCCHINI));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testLeftHasChildDepth2_DeleteRight() {
		// Tree
		// ..C
		// .B Z*
		// A
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL, createIngredientNode(Ingredient.APPLE), (IngredientNode) null),
				createIngredientNode(Ingredient.ZUCCHINI));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.ZUCCHINI);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.BASIL,
				createIngredientNode(Ingredient.APPLE), createIngredientNode(Ingredient.CINNAMON));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testRightHasChildDepth2_DeleteRightGranchild() {
		// Tree
		// ..C
		// .B F
		// ....Z*
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.ZUCCHINI);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testRightHasChildDepth2_DeleteRightChild() {
		// Tree
		// ..C
		// .B F*
		// ....Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.FENNEL);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.ZUCCHINI));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testRightHasChildDepth2_DeleteRoot() {
		// Tree
		// ..C*
		// .B F
		// ....Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.CINNAMON);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.FENNEL,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.ZUCCHINI));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testRightHasChildDepth2_DeleteLeft() {
		// Tree
		// ..C
		// .B*F
		// ....Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));

		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.BASIL);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.FENNEL,
				createIngredientNode(Ingredient.CINNAMON), createIngredientNode(Ingredient.ZUCCHINI));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testFullTree_DeleteNode1() {
		// Tree
		// ...C
		// .B..F
		// ...D*Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.CORIANDER);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testFullTree_DeleteNode2() {
		// Tree
		// ...C
		// .B..F*
		// ...D.Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.FENNEL);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.CORIANDER,
						(IngredientNode) null, createIngredientNode(Ingredient.ZUCCHINI)));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}

	@Test
	public void testFullTree_DeleteRoot() {
		// Tree
		// ...Ci*
		// .B...F
		// ...Co.Z
		final IngredientNode rootNode = createIngredientNode(Ingredient.CINNAMON,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL,
						createIngredientNode(Ingredient.CORIANDER), createIngredientNode(Ingredient.ZUCCHINI)));
		final IngredientNode actualNode = IngredientTreeProcessor.deleteNodeFromTree(rootNode, Ingredient.CINNAMON);

		final IngredientNode expectedNode = createIngredientNode(Ingredient.CORIANDER,
				createIngredientNode(Ingredient.BASIL), createIngredientNode(Ingredient.FENNEL, (IngredientNode) null,
						createIngredientNode(Ingredient.ZUCCHINI)));

		IngredientAssertionUtil.containsAllIngredients(expectedNode, actualNode);
	}
}
