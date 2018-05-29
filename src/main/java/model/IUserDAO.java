package model;

import java.util.Optional;

public interface IUserDAO {
    Optional<User> findByLogin(String login);

    boolean updatePassword(String login, String password);

    boolean updateEmail(String login, String email);

    boolean updatePhoneNo(String login, String password);

    boolean save(User user);

    boolean containsLogin(String login);

    boolean checkPassword(String login, String password);
}
