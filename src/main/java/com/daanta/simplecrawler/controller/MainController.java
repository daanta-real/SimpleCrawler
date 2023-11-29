package com.daanta.simplecrawler.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/"})
public class MainController {

    @RequestMapping("")
    String index() {
        return "index";
    }

}
