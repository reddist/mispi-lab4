package reddist.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table (name = "USERS_LAB4_REDDIST")
@AllArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @Getter
    @Setter
    @Column(name = "login")
    private String login;

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    public UserEntity() {}

    /*@Getter
    @Setter
    @OneToMany(mappedBy = "user_owner")
    @JoinColumn(table = "HITS_LAB4_REDDIST", name = "user_owner")
    private Collection<HitsEntity> hitsEntities;*/
}
