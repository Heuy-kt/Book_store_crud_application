package com.prunnytest.bookstore.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/admin")
public class AdminController {

    @GetMapping("fetch")
    public String get(){
        return "Admin :: Get";
    }

    @PostMapping("post")
    public String post(){
        return "Admin :: Post";
    }

    @PutMapping("put")
    public String put(){
        return "Admin:: put";
    }

    @DeleteMapping("delete")
    public String remove(){
        return "Admin :: Delete";
    }
}
