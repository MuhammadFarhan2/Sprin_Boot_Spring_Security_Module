package com.example.securityjwtrole.Controller;

import com.example.securityjwtrole.Model.Consumer;
import com.example.securityjwtrole.Model.Role;
import com.example.securityjwtrole.Service.ConsumerService;
import com.example.securityjwtrole.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class Controller {
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/saveConsumer")
    public Consumer saveRole(@RequestBody Consumer consumer){
        consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
        return consumerService.saveConsumer(consumer);
    }
    
    @GetMapping("/getAllConsumer")
    public List<Consumer> getAllConsumer(){
        return consumerService.getAllConsumer();
    }
    @GetMapping("/getConsumerById/{id}")
    public Consumer getConsumerById(@PathVariable Integer id){
        return consumerService.getConsumerById(id);
    }

//    ...........................ROLE MODEL..................................
    @PostMapping("/saveRole")
    public Role saveRole(@RequestBody Role role){
        return roleService.saveRole(role);
    }
    @GetMapping("/getAllRole")
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }
    @GetMapping("/getRoleById/{id}")
    public Role getRoleById(@PathVariable Integer id){
        return roleService.getRoleById(id);
    }
}