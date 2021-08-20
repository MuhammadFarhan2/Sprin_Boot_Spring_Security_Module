package com.example.securityjwtrole.Repository;

import com.example.securityjwtrole.Model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer,Integer> {
    Consumer findByUserName(String userName);
//    Consumer findByUserNameAndPassword(Consumer consumer);
}
