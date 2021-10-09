package reddist.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reddist.model.HitBase;
import reddist.database.service.PointService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @CrossOrigin
    @PostMapping(value = "/web4/api/save")
    public List<HitBase> saveHit(@Valid @RequestBody HitBase hit,
                                 @CookieValue(name = "login") String login,
                                 @CookieValue(name = "password") String password){
        return pointService.saveHit(hit, login, password);
    }

    @CrossOrigin
    @GetMapping(value = "/web4/api/get")
    public List<HitBase> getHits(@CookieValue(name = "login") String login,
                                 @CookieValue(name = "password") String password){
        return pointService.getHitsForController(login, password);
    }

    @CrossOrigin
    @DeleteMapping(value = "web4/api/delete")
    public void deleteHits(@CookieValue(name = "login") String login,
                           @CookieValue(name = "password") String password) {
        pointService.deleteHitsForController(login, password);
    }
}
