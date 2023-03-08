package com.cm.my_money_be.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkIfUserExist(String email){
        return userRepository.findByEmail(email) != null;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(username);

         //return user;
        return new org.springframework.security.core.userdetails.User("asd","asd",new ArrayList<>());
    }
}
