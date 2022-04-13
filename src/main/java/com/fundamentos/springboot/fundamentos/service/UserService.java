package com.fundamentos.springboot.fundamentos.service;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTransactional(List<User> users) {
        users.stream()
                .peek(user -> LOG.info("Usuario insertado: " + user))
                .forEach(userRepository::save);
        //      .forEach(user -> userRepository.save(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(Long id, User user) {
        return userRepository.findById(id)
                .map(
                        u -> {
                            u.setEmail(user.getEmail());
                            u.setName(user.getName());
                            u.setBirthdate(user.getBirthdate());
                            return userRepository.save(u);
                        }
                ).get();
    }
}
