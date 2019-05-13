package com.otto.ottocash;

import java.text.DecimalFormat;

public class UiUtil {


    public static String formatMoneyIDR(long number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String result = "Rp " + formatter.format(number).replaceAll(",", ".");

        return result;
    }

}
