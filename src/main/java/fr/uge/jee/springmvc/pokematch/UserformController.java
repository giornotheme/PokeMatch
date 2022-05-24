package fr.uge.jee.springmvc.pokematch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserformController {

	private User user = new User();
	private PokemonWithApiService pokemonWithApiService = new PokemonWithApiService();
	Map<String, Integer> pokemonCache = new HashMap<String, Integer>();

	@GetMapping("/userform")
	public String getUserform() {

		for (int i = 1; i < 41; i++) {
			pokemonCache.put(pokemonWithApiService.getPokemon(i), 0);
			System.out.println(pokemonWithApiService.getPokemon(i) + " " + pokemonWithApiService.getPokemon(i).hashCode());
		}

		return "userform";
	}

	@PostMapping("/userform")
	public String hashcodeUserAndPokemon(@RequestParam("nom") String nom,
	                                     @RequestParam("prenom") String prenom,
	                                     Model model) {

		int hashcodeUser = nom.hashCode() + prenom.hashCode();

		String pokemonName = lookalikePokemon(hashcodeUser, pokemonCache);
		addOccurencePokemon(pokemonName, pokemonCache);

		List<String> listPokemonFetiche = getListPokemonFetiche(pokemonCache);

		model.addAttribute("nom", nom);
		model.addAttribute("prenom", prenom);
		model.addAttribute("hashcodeUser", hashcodeUser);
		model.addAttribute("pokemonName", pokemonName);
		model.addAttribute("listPokemonFetiche", listPokemonFetiche);

		return "userform";
	}

	public String lookalikePokemon(int userHashcode, Map<String, Integer> map) {
		int distance = 2000000000;
		String lookalikePokemon = "";

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (Math.abs(userHashcode - Math.abs(entry.getKey().hashCode())) < distance) {
				distance = userHashcode - entry.getKey().hashCode();
				lookalikePokemon = entry.getKey();
			}
		}
		return lookalikePokemon;
	}

	public void addOccurencePokemon(String name, Map<String, Integer> map) {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (name == entry.getKey()) {
				entry.setValue(entry.getValue() + 1);
			}
		}
	}

	public List<String> getListPokemonFetiche(Map<String, Integer> map) {
		List<String> listPokemonFetiche = new ArrayList<>(10);
		Map.Entry<String, Integer> one = null;
		Map.Entry<String, Integer> two = null;
		Map.Entry<String, Integer> three = null;
		Map.Entry<String, Integer> four = null;
		Map.Entry<String, Integer> five = null;
		Map.Entry<String, Integer> six = null;
		Map.Entry<String, Integer> seven = null;
		Map.Entry<String, Integer> eight = null;
		Map.Entry<String, Integer> nine = null;
		Map.Entry<String, Integer> ten = null;

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (one == null || entry.getValue().compareTo(one.getValue()) > 0) {
				one = entry;
				listPokemonFetiche.add(0, one.getKey());
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (two == null || entry.getValue().compareTo(two.getValue()) > 0 && entry.getValue() < one.getValue()) {
				two = entry;
				listPokemonFetiche.add(1, two.getKey());
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (three == null || entry.getValue().compareTo(three.getValue()) > 0 && entry.getValue() < two.getValue()) {
				three = entry;
				listPokemonFetiche.add(2, three.getKey());
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (four == null || entry.getValue().compareTo(four.getValue()) > 0 && entry.getValue() < three.getValue()) {
				four = entry;
				listPokemonFetiche.add(3, four.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (five == null || entry.getValue().compareTo(five.getValue()) > 0 && entry.getValue() < four.getValue()) {
				five = entry;
				listPokemonFetiche.add(4, four.getKey());
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (six == null || entry.getValue().compareTo(six.getValue()) > 0 && entry.getValue() < five.getValue()) {
				six = entry;
				listPokemonFetiche.add(5, six.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (seven == null || entry.getValue().compareTo(seven.getValue()) > 0 && entry.getValue() < six.getValue()) {
				seven = entry;
				listPokemonFetiche.add(6, seven.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (eight == null || entry.getValue().compareTo(eight.getValue()) > 0 && entry.getValue() < seven.getValue()) {
				eight = entry;
				listPokemonFetiche.add(7, eight.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (nine == null || entry.getValue().compareTo(nine.getValue()) > 0 && entry.getValue() < eight.getValue()) {
				nine = entry;
				listPokemonFetiche.add(8, nine.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (ten == null || entry.getValue().compareTo(ten.getValue()) > 0 && entry.getValue() < nine.getValue()) {
				ten = entry;
				listPokemonFetiche.add(9, ten.getKey());
			}
		}
		return listPokemonFetiche;
	}
}
