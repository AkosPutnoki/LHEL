package com.codecool.lhel.util;


import com.codecool.lhel.domain.game.Game;

import java.io.*;

public class GameSerializer {

    public static byte[] serialize(Game game) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(game);
        byte[] result = baos.toByteArray();
        baos.close();
        oos.close();
        return result;
    }

    public static Game deSerialize(byte[] source) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(source);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Game game = null;
        try {
            game = (Game) ois.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Game class not found");
        }
        bais.close();
        ois.close();
        return game;
    }

}
