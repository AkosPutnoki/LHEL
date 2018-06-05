package com.codecool.lhel.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameService {

    private static final String baseURL = "https://poker-handchecker-lhel.herokuapp.com/api/";

    public static int getWinnerBasedOnHands(List<Card> firstHand, List<Card> secondHand) throws IOException {
        Map<String, List<String>> handMap = new HashMap<String, List<String>>(){{

            put("firstHand", firstHand.stream().map(Card::toString).collect(Collectors.toList()));
            put("secondHand", secondHand.stream().map(Card::toString).collect(Collectors.toList()));

        }};

        String handCheckerUrl = baseURL + "get-winner-hand";
        StringEntity entity = new StringEntity(new ObjectMapper().writeValueAsString(handMap),
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(handCheckerUrl);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);

        String json = EntityUtils.toString(response.getEntity());

        Winner winner = new Gson().fromJson(json, Winner.class);

        return winner.getWinner();
    }

    private class Winner{
        int winner;

        int getWinner() {
            return winner;
        }

        void setWinner(int winner) {
            this.winner = winner;
        }
    }
}
