package com.wduan.lunchlinebackend.util;

import lombok.Getter;
import lombok.Setter;

public class Config {
    @Getter@Setter
    private static boolean emailAuth = true;
    @Getter@Setter
    private static int maxOrders = 100;


}
