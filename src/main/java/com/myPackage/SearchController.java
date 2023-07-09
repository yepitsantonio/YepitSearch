package com.myPackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

@Controller
public class SearchController {

    //Singleton pattern for API key. Create an account at serpapi.com for your api key.
    private final String api_key = "6e0cf279ff1335075a2f412aa14de4c9093333079ae2ccd15bbb6321456aa494";

    //Annotation maps GET request to the path endpoint. Value of query parameter is bound to String query from the html input. Model passes data to the view.
    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) throws JsonProcessingException {
        //Retrieve JSON string from the serpapi call
        try {
            String searchURL = "https://serpapi.com/search.json?q=" + query + "&location=Austin,+Texas,+United+States&hl=en&gl=us&google_domain=google.com&api_key=" + api_key;
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject(searchURL, String.class);

            //JsonNode allows processing of the json response and ObjectMapper allows converting json data to objects.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            /*
            Check to see if the key value of "organic_results" exists within the json response, if so get the fields required for a SearchResult object
            and add it to an ArrayList to be sent to the view.
            */
            if (jsonNode.has("organic_results")) {
                JsonNode resultsNode = jsonNode.get("organic_results");

                ArrayList<SearchResult> searchResultArrayList = new ArrayList<>();

                for (JsonNode resultNode : resultsNode) {
                    String source = resultNode.get("source").asText();
                    String title = resultNode.get("title").asText();
                    String link = resultNode.get("link").asText();
                    String displayedLink = resultNode.get("displayed_link").asText();
                    String snippet = resultNode.has("snippet") ? resultNode.get("snippet").asText() : "";

                    SearchResult searchResult = new SearchResult(source, title, link, displayedLink, snippet);
                    searchResultArrayList.add(searchResult);
                }
                model.addAttribute("searchResultArrayList", searchResultArrayList);
                return "search_results";
            }

            //If search query does not return results, return an error message to the view.
            else {
                String errorMessage = "Could not find anything with your search of " + query;
                model.addAttribute("errorMessage", errorMessage);
                return "error";
            }
        }
        catch (Exception e){
            String errorMessage = "An error has occurred.";
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }
}
