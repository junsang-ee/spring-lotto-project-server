package com.lotto.web.util;

public class LottoUtil {
    public static final int priceUnit = 1000;

    public static boolean getIsCorrectPrice(int price) {
        return Math.floorMod(price, priceUnit) == 0;
    }

    public static int getLottoCount(int price) {
        return Math.floorDiv(price, priceUnit);
    }

    public static int getRandomNumber() {
        return (int) (Math.random() * 45 + 1);
    }

}
