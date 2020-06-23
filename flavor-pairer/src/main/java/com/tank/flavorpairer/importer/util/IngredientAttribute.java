package com.tank.flavorpairer.importer.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public enum IngredientAttribute {
	SEASON("Season"), WEIGHT("Weight"), VOLUME("Volume"), TECHNIQUE("Techniques"), TASTE("Taste"), TIPS("Tips");

	private static final Map<String, IngredientAttribute> attributesByText = new HashMap<>();
	public final String attributeText;

	private IngredientAttribute(String attributeText) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(attributeText));
		this.attributeText = attributeText;
	}

	static {
		for (final IngredientAttribute attribute : values()) {
			attributesByText.put(attribute.getAttributeText(), attribute);
		}
	}

	public String getAttributeText() {
		return attributeText;
	}

	public static IngredientAttribute getAttributeByText(String text) {
		return attributesByText.get(text);
	}
}
