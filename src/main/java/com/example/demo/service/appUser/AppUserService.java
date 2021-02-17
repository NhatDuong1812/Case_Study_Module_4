package com.example.demo.service.appUser;

import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
//import org.omg.IOP.ServiceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements IAppUserService, UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;
    @Override
    public AppUser getUserByName(String name) {
        return userRepository.findAppUserByUsername(name);
    }

    @Override
    public AppUser getCurrentUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        AppUser user= userRepository.findAppUserByUsername(userName);
        return user;

    }

    @Override
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user= this.getUserByName(username);
        List<GrantedAuthority> authorities= new ArrayList<>();
        UserDetails userDetails = null;
        authorities.add(user.getRole());
        if(user != null) {
             userDetails = new User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        }
        return userDetails;
    }

}
