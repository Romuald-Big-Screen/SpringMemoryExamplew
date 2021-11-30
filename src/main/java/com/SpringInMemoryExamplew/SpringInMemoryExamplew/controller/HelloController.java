package com.SpringInMemoryExamplew.SpringInMemoryExamplew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HelloController {

    @Autowired
    public InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/employee")
    public String welcomeEmployee()
    {
        return "Welcome Employee";
    }

    @GetMapping("/manager")
    public String welcomeManager()
    {
        return "Welcome Manager";
    }

    @GetMapping("/employee/{username}")
    public String checkIfUserExists(@PathVariable("username") String username)
    {
        boolean flag = inMemoryUserDetailsManager.userExists(username);
        if (flag)
            return "\""+username + "\" exist in InMemoryUserDetailsManager";
        else
            return "\""+username + "\" does not exist in InMemoryUserDetailsManager";
    }

    @GetMapping("/employee/create/{username}/{password}/{role}")
    public String createUser(@PathVariable("username") String username, @PathVariable("password") String password,
                             @PathVariable("role") String role)
    {

        ArrayList<GrantedAuthority> grantedAuthoritiesList= new ArrayList<>();
        grantedAuthoritiesList.add(new SimpleGrantedAuthority(role));

        inMemoryUserDetailsManager.createUser(new User(username, passwordEncoder.encode(password), grantedAuthoritiesList));

        return checkIfUserExists(username);
    }

    @GetMapping("/employee/update/{username}/{password}/{role}")
    public String updateUser(@PathVariable("username") String username, @PathVariable("password") String password,
                             @PathVariable("role") String role)
    {
        ArrayList<GrantedAuthority> grantedAuthoritiesList= new ArrayList<>();
        grantedAuthoritiesList.add(new SimpleGrantedAuthority(role));

        inMemoryUserDetailsManager.updateUser(new User(username, passwordEncoder.encode(password), grantedAuthoritiesList));

        return checkIfUserExists(username);
    }

    @GetMapping("/employee/delete/{username}")
    public String deleteUser(@PathVariable("username") String username)
    {

        inMemoryUserDetailsManager.deleteUser(username);

        return checkIfUserExists(username);
    }

}
