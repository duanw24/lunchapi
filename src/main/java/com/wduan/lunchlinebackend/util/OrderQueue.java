package com.wduan.lunchlinebackend.util;

import com.wduan.lunchlinebackend.Config;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import lombok.Getter;

import java.util.HashMap;


public class OrderQueue {
    //private static final int MAX_ORDERS = 100;
    @Getter
    private static HashMap<String, Order> orders = new HashMap<>();

    public static int size() {
        return orders.size();
    }

    public static void init() {
    }

    public static void addOrder(Order order) {
        if (orders.size() < Config.getMaxOrders()) {
            //unique identifier is timestamp*(1+id)
            orders.put(String.valueOf(order.getId()), order);
            if(!Config.isEmailAuth()) {
                dbHelper.submitOrder(order);
            }
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
