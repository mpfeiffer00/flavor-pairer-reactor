package com.tank.server.flavorpairer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tank.server.flavorpairer.object.FlavorPairer;

@RestController
public class FlavorPairerController {
	@GetMapping("/flavorpairing")
	public FlavorPairer greeting(@RequestParam(value = "ingredient", defaultValue = "World") String ingredient) {
		return createFlavorPairer(ingredient);
	}

	private static FlavorPairer createFlavorPairer(String ingredient) {
		switch (ingredient) {
		case "test":
			return new FlavorPairer(ingredient, "test", "1");
		default:
			return new FlavorPairer("candy", "lame");
		}
	}
}
