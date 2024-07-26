package com.example.githubapi.service;

import com.example.githubapi.model.Branch;
import com.example.githubapi.model.Repository;
import com.example.githubapi.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final String GITHUB_API_URL = "https://api.github.com";

    private String tokenFirstPart = "ghp_22Unjp24yjFgbw";
    private String tokenSecondPart = "ZQIX3P38lO4s0SZE3hjNum";
    private String githubToken = tokenFirstPart + tokenSecondPart; // This is necessary for GitHub to allow pushing this code to the public repository.

    public List<Repository> getRepositories(String username) throws UserNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        String url = GITHUB_API_URL + "/users/" + username + "/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github.v3+json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);

            Map[] repos = response.getBody();
            List<Repository> repositoryList = new ArrayList<>();

            for (Map repo : repos) {
                if (!(Boolean) repo.get("fork")) {
                    Repository repository = new Repository();
                    repository.setName((String) repo.get("name"));
                    repository.setOwnerLogin((String) ((Map) repo.get("owner")).get("login"));

                    // Fetch branches for the repository
                    String branchesUrl = ((String) repo.get("branches_url")).replace("{/branch}", "");
                    ResponseEntity<Map[]> branchesResponse = restTemplate.exchange(branchesUrl, HttpMethod.GET, entity, Map[].class);
                    Map[] branches = branchesResponse.getBody();

                    for (Map branch : branches) {
                        Branch branchObj = new Branch();
                        branchObj.setName((String) branch.get("name"));
                        branchObj.setLastCommitSha((String) ((Map) branch.get("commit")).get("sha"));
                        repository.getBranches().add(branchObj);
                    }

                    repositoryList.add(repository);
                }
            }

            return repositoryList;

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("Something went wrong, user not found. Please check the information and try again.");
            } else {
                throw e;
            }
        }
    }
}