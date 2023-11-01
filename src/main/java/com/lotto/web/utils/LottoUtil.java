package com.lotto.web.utils;

public class LottoUtil {
    public static final int priceUnit = 1000;

    public static boolean getIsCorrectPrice(int price) {
        return Math.floorMod(price, priceUnit) == 0;
    }

}
