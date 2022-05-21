package fr.uge.jee.springmvc.pokematch;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserformController {

    private User user = new User();
    private PokemonWithApiService pokemonWithApiService = new PokemonWithApiService();
    Map<Integer, String> pokemonCache = new HashMap<Integer, String>();

    @GetMapping("/userform")
    public String getUserform() {

        for (int i = 1; i < 41; i++) {
            pokemonCache.put(i, pokemonWithApiService.getPokemon(i));
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

        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        model.addAttribute("hashcodeUser", hashcodeUser);
        model.addAttribute("pokemonName", pokemonName);

        return "userform";
    }

    public String lookalikePokemon(int userHashcode, Map<Integer, String> map) {
        int distance = 2000000000;
        String lookalikePokemon = "";

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if(Math.abs(userHashcode - Math.abs(entry.getValue().hashCode())) < distance){
                distance = userHashcode - entry.getValue().hashCode();
                lookalikePokemon = entry.getValue();
            }
        }
        return lookalikePokemon;
    }
}
