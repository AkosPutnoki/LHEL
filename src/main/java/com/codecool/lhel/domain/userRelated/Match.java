package com.codecool.lhel.domain.userRelated;

import com.codecool.lhel.domain.enums.ResultType;
import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.util.GameSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`match`")
@Proxy(lazy=false)
public class Match implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToMany
    @JsonManagedReference
    private List<UserEntity> users = new ArrayList<>();

    private ResultType result;
    private byte[] game;

    public Match(UserEntity firstUser, UserEntity secondUser) {
        users.add(firstUser);
        users.add(secondUser);
        firstUser.getMatches().add(this);
        secondUser.getMatches().add(this);
        this.result = ResultType.PENDING;

        createGame(firstUser, secondUser);
    }

    private void createGame(UserEntity firstUser, UserEntity secondUser) {
        try {
            this.game = GameSerializer.serialize(new Game(firstUser, secondUser, id));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Game getDeserializedGame(){
        try {
            return GameSerializer.deSerialize(game);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setGame(byte[] game) {
        this.game = game;
    }
}
