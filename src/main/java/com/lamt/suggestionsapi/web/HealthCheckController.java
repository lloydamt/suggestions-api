package com.lamt.suggestionsapi.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    @GetMapping("")
    public ResponseEntity<HttpStatus> getHealthyEnvironment() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
