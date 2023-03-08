package com.cm.my_money_be.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersDAO {

    @Autowired
    UserRepository userRepository;

    public User getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null) throw new Exception("User with email: " + email + " was not found");

        return user;
    }

    public List<User> getUsers() throws Exception{
        return userRepository.findAll();
    }

    //Get all accounts with balance updated as of the date indicated
    public List<String> getAllEmail() throws Exception {
        return userRepository.findAllEmail();
    }

    //Get all users id
    public List<Long> getAllIds() throws Exception{
        return userRepository.findAllId();
    }

    //Get user id
    public Optional<User> getUser(Long id) throws Exception{
        return userRepository.findById(id);
    }

    public void saveUser(User user) throws Exception{
        userRepository.save(user);
    }
}
