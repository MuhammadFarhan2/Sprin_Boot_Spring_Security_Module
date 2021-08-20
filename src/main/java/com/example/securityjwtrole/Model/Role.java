package com.example.securityjwtrole.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;

    @JsonManagedReference("ConsumerRole")
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<Consumer> consumers;

    @JsonManagedReference("ManagerRole")
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<Manager> managers;

}
