package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RootController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> helloMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("greetings", "Hello, welcome to the backend of pi studio!");
        map.put("deploy", "This app is deployed on Google Cloud Platform, possibly with Cloud Run or App Engine.");
        map.put("database", "This app uses Google Cloud Datastore as its database.");
        map.put("backendLanguage", "This app is written by sprint boot");
        map.put("storage", "Data is stored on Google Cloud Cloud Storage");
        map.put("authorization", "Only authorized user can access resources by Json Web Token");
        return map;
    }
}
