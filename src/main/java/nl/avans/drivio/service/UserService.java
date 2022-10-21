package nl.avans.drivio.service;


import nl.avans.drivio.model.User;
import nl.avans.drivio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("No user found with given id["+ userId +"]");
        }
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public User updateUser(User user, int userId) {

        // check whether a user with given id exists in the DB or not
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("No user found with given id["+ userId +"]"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setCity(user.getCity());
        existingUser.setPhone(user.getPhone());
        existingUser.setEmail(user.getEmail());
        existingUser.setPasswords(user.getPasswords());

        // save existing user to the Database
        userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUser(int userId) {

        // check whether a user exists in the database. if not, throw exception.
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user found with given id["+ userId +"]"));
        userRepository.deleteById(userId);
    }
}
