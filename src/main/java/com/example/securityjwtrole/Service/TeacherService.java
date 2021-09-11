package com.example.securityjwtrole.Service;

import com.example.securityjwtrole.Model.Teacher;
import com.example.securityjwtrole.Repository.TeacherRepository;
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
//@Qu
// alifier
@Service
public class TeacherService implements UserDetailsService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Integer id) {
        return teacherRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername( String s) throws UsernameNotFoundException {
        Teacher teacherUser = teacherRepository.findByUserName(s);
        if (teacherUser != null)
        {
            Collection<SimpleGrantedAuthority> authoritiesManager = new ArrayList<>();
            authoritiesManager.add(new SimpleGrantedAuthority(teacherUser.getRole().getRole()));
            return new User(teacherUser.getUserName(), teacherUser.getPassword(),authoritiesManager);
        }
        else
        throw  new UsernameNotFoundException("Not Exist!");
    }
}
