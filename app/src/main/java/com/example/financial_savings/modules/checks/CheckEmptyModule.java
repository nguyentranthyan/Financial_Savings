package com.example.financial_savings.modules.checks;

public class CheckEmptyModule {

    public static boolean isEmpty(String param1, String param2, String param3) {
        return !param1.isEmpty() && !param2.isEmpty() && !param3.isEmpty();
    }
}
