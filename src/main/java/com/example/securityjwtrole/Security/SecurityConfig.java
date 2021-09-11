package com.example.securityjwtrole.Security;

import com.example.securityjwtrole.Service.StudentService;
import com.example.securityjwtrole.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure( AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("I");
        auth.userDetailsService(studentService).passwordEncoder(bCryptPasswordEncoder);
        auth.userDetailsService(teacherService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("J");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/loginConsumer","/loginManager","/saveManager","/saveConsumer").permitAll();
        http.authorizeRequests().antMatchers("/getConsumerById/{id}","/getAllConsumer").hasAnyAuthority("consumer","admin","manager");
        http.authorizeRequests().antMatchers("/saveRole").hasAnyAuthority("admin");
        http.authorizeRequests().antMatchers("/getAllRole","/getRoleById/{id}").hasAnyAuthority("manager","admin");
        System.out.println("K");
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        System.out.println("L");
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        System.out.println("M");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        System.out.println("N");
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        System.out.println("O");
        return new BCryptPasswordEncoder();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        System.out.println("P");
        return new BCryptPasswordEncoder();
    }
}