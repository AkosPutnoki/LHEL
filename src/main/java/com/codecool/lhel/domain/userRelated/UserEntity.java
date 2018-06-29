package com.codecool.lhel.domain.userRelated;

import org.hibernate.annotations.Proxy;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity")
@Proxy(lazy=false)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<Match> matches = new ArrayList<>();

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public UserEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public static void hashPw(UserEntity user) {
        user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
    }

    public static boolean checkPw(UserEntity actualUser, UserEntity promisedUser){
        return BCrypt.checkpw(promisedUser.password, actualUser.password);
    }

}
