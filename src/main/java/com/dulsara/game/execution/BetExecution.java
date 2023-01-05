package com.dulsara.game.execution;

import com.dulsara.game.bet.dto.BetDTO;
import com.dulsara.game.bet.service.BetService;
import com.dulsara.game.util.GlobalConstant;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BetExecution implements Runnable {
    private static int betCount = 1;

    private static AtomicInteger globalCount ;

    private  Double localTotalBetValue = 0.00;

    private  Double localTotalWinValue = 0.00;

    private   static Double totalBetValue = 0.00;

    private   static Double totalWinValue = 0.00;


    private static final int betExecutionLimit = 10000;
    private static final int threadPoolSize = 24;
    private static final Object lock = new Object();

    private static final BetService betService = new BetService();

    private  final Random r = new Random();

    @Override
    public void run() {
        int localbet =0 ;

        localbet = globalCount.getAndIncrement();
        while (localbet <= betExecutionLimit) {
            try {
                executeBet();
            } catch (Exception e) {
                System.out.println(e);
            }

            localbet = globalCount.getAndIncrement();
        }

        synchronized (lock) {
            totalBetValue = totalBetValue + localTotalBetValue;
            totalWinValue = totalWinValue + localTotalWinValue;
        }

    }

    private void executeBet() throws Exception {

            BetDTO betDTO = new BetDTO();
            double betValue = r.nextDouble();
            localTotalBetValue = localTotalBetValue + betValue;
            int userBetNumber = r.nextInt(GlobalConstant.RandomValueGenerator.MAX_VALUE-GlobalConstant.RandomValueGenerator.MIN_VALUE) + GlobalConstant.RandomValueGenerator.MIN_VALUE;
            betDTO.setBet(betValue);
            betDTO.setNumber(userBetNumber);
            localTotalWinValue  = localTotalWinValue + betService.processBet(betDTO).getWinValue();
    }

    public static void main(String[] args) {
        globalCount = new AtomicInteger(1);
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new BetExecution());
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("Total Bet : " + String.format("%.2f",totalBetValue));
            System.out.println("Total Win : " + String.format("%.2f",totalWinValue));
            Double rtp = (totalWinValue / totalBetValue) * 100;

            System.out.println("RTP : " + String.format("%.2f", rtp) +"%");


        } catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }
    }
}
