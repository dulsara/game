package com.dulsara.game.execution;

import com.dulsara.game.bet.model.Bet;
import com.dulsara.game.bet.service.BetService;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BetExecution implements Runnable {
    private static int betCount = 1;

    private static Double totalBetValue = 0.00;

    private static Double totalWinValue = 0.00;


    private static final int betExecutionLimit = 1000000;
    private static final int threadPoolSize = 24;
    private static final Object lock = new Object();

    private static final BetService betService = new BetService();

    private static final Random r = new Random();

    @Override
    public void run() {
        synchronized (lock) {
            while (betCount <= betExecutionLimit) {
                executeBet();
            }
        }
    }

    private void executeBet() {
        synchronized (lock) {
            betCount++;
            Bet bet = new Bet();
            double betValue = r.nextDouble();
            totalBetValue = totalBetValue + betValue;
            bet.setBet(betValue);
            bet.setNumber(betService.generateServerBet());
            totalWinValue  = totalWinValue + betService.calculateWinValue(bet,betService.generateServerBet());
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new BetExecution());
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("Total Bet : " + totalBetValue);
            System.out.println("Total Win : " + totalWinValue);
            Double rtp = (totalWinValue / totalBetValue) * 100;

            System.out.println("RTP : " + String.format("%.2f", rtp) +"%");


        } catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }
    }
}
