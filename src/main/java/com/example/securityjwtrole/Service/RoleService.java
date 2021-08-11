package com.example.securityjwtrole.Service;

import com.example.securityjwtrole.Model.Consumer;
import com.example.securityjwtrole.Model.Role;
import com.example.securityjwtrole.Repository.ConsumerRepository;
import com.example.securityjwtrole.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Role saveRole(Role role)
    {
        return roleRepository.save(role);
    }
    public List<Role> getAllRole(){
        return  roleRepository.findAll();
    }
    public Role getRoleById(Integer id){
        return roleRepository.getById(id);
    }

}
