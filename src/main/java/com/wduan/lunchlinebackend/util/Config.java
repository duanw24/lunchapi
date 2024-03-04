package com.wduan.lunchlinebackend.util;

import lombok.Getter;
import lombok.Setter;

public class Config {
    @Getter@Setter
    private static boolean emailAuth = false;
    @Getter@Setter
    private static int maxOrders = 100;


}
