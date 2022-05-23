package fr.uge.jee.springmvc.pokematch;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PokemonWithApiService {
    String apiUrl = "https://pokeapi.co/api/v2/pokemon/";

    public String getPokemon(int id){
        try{
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(apiUrl + id + "/", ObjectNode.class);
            ObjectNode jsonObject = response.getBody();

            return jsonObject.path("name").asText();
        }catch (Exception e){
            System.out.println("Something wrong with pokeAPI" + e.getMessage());
            return e.getMessage();
        }

    }
}
