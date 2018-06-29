package com.codecool.lhel.domain.userRelated;

import com.codecool.lhel.domain.enums.ResultType;
import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.util.GameSerializer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`match`")
@Proxy(lazy=false)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToMany
    private List<UserEntity> users = new ArrayList<>();

    private ResultType result;
    private byte[] game;

    public Match(UserEntity firstUser, UserEntity secondUser, byte[] game) {
        users.add(firstUser);
        users.add(secondUser);
        firstUser.getMatches().add(this);
        secondUser.getMatches().add(this);
        this.game = game;
        this.result = ResultType.PENDING;
    }

    public Match() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public byte[] getGame() {
        return game;
    }

    public Game getDeserializedGame() throws IOException {
        return GameSerializer.deSerialize(game);
    }

    public void setGame(byte[] game) {
        this.game = game;
    }
}
