package com.example.securityjwtrole.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//Here we'll be doing validating token and then allow that token to access resource that is applicable
public class CustomAuthorizationFilter extends OncePerRequestFilter {
//    So all logic we'll put here to filter the request comming in
//    OncePerRequestFilter  will intercept every request that come into apps
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/loginConsumer")) {
            System.out.println("What happen in Consumer!");
            try {
                    System.out.println("A:"+ request);
                    filterChain.doFilter(request,response);
                System.out.println("End of do filter: "+ response);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("D");

        }
        else if (request.getServletPath().equals("/loginManager")) {
            System.out.println("What happen in Manager!");
            try {
                    System.out.println("A:"+ request);
                    filterChain.doFilter(request,response);
                System.out.println("End of do filter: "+ response);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("D");

        }
        else
        {
            System.out.println("E");
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    System.out.println("F");

                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secrete".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("role").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role-> {authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(username,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }
                catch (Exception invalid_token){
                    invalid_token = new Exception("Invalid Token");
                     response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    Map<String,String> error = new HashMap<>();
                    error.put("Error : ",invalid_token.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);
                }
            }else {
                System.out.println("G");

                filterChain.doFilter(request,response);
            }
        }
    }
}