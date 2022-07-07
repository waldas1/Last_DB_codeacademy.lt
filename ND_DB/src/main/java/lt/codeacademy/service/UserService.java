package lt.codeacademy.service;

import lt.codeacademy.entity.User;
import lt.codeacademy.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }

    public void createNewUser(User user) {
        repository.createUser(user);
    }

    public List<User> getUsers() {
        return repository.getUsers();
    }

    public void deleteUserById(User user) {
        repository.deleteUser(user);
    }
}
