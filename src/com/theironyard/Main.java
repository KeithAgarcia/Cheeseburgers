package com.theironyard;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import spark.Spark;

import java.util.ArrayList;

public class Main {
    static ArrayList<CheeseBurger> cheeseBurgers = new ArrayList<>(); //storing our cheeseburgers in a public static arraylist.

    public static void main(String[] args) {
        Spark.init();

        cheeseBurgers.add(new CheeseBurger("yes", 2, 3, 4, "medium-rare"));
        cheeseBurgers.add(new CheeseBurger("no", 1, 2, 3, "put a little ketchup"));
        cheeseBurgers.add(new CheeseBurger("no", 3, 2, 4, "rare"));
        cheeseBurgers.add(new CheeseBurger("yes", 4, 0, 0, "medium-rare"));

        Spark.get(
                "/cheeseburgers",
                ((request, response) -> {
                    JsonSerializer serializer = new JsonSerializer();
                    String json = serializer.serialize(cheeseBurgers);
                    return json;
                })
        );
        Spark.post(
                "/cheeseburgers",
                ((request, response) -> {
                    String json = request.body();
                    JsonParser parser = new JsonParser();
                    CheeseBurger cheeseBurger = parser.parse(json, CheeseBurger.class);
                    cheeseBurgers.add(cheeseBurger);
                    return "";
                })
        );
    }
}

