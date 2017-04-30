package com.theironyard;

import java.util.ArrayList;

/**
 * Created by Keith on 4/29/17.
 */
public class Customer {
    String name;
    ArrayList<CheeseBurger> cheeseBurgers = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }
}
