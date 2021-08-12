package com.example.securityjwtrole.Service;

import com.example.securityjwtrole.Model.Consumer;
import com.example.securityjwtrole.Model.Role;
import com.example.securityjwtrole.Repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ConsumerService implements UserDetailsService {
    @Autowired
    private ConsumerRepository  consumerRepository;
    public Consumer saveConsumer(Consumer consumer)
    {
        return consumerRepository.save(consumer);
    }
    public List<Consumer> getAllConsumer(){
        return  consumerRepository.findAll();
    }
    public Consumer getConsumerById(Integer id){
        return consumerRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Consumer user = consumerRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Not Exist!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
        return new User(user.getUserName(),user.getPassword(),authorities);
    }
}