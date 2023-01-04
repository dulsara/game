package com.dulsara.game.util;

public class GlobalConstant {


    public  static class BetCalculationMessages {

        public static final String BET_WIN_MESSAGE="Congratulations You have Won the Bet";
        public static final String BET_FAIL_MESSAGE="Sorry You have Lost the Bet";

    }

    public  static class BetErrors {

        public static final String BET_VALUE_MANDATORY_ERROR="Bet value is Mandatory";
        public static final String BET_NUMBER_MANDATORY_ERROR="Bet value is Mandatory";
        public static final String BET_NUMBER_INVALID_ERROR="Bet Number should be in 1 - 100 range";
        public static final String BET_INVALID_ERROR="Bet should be greater than 0 ";


    }

    public  static class RandomValueGenerator {

        public static final Integer MAX_VALUE= 99;
        public static final Integer MIN_VALUE= 1;

    }

}
