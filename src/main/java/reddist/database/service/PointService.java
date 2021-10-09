package reddist.database.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddist.Lab4Application;
import reddist.database.HitsEntity;
import reddist.database.PointRepository;
import reddist.database.UserEntity;
import reddist.database.UserRepository;
import reddist.mbeans.ClickIntervalMBean;
import reddist.mbeans.PointCounterMBean;
import reddist.model.HitBase;
import reddist.model.HitResult;
import reddist.model.LoginResult;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PointService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PointRepository pointRepository;
    private PointCounterMBean pointCounterMBean;
    private ClickIntervalMBean clickIntervalMBean;

    @Autowired
    public PointService(UserRepository userRepository, PointRepository pointRepository, UserService userService, PointCounterMBean pointCounterMBean, ClickIntervalMBean clickIntervalMBean) {
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
        this.userService = userService;
        this.pointCounterMBean = pointCounterMBean;
        this.clickIntervalMBean = clickIntervalMBean;
    }

    public List<HitBase> saveHit(HitBase hit, String login, String password){
        UserEntity user = findUserByEmailOrLogin(login);
        if (user == null)
            return new ArrayList<HitBase>();
        user.setPassword(password);
        LoginResult loginResult = userService.login(user);
        if ("spring_server_reddist_web4".equals(loginResult.getToken())) {
            if ("click".equals(hit.getType())) clickIntervalMBean.click();
            HitsEntity newHit = new HitsEntity();
            HitResult hitResult = new HitResult(hit);
            if(hitResult.isValid()) {
                newHit.setX(hitResult.getX());
                newHit.setY(hitResult.getY());
                newHit.setR(hitResult.getR());
                newHit.setType(hit.getType());
                newHit.setResult(hitResult.getResult());
                newHit.setOwner(user);
                pointRepository.save(newHit);
            }
            pointCounterMBean.count("Вы не попали".equals(hitResult.getResult()));
            return getHitsByLogin(login);
        } else {
            return new ArrayList<HitBase>();
        }
    }

    private List<HitBase> getHitsByLogin(String login) {
        UserEntity user = findUserByEmailOrLogin(login);
        if (user == null)
            return new ArrayList<HitBase>();
        List<HitsEntity> hitsFromDatabase = pointRepository.findAllByOwner(user);
        List<HitBase> responseHits = new ArrayList<HitBase>();
        hitsFromDatabase.forEach((hitsEntity) -> {
            responseHits.add(new HitBase(String.valueOf(hitsEntity.getX()),
                    String.valueOf(hitsEntity.getY()),
                    String.valueOf(hitsEntity.getR()),
                    String.valueOf(hitsEntity.getType()),
                    String.valueOf(hitsEntity.getResult()))
            );
        });
        Collections.reverse(responseHits);
        return responseHits;
    }

    public List<HitBase> getHitsForController(String login, String password) {
        UserEntity user = findUserByEmailOrLogin(login);
        if (user == null)
            return new ArrayList<HitBase>();
        user.setPassword(password);
        LoginResult loginResult = userService.login(user);
        if ("spring_server_reddist_web4".equals(loginResult.getToken())) {
           return getHitsByLogin(login);
        }  else {
            return new ArrayList<HitBase>();
        }
    }

    private UserEntity findUserByEmailOrLogin(String login){
        UserEntity user = userRepository.findByLogin(login);
        if (user == null)
            user = userRepository.findByEmail(login);
        return user;
    }

    @Transactional
    public void deleteHitsForController(String login, String password) {
        UserEntity user = findUserByEmailOrLogin(login);
        if (user == null)
            return;
        user.setPassword(password);
        LoginResult loginResult = userService.login(user);
        if ("spring_server_reddist_web4".equals(loginResult.getToken())) {
            pointRepository.deleteAllByOwner(user);
            pointCounterMBean.clear();
            clickIntervalMBean.clear();
        }
    }
}
