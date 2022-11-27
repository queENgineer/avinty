package com.avinty.hr.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.Tuple;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    public static final  Map<String, Pair<String,String[]>> users=new HashMap<>();

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void  init(){
    
        users.put("admin", new Pair<String, String[]>() {
        
        
            @Override
            public String[] setValue(String[] value) {
                return null;
            }
        
            @Override
            public String getLeft() {
                return passwordEncoder.encode("123");
            }
        
            @Override
            public String[] getRight() {
                String[] roles=new  String[2] ;
                roles[0]="ADMIN";
                roles[1]="USER";
                return roles;
            }
        
        
        });
        
        users.put("user", new Pair<String, String[]>() {
        
        
            @Override
            public String[] setValue(String[] value) {
                return null;
            }
        
            @Override
            public String getLeft() {
                return passwordEncoder.encode("456");
            }
        
            @Override
            public String[] getRight() {
                String[] roles=new  String[1] ;
                roles[0]="USER";
                return roles;
            }
        
        
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(users.containsKey(username)){
            return User.withUsername(username).roles(users.get(username).getRight()).password(users.get(username).getLeft()).build();
        }
        throw new UsernameNotFoundException(username);
    }
    
}
