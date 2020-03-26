package com.finaltest.authentication.controller;

import com.finaltest.authentication.dto.AuthDto;
import com.finaltest.authentication.exception.AuthException;
import com.finaltest.authentication.service._AuthLogServices;
import com.finaltest.authentication.service._AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class AuthController {

    @Autowired
    _AuthUserService authUserService;

    @Autowired
    _AuthLogServices authLogServices;

    @PostMapping(value = "/users")
    public ResponseEntity UserRegister (AuthDto AuthDto) {
        try {
            return new ResponseEntity(authUserService.addUser(AuthDto.getFullname(), AuthDto.getEmail(), AuthDto.getPassword(), AuthDto.getRepass()), HttpStatus.OK);
        } catch (AuthException err) {
            return new ResponseEntity(err.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (NullPointerException nulerr) {
            return new ResponseEntity(nulerr.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity getUserById (@PathVariable("id") int id) {
        return new ResponseEntity(authUserService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity getUserByWhat (@RequestParam(required = false) String email, @RequestParam(required = false) String name) {

        if (email == null && name == null){
            return new ResponseEntity(authUserService.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(authUserService.getByWhat(email, name), HttpStatus.OK);
        }

    }

    @PostMapping
    public ResponseEntity login (AuthDto AuthDto) {
        try {
            return new ResponseEntity(authUserService.login(AuthDto.getEmail(), AuthDto.getPassword()), HttpStatus.OK);

        } catch (AuthException err) {
            return new ResponseEntity(err.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/logs")
    public ResponseEntity getLogs (@RequestParam(required = false) String email, @RequestParam(required = false) String status) {

        if (email == null) {
            return new ResponseEntity(authLogServices.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(authLogServices.getByEmail(email, status), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/test")
    public ResponseEntity TestApi() {
        return new ResponseEntity("Hello masuk", HttpStatus.OK);
    }
}
