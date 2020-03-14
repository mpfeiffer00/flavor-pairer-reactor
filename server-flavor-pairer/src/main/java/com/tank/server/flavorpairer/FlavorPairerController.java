package com.tank.server.flavorpairer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tank.server.flavorpairer.object.FlavorPairer;

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
