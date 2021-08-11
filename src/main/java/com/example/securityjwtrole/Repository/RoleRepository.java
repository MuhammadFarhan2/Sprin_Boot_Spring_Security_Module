package com.example.securityjwtrole.Repository;

import com.example.securityjwtrole.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
