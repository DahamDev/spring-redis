package com.cache.cache.controller;

import com.cache.cache.external.Firm;
import com.cache.cache.external.FirmRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.Optional;

@RestController
@RequestMapping("firm")
public class Controller {

    @Autowired
    FirmRepository firmRepository;

    @GetMapping("/test")
    public String testPoint(){
        return "Ok working";
    }

    @GetMapping("/getfirm/{id}")
    public ResponseEntity getFirm(@PathVariable int id){
        System.out.println("get request received");
        Firm newFirm = firmRepository.getFirm(id);
        return ResponseEntity.ok().body(newFirm);
    }

    @PostMapping("add")
    public ResponseEntity addFirm(@RequestBody Firm newFirm){
        firmRepository.saveFirm(newFirm);
        return ResponseEntity.ok().body("OK");
    }
}
