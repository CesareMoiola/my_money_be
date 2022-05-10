package com.cm.my_money_be.services;

import com.cm.my_money_be.data.User;
import com.cm.my_money_be.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void signUp(User user) throws Exception {

        //Let's check if user already registered with us
        if(checkIfUserExist(user.getEmail())){
            throw new Exception("User already exists for this email");
        }
        User newUser = new User();
        BeanUtils.copyProperties(user,newUser);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
    }

    public boolean checkIfUserExist(String email){
        return userRepository.findByEmail(email).size() > 0;
    }

    //Return all users
    public List<User> list() {
        return userRepository.findAll();
    }

    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }
}
