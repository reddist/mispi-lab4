package reddist.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reddist.database.UserEntity;
import reddist.database.UserRepository;
import reddist.model.CryptoHash;
import reddist.model.LoginResult;
import reddist.model.RegistrationResult;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationResult register(UserEntity user){
        UserEntity userFromDatabaseForLogin = userRepository.findByLogin(user.getLogin());
        UserEntity userFromDatabaseForEmail = userRepository.findByEmail(user.getEmail());
        if(userFromDatabaseForLogin == null) {
            if(userFromDatabaseForEmail == null) {
                user.setPassword(CryptoHash.getHash(user.getPassword()));
                UserEntity registeredUser = userRepository.save(user);
                return new RegistrationResult("Регистрация прошла успешно.", "success");
            } else {
                return new RegistrationResult("Пользователь с таким email уже существует.", "fail");
            }
        } else {
            return new RegistrationResult("Пользователь с таким логином уже существует.", "fail");
        }
    }

     public LoginResult login(UserEntity user){
        UserEntity userFromDatabaseForLogin = userRepository.findByLogin(user.getLogin());
        UserEntity userFromDatabaseForEmail = userRepository.findByEmail(user.getEmail());
        if (userFromDatabaseForLogin == null) {
            if (userFromDatabaseForEmail == null) {
                return new LoginResult("", "Пользователя с таким логином/email не существует.");
            } else {
                user.setPassword(CryptoHash.getHash(user.getPassword()));
                if(userFromDatabaseForEmail.getPassword().equals(user.getPassword())) {
                    return new LoginResult("spring_server_reddist_web4", "Вход успешный.");
                } else {
                    return new LoginResult("", "Неверный пароль.");
                }
            }
        } else {
            user.setPassword(CryptoHash.getHash(user.getPassword()));
            if(userFromDatabaseForLogin.getPassword().equals(user.getPassword())) {
                return new LoginResult("spring_server_reddist_web4", "Вход успешный.");
            } else {
                return new LoginResult("", "Неверный пароль.");
            }
        }
    }
}
