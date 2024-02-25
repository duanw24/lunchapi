package com.wduan.lunchlinebackend;

import com.wduan.lunchlinebackend.helpers.dbHelper;
import com.wduan.lunchlinebackend.util.Order;
import lombok.Getter;

import java.util.HashMap;


public class OrderQueue {
    private static final int MAX_ORDERS = 100;
    @Getter
    private static HashMap<String, Order> orders = new HashMap<>();

    public static int size() {
        return orders.size();
    }

    public static void init() {
    }

    public static void addOrder(Order order) {
        if (orders.size() < MAX_ORDERS) {
            //unique identifier is timestamp*(1+id)
            orders.put(String.valueOf(order.getId()), order);
        } else {

        }
    }

    public static Order getOrder(String id) {
        //System.out.println(orders+":"+id);
        return orders.get(id);
    }

    public static String generateID() {
        return "123456";
    }
}
