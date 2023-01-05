package com.dulsara.game.bet.execution;

import com.dulsara.game.bet.dto.BetDTO;
import com.dulsara.game.bet.service.BetService;
import com.dulsara.game.util.GlobalConstant;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class BetExecutionTest implements Runnable {
    Logger logger = Logger.getLogger(BetExecutionTest.class.getName());

    //define static atomic count value to handle 1 million bet executions
    private static AtomicInteger globalCount = new AtomicInteger(1);
    ;

    // each thread calculate total bet values if  own executions
    private Double localTotalBetValue = 0.00;

    // each thread calculate total win values if  own executions
    private Double localTotalWinValue = 0.00;

    //  use to calculate total bet value of every threads as static variable
    public static Double totalBetValue = 0.00;

    //  use to calculate total win value of every threads as static variable
    public static Double totalWinValue = 0.00;

    //  define the total executions as static variable
    private static final int betExecutionLimit = 1000000;
    private static final Object lock = new Object();

    private BetService betService;

    private final Random r = new Random();

    public BetExecutionTest(BetService betService) {
        this.betService = betService;
    }

    @Override
    public void run() {
        int localbet = 0;

        // atomically increase the count value
        localbet = globalCount.getAndIncrement();
        while (localbet <= betExecutionLimit) { // check that total count execution is less than the limit
            try {
                // execute the one bet process
                executeBet();
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
            // atomically increase the count value
            localbet = globalCount.getAndIncrement();
        }

        /**completing while loop means,  thread complete the necessary bet executions ( 1 million executions)
         Then need to compute total win value, bet value by adding every thread's final total win value and bet value to static variable
         static variables are common and need to calculate total value synchronizely.
         use the lock object to manage the synchronization properly **/
        synchronized (lock) {
            totalBetValue = totalBetValue + localTotalBetValue;
            totalWinValue = totalWinValue + localTotalWinValue;
        }

    }

    private void executeBet() throws Exception {

        BetDTO betDTO = new BetDTO();
        // bet value is taken as random nummber
        double betValue = r.nextDouble();
        // set the local total for bet values
        localTotalBetValue = localTotalBetValue + betValue;
        int userBetNumber = r.nextInt(GlobalConstant.RandomValueGenerator.MAX_VALUE - GlobalConstant.RandomValueGenerator.MIN_VALUE) + GlobalConstant.RandomValueGenerator.MIN_VALUE;
        betDTO.setBet(betValue);
        betDTO.setNumber(userBetNumber);
        // set the local total for win values
        localTotalWinValue = localTotalWinValue + betService.processBet(betDTO).getWinValue();
    }
}
