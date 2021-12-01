package pl.polsl.fitstat.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class RestSpringBootController {

    private String clientId = "919304424653-gr2lkog75fb4f1otn63o7jvbc0ovd3au.apps.googleusercontent.com";
    private String clientSecret = "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";

    @GetMapping("/resttest")
    public List<Object> getRest(){
        String uri = "https://restcountries.com/v3.1/all";
        RestTemplate restTemplate = new RestTemplate();
        Object[] result = restTemplate.getForObject(uri, Object[].class);
        assert result != null;
        return Arrays.asList(result);
    }

    @GetMapping("/g1")
    public List<Object> getSources(){
        String uri = "https://www.googleapis.com/fitness/v1/users/me/dataSources";
        RestTemplate restTemplate = new RestTemplate();
        Object[] result = restTemplate.getForObject(uri, Object[].class);
        assert result != null;
        return Arrays.asList(result);
    }



}
