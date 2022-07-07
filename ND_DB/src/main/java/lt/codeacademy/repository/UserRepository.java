package lt.codeacademy.repository;

import lt.codeacademy.entity.User;

import java.util.List;

public class UserRepository extends AbstractRepository {

    public void createUser(User user) {
        modifyEntity(session -> session.persist(user));
    }

    public List<User> getUsers() {
        return getValue(session -> session.createQuery("From User", User.class).list());
    }

    public void deleteUser(User user){
        modifyEntity(session -> session.delete(user));
    }
}
