package com.example.securityjwtrole.Service;

import com.example.securityjwtrole.Model.Manager;
import com.example.securityjwtrole.Repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Primary
//@Qu
// alifier
@Qualifier("manager")
@Service
public class ServiceManager implements UserDetailsService {

    @Autowired
    private ManagerRepository managerRepository;

    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManager() {
        return managerRepository.findAll();
    }

    public Manager getManagerById(Integer id) {
        return managerRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(@Qualifier("manager") String s) throws UsernameNotFoundException {
        Manager managerUser = managerRepository.findByUserName(s);
        if (managerUser != null)
        {
            Collection<SimpleGrantedAuthority> authoritiesManager = new ArrayList<>();
            authoritiesManager.add(new SimpleGrantedAuthority(managerUser.getRole().getRole()));
            System.out.println("Authority"+authoritiesManager.toString());
            System.out.println("H");
            return new User(managerUser.getUserName(),managerUser.getPassword(),authoritiesManager);
        }
        else
        throw  new UsernameNotFoundException("Not Exist!");
    }
}
