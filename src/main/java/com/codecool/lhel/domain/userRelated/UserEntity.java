package com.codecool.lhel.domain.userRelated;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Proxy;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`users`")
@Proxy(lazy=false)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Match> matches;

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
        matches = new ArrayList<>();
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
