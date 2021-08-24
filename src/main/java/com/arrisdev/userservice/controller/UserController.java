package com.arrisdev.userservice.controller;

import com.arrisdev.userservice.model.Role;
import com.arrisdev.userservice.model.User;
import com.arrisdev.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/service/services")
    public ResponseEntity<?> getServices(){
        return new ResponseEntity<>(discoveryClient.getServices(), HttpStatus.OK);
    }

    @Autowired
    UserService userService;

    @PostMapping("/service/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        if (userService.findByUsername(user.getUsername()) != null){
            //status code:400
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //default role = user
        user.setRoles(Role.USER);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/service/login")
    public ResponseEntity<?> getUser(Principal principal){
        if (principal == null || principal.getName() == null){
            //this means : logout will be successful. login?logout
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //username = principal.getName()
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @PostMapping("/service/names")
    public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList){
        return ResponseEntity.ok(userService.findByUsers(idList));
    }

    @GetMapping("/service/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("It is working...");
    }
}
