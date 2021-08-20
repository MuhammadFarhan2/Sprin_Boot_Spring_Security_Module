package com.example.securityjwtrole.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.securityjwtrole.Model.Consumer;
import com.example.securityjwtrole.Model.Manager;
import com.example.securityjwtrole.Model.Role;
import com.example.securityjwtrole.Security.CustomAuthenticationFilter;
import com.example.securityjwtrole.Service.ConsumerService;
import com.example.securityjwtrole.Service.RoleService;
import com.example.securityjwtrole.Service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private ServiceManager serviceManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private Authentication authenticate;
    private String access_Token;

    @PostMapping("/saveConsumer")
    public Consumer saveRole(@RequestBody Consumer consumer) {
        consumer.setPassword(passwordEncoder.encode(consumer.getPassword()));
        return consumerService.saveConsumer(consumer);
    }

    @GetMapping("/getAllConsumer")
    public List<Consumer> getAllConsumer() {
        return consumerService.getAllConsumer();
    }

    @GetMapping("/getConsumerById/{id}")
    public Consumer getConsumerById(@PathVariable Integer id) {
        return consumerService.getConsumerById(id);
    }

    //    ...........................ROLE MODEL..................................
    @PostMapping("/saveRole")
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @GetMapping("/getAllRole")
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    @GetMapping("/getRoleById/{id}")
    public Role getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }


//    ..................................Manager API.......................................................

    @PostMapping("/saveManager")
    public Manager saveRole(@RequestBody Manager manager) {
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        return serviceManager.saveManager(manager);
    }

    @GetMapping("/getAllManager")
    public List<Manager> getAllManager() {
        return serviceManager.getAllManager();
    }

    @GetMapping("/getManagerById/{id}")
    public Manager getManagerById(@PathVariable Integer id) {
        return serviceManager.getManagerById(id);
    }


    //    ................................login API.........................................
    @PostMapping("/loginConsumer")
    public String login(@RequestBody Consumer consumer) {
        try{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(consumer.getUserName(),consumer.getPassword());
            System.out.println("Checking auth: "+authenticationToken.isAuthenticated());
            System.out.println("attempt: "+authenticationToken.toString());
            authenticate = authenticationManager.authenticate(authenticationToken);
            System.out.println("Authenticated: "+authenticate);
        }
        catch (Exception e){
           e = new Exception("UserName/Password is incorrect");
            return e.getMessage();
        }

        User user = (User)authenticate.getPrincipal();
        System.out.println("I am: "+user);
        Algorithm algorithm = Algorithm.HMAC256("secrete".getBytes());//this is going to be sign the JSON web token
        access_Token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
                .withClaim("role",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return access_Token;
    }

    @PostMapping("/loginManager")
    public String login(@RequestBody Manager manager) {
        try{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(manager.getUserName(),manager.getPassword());
            System.out.println("Checking auth: "+authenticationToken.isAuthenticated());
            System.out.println("attempt: "+authenticationToken.toString());
            authenticate = authenticationManager.authenticate(authenticationToken);
            System.out.println("Authenticated: "+authenticate);
        }
        catch (Exception e){
           e = new Exception("UserName/Password is incorrect is in Manager");
            return e.getMessage();
        }

        User user = (User)authenticate.getPrincipal();
        System.out.println("I am: "+user);
        Algorithm algorithm = Algorithm.HMAC256("secrete".getBytes());//this is going to be sign the JSON web token
        access_Token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
                .withClaim("role",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return access_Token;
    }
}