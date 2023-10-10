package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api/admin")
public class RestController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public RestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/show")
    public ResponseEntity<User> showInfoUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUsername(principal.getName()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.showUserById(id).getRoles(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.showUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User newUser) {
        userService.saveUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User userFromWebPage, @PathVariable("id") int id) {
        userService.updateUserById(id, userFromWebPage);
        return new ResponseEntity<>(userFromWebPage, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
