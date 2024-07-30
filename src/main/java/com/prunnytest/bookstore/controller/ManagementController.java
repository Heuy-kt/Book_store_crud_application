package com.prunnytest.bookstore.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/management")
public class ManagementController {

    @GetMapping("fetch")
    public String get(){
        return "Management :: Get";
    }

    @PostMapping("post")
    public String post(){
        return "Management :: Post";
    }

    @PutMapping("put")
    public String put(){
        return "Management :: put";
    }

    @DeleteMapping("delete")
    public String remove(){
        return "Management :: Delete";
    }
}
