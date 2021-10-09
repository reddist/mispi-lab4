package reddist.rest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reddist.Lab4Application;
import reddist.database.UserEntity;
import reddist.model.LoginResult;
import reddist.model.RegistrationResult;
import reddist.database.service.UserService;

import org.slf4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(Lab4Application.class);

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/web4/register", method = RequestMethod.POST)
    public RegistrationResult register(@Valid @RequestBody UserEntity requestBody){
//        Gson gson = new Gson();
//        logger.debug("Registration attempt with " + requestBody);
//        logger.warn("Registration attempt with");
//        UserEntity newUser = new UserEntity("", "", "");
//        try {
//            newUser = gson.fromJson(requestBody, UserEntity.class);
//        } catch (Exception e) {
//            return new RegistrationResult("Неверный формат запроса регистрации.", "fail");
//        }
        return userService.register(requestBody);
    }

    @CrossOrigin
    @RequestMapping(value = "/web4/login")
    public LoginResult login(@Valid @RequestBody UserEntity requestBody, HttpServletResponse response) {
        Cookie loginCookie = new Cookie("login", requestBody.getLogin());
        Cookie passwordCookie = new Cookie("password", requestBody.getPassword());
        loginCookie.setPath("/");
        passwordCookie.setPath("/");
        response.addCookie(loginCookie);
        response.addCookie(passwordCookie);
//        Gson gson = new Gson();
//        String requestBody = "{\"login\":\"fuckAngular\"," +
//                              "\"password\":\"bullshit\"," +
//                              "\"email\":\"fuckAngular\"}";
//        logger.debug("Login attempt with " + requestBody);
//        UserEntity tryLoginUser = new UserEntity("", "", "");
//        try {
//            tryLoginUser = gson.fromJson(requestBody, UserEntity.class);
//        } catch (Exception e) {
//            return new LoginResult("", "Неверный формат запроса авторизации.");
//        }
//        return userService.login(tryLoginUser);
        return userService.login(requestBody);
    }
}
