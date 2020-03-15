package com.tank.server.flavorpairer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tank.flavorpairer.IngredientPairingEngine;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientPairingResponse;

@RestController
@SpringBootApplication
public class FlavorPairerController {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FlavorPairerController.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Hello World!";
	}

	@GetMapping("/flavorpairing")
	public IngredientPairingResponse greeting(
			@RequestParam(value = "ingredient", defaultValue = "World") String ingredient) {
		return createFlavorPairer(ingredient);
	}

	private static IngredientPairingResponse createFlavorPairer(String ingredient) {
		if (StringUtils.isEmpty(ingredient)) {
			return null;
		}

		if (Ingredient.getIngredient(ingredient) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ingredient + " not found");
		}

		final IngredientPairingEngine ingredientPairingEngine = new IngredientPairingEngine();
		final IngredientPairingResponse ingredientPairingResponse = ingredientPairingEngine
				.computePairings(Ingredient.getIngredient(ingredient));
		return ingredientPairingResponse;
	}
}
