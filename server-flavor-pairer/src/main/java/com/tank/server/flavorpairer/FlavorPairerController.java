package com.tank.server.flavorpairer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.flavorpairer.IngredientPairingEngine;
import com.tank.flavorpairer.object.Ingredient;
import com.tank.flavorpairer.object.IngredientPairingResponse;

@RestController
@SpringBootApplication
public class FlavorPairerController {
	public static void main(String[] args) {
		SpringApplication.run(FlavorPairerController.class, args);
	}

	@Autowired
	private FPC fpc;

	@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
	public String index() throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(fpc.index());
	}

	@GetMapping(value = "/api/flavorpairing", produces = MediaType.APPLICATION_JSON_VALUE)
	public IngredientPairingResponse greeting(
			@RequestParam(value = "ingredient", defaultValue = "bacon") String ingredient) {
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
				.computePairings(Ingredient.getIngredient(ingredient.toLowerCase()));
		return ingredientPairingResponse;
	}
}
