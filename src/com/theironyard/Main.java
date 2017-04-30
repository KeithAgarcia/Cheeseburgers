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

        Spark.post(
                "/create-cheeseburger",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("customerName");
                    Customer customer = customers.get(name);
                    String mayo = request.queryParams("mayo");
                    int patties = Integer.valueOf(request.queryParams("patties"));
                    int buns = Integer.valueOf(request.queryParams("buns"));
                    int bacon = Integer.valueOf(request.queryParams("bacon"));
                    String instructions = request.queryParams("instructions");

                    CheeseBurger cheeseBurger = new CheeseBurger(mayo, patties, buns, bacon, instructions);

                    customer.cheeseBurgers.add(cheeseBurger);
                    response.redirect("/");
                    return "";
                }
        );

        Spark.post(
                "/logout",
                ((request, response) ->{
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return  "";
                })
        );
    }
}

