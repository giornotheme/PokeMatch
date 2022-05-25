package fr.uge.jee.springmvc.pokematch;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserformController {

    private User user = new User();
    private PokemonWithApiService pokemonWithApiService = new PokemonWithApiService();
    Map<String, Integer> pokemonCache = new HashMap<>();

    @GetMapping("/userform")
    public String getUserform(@CookieValue(value = "listPokemonFetiche", defaultValue = "ma liste de pokemon fetiche") String listPokemonFetiche,
                              Model model) {

        if(pokemonCache.size() == 0){
            for (int i = 1; i < 41; i++) {
                pokemonCache.put(pokemonWithApiService.getPokemon(i), 0);
                System.out.println(pokemonWithApiService.getPokemon(i) + " " + pokemonWithApiService.getPokemon(i).hashCode());
            }
        }

        //model.addAttribute("listPokemonFetiche", listPokemonFetiche);

        return "userform";
    }

    @PostMapping("/userform")
    public String hashcodeUserAndPokemon(@RequestParam("nom") String nom,
                                         @RequestParam("prenom") String prenom,
                                         //HttpServletResponse response,
                                         Model model) {

        //response.addCookie(new Cookie("listPokemonFetiche", "listPokemonFetiche.toString()"));

        int hashcodeUser = nom.hashCode() + prenom.hashCode();
        String pokemonName = lookalikePokemon(hashcodeUser, pokemonCache);
        addOccurencePokemon(pokemonName, pokemonCache);

        List<Integer> sortPokemonHashMap = new ArrayList<>(pokemonCache.values());
        List<String> listPokemonFetiche = new ArrayList<>(10);


        Collections.sort(sortPokemonHashMap);
        System.out.println(sortPokemonHashMap);
        System.out.println(pokemonCache);

        for (int i = 1; i < 11; i++) {
            List<String> allPokemonWithSameOccurence = findPokemonByOccurence(sortPokemonHashMap.get(sortPokemonHashMap.size() - i), pokemonCache);
            if (listPokemonFetiche.contains(allPokemonWithSameOccurence.get(0))) {
                int new_index = allPokemonWithSameOccurence.indexOf((listPokemonFetiche.get(listPokemonFetiche.size() - 1)));
                allPokemonWithSameOccurence.remove(new_index);
                listPokemonFetiche.add(i - 1, allPokemonWithSameOccurence.get(new_index));
            } else {
                listPokemonFetiche.add(i - 1, allPokemonWithSameOccurence.get(0));
            }
        }

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
                break;
            }
        }
    }

    public List<String> findPokemonByOccurence(int occurence, Map<String, Integer> map) {
        List<String> allPokemonWithSameOccurence = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (occurence == entry.getValue()) {
                allPokemonWithSameOccurence.add(entry.getKey());
            }
        }
        return allPokemonWithSameOccurence;
    }
}
