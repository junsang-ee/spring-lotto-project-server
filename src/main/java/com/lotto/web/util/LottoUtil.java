package com.lotto.web.util;

public class LottoUtil {
    public static final int priceUnit = 1000;

    public static final String DIV_DATE = "div.select_tab";
    public static final String DIV_WINNINGS = "div.winning_number";
    public static final String DIV_BONUS = "div.bonus_number";

    public static boolean getIsCorrectPriceUnit(int price) {
        return Math.floorMod(price, priceUnit) == 0;
    }

    public static int getLottoCount(int price) {
        return Math.floorDiv(price, priceUnit);
    }

    public static int getRandomNumber() {
        return (int) (Math.random() * 45 + 1);
    }

}
