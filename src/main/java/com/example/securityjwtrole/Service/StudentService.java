package com.example.securityjwtrole.Service;

import com.example.securityjwtrole.Model.Student;
import com.example.securityjwtrole.Repository.StudentRepository;
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

//@Primary

@Service
public class StudentService implements UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;
    public Student saveStudent(Student student)
    {
        return studentRepository.save(student);
    }
    public List<Student> getAllStudent(){
        return  studentRepository.findAll();
    }
    public Student getStudentById(Integer id){
        return studentRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("Load: "+userName);
        Student user = studentRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Not Exist!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
        System.out.println("Authority"+authorities.toString());
        System.out.println("H");
        return new User(user.getUserName(),user.getPassword(),authorities);
    }
}