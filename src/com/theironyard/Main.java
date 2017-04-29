package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap <String, Customer> customers = new HashMap<>(); // storing our customers in a hashmap.
    static ArrayList<CheeseBurger> cheeseBurgers = new ArrayList<>(); //storing our cheeseburgers in a public static arraylist.

    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/", //creating a default route
                ((request, response) -> {
                    HashMap m = new HashMap(); //creating hashmap for customer
                    Session session = request.session(); //creating session
                    String name = session.attribute("customerName");
                    Customer customer = customers.get(name);

                    if(customer == null){
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", customer.name);
                        return new ModelAndView(customer, "home.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/newCustomer",
                ((request, response) -> {
                    String name = request.queryParams("customer");
                    Customer customer = customers.get(name);
                    if(customer == null){
                        customer = new Customer(name);
                        customers.put(name, customer);
                    }

                    Session session = request.session();
                    session.attribute("customerName", name);

                    response.redirect("/");
                    return  "";
                })
        );
    }
}
