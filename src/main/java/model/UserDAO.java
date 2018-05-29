package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IUserDAO {

    //replaces DB
    private List<User> userList;

    public UserDAO() {
        userList = new ArrayList<>();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userList.stream().filter(u -> u.getLogin().equals(login)).findFirst();
    }

    @Override
    public boolean updatePassword(String login, String password) {
        Optional<User> user = findByLogin(login);
        if (user.isPresent()) {
            user.get().setPassword(password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateEmail(String login, String email) {
        Optional<User> user = findByLogin(login);
        if (user.isPresent()) {
            user.get().setEmail(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePhoneNo(String login, String phoneNumber) {
        Optional<User> user = findByLogin(login);
        if (user.isPresent()) {
            user.get().setPhoneNumber(phoneNumber);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean save(User user) {
        if(user == null)
            return false;

        if (containsLogin(user.getLogin())) {
            return false;
        } else {
            userList.add(user);
            return true;
        }
    }

    @Override
    public boolean containsLogin(String login) {
        return userList.stream().anyMatch(u -> u.getLogin().equals(login));
    }

    @Override
    public boolean checkPassword(String login, String password) {
        return userList.stream().anyMatch(u -> u.getLogin().equals(login) && u.getPassword().equals(password));
    }


}
