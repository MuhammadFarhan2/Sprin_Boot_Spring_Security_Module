package com.example.securityjwtrole.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.securityjwtrole.Model.Student;
import com.example.securityjwtrole.Model.Teacher;
import com.example.securityjwtrole.Model.Role;
import com.example.securityjwtrole.Service.StudentService;
import com.example.securityjwtrole.Service.RoleService;
import com.example.securityjwtrole.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private Authentication authenticate;
    private String access_Token;

    @PostMapping("/saveStudent")
    public Student saveStudent(@RequestBody Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentService.saveStudent(student);
    }

    @GetMapping("/getAllStudent")
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @GetMapping("/getStudentById/{id}")
    public Student getConsumerById(@PathVariable Integer id) {
        return studentService.getStudentById(id);
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


    //    ..................................Teacher API.......................................................
    @PostMapping("/saveTeacher")
    public Teacher saveTeacher(@RequestBody Teacher teacher) {
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        return teacherService.saveTeacher(teacher);
    }

    @GetMapping("/getAllTeacher")
    public List<Teacher> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @GetMapping("/getTeacherById/{id}")
    public Teacher getManagerById(@PathVariable Integer id) {
        return teacherService.getTeacherById(id);
    }


    //    ................................login API.........................................
    @PostMapping("/loginStudent")
    public String login(@RequestBody Student student) {
        try {

        UserDetails userDetails = studentService.loadUserByUsername(student.getUserName());

        if (userDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(student.getUserName(), student.getPassword());
                authenticate = authenticationManager.authenticate(authenticationToken);
            User user = (User) authenticate.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256("secrete".getBytes());//this is going to be sign the JSON web token
            access_Token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            return access_Token;
        } else {
            return "User Not Found!";
        }
        } catch (Exception e) {
            e = new Exception("UserName/Password is incorrect");
            return e.getMessage();
        }
    }

    @PostMapping("/loginTeacher")
    public String login(@RequestBody Teacher teacher) {
        try {
            UserDetails userDetails = teacherService.loadUserByUsername(teacher.getUserName());
            if (userDetails != null) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(teacher.getUserName(), teacher.getPassword());
                authenticate = authenticationManager.authenticate(authenticationToken);
                User user = (User) authenticate.getPrincipal();
                Algorithm algorithm = Algorithm.HMAC256("secrete".getBytes());//this is going to be sign the JSON web token
                access_Token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                return access_Token;
            }
            else {
                return "UserName/Password is incorrect is in Teacher";
            }
        } catch (Exception e) {
            e = new Exception("UserName/Password is incorrect is in Teacher");
            return e.getMessage();
        }
    }
}