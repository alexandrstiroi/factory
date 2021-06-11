package org.shtiroy.factory.service;

import org.shtiroy.factory.entity.User;
import org.shtiroy.factory.repository.RoleRepository;
import org.shtiroy.factory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Нет такого пользователя");
        }

        return user;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user){
        User userFromDB = userRepository.findByUserName(user.getUserName());

        if (userFromDB != null) {
            return false;
        }

        user.setUserRole(roleRepository.getOne(Long.valueOf(1)));
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return true;
    }
}
