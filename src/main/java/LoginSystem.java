import model.IUserDAO;
import model.User;
import model.UserDAO;
import utils.RandomPasswordGenerator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;

public class LoginSystem {
    private String currentUser;
    private Validator validator;
    private BufferedReader br;
    private IUserDAO userDAO;
    private RandomPasswordGenerator randomPasswordGenerator;

    public LoginSystem() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.userDAO = new UserDAO();
        this.randomPasswordGenerator = new RandomPasswordGenerator();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("1 - log in \n2 - sign up \n3 - exit \n");
                String userChoice = br.readLine();
                if ("1".equals(userChoice)) {
                    logIn();
                } else if ("2".equals(userChoice)) {
                    signUp();
                } else if ("3".equals(userChoice)) {
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void signUp() throws IOException {
        Optional<User> userOptional = getUser();
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return;
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.size() > 0) {
            System.out.println("Sign up failed");
            violations.forEach(v -> System.out.println(v.getMessage()));
        } else if (userDAO.save(user)) {
            System.out.println("Sign up successful");
            currentUser = user.getLogin();
            loggedUser();
        } else {
            System.out.println("Sign up failed. Cannot create such user.");
        }
    }

    private Optional<User> getUser() throws IOException {
        System.out.print("Login (min. 3 signs): ");
        String login = br.readLine();
        System.out.print("Password (min. 3 signs, 1 special sign, 1 capital letter, 1 digit, random password if empty): ");
        String password = br.readLine();
        if (password.equals("")) {
            password = randomPasswordGenerator.generateRandomPassword();
            System.out.println("Generated password: " + password);
        } else {
            System.out.print("Repeat password: ");
            String repeatedPassword = br.readLine();
            if (!repeatedPassword.equals(password)) {
                System.out.println("Provided passwords do not match");
                return Optional.empty();
            }
        }
        System.out.print("Email: ");
        String email = br.readLine();
        System.out.print("Phone number: ");
        String phoneNumber = br.readLine();
        return Optional.of(new User(login, password, email, phoneNumber));
    }

    private void logIn() throws IOException {
        System.out.print("Login: ");
        String login = br.readLine();
        System.out.print("Password: ");
        String password = br.readLine();
        if (userDAO.checkPassword(login, password)) {
            System.out.println("Login successful");
            currentUser = login;
            loggedUser();
        } else {
            System.out.println("Login failed");
        }
    }

    private void loggedUser() throws IOException {
        while (true) {
            System.out.println("1 - logout \n2 - change email \n3 - change phone number \n4 - show user data \n5 - exit \n");
            String userChoice = br.readLine();
            if ("1".equals(userChoice)) {
                return;
            } else if ("2".equals(userChoice)) {
                changeEmail();
            } else if ("3".equals(userChoice)) {
                changePhoneNo();
            } else if ("4".equals(userChoice)) {
                showUserData();
            } else if ("5".equals(userChoice)) {
                System.exit(0);
            }
        }
    }

    private void showUserData() {
        Optional<User> userOptional = userDAO.findByLogin(currentUser);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Login: " + user.getLogin());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone number: " + user.getPhoneNumber());
        } else {
            System.out.println("There is not such user in DB");
        }
    }

    private void changePhoneNo() throws IOException {
        System.out.print("Enter password: ");
        String password = br.readLine();
        System.out.print("Phone number: ");
        String phoneNumber = br.readLine();

        Set<ConstraintViolation<User>> violations = validator.validateValue(User.class, "phoneNumber", phoneNumber);
        if (violations.size() == 0 && userDAO.checkPassword(currentUser, password) && userDAO.updatePhoneNo(currentUser, phoneNumber)) {
            System.out.println("Phone number updated successfully");
        } else {
            System.out.println("Phone number update failed");
            violations.forEach(v -> System.out.println(v.getMessage()));
        }
    }

    private void changeEmail() throws IOException {
        System.out.print("Password: ");
        String password = br.readLine();
        System.out.print("Email: ");
        String email = br.readLine();

        Set<ConstraintViolation<User>> violations = validator.validateValue(User.class, "email", email);
        if (violations.size() == 0 && userDAO.checkPassword(currentUser, password) && userDAO.updateEmail(currentUser, email)) {
            System.out.println("Email updated successfully");
        } else {
            System.out.println("Email updated failed");
            violations.forEach(v -> System.out.println(v.getMessage()));
        }
    }
}
