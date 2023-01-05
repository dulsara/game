package com.dulsara.game.bet.execution;

import com.dulsara.game.bet.service.BetService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@SpringBootTest
public class ExecutionTest {

    @InjectMocks
    private BetService betService;

    Logger logger = Logger.getLogger(ExecutionTest.class.getName());

    @Test
    @SneakyThrows
    void processBet() {
        int threadPoolSize = 24;

        // setting 24 threads for Executor thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new BetExecutionTest(betService));
        }
        executorService.shutdown();

        // wait for completing each and every threads in thread pool
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // log the necessary information related RTP value
        logger.info("\n\n ****** Total Bet : " + String.format("%.2f", BetExecutionTest.totalBetValue) + " ******\n");
        logger.info("\n\n ******Total Win : " + String.format("%.2f", BetExecutionTest.totalWinValue) + " ******\n");
        Double rtp = (BetExecutionTest.totalWinValue / BetExecutionTest.totalBetValue) * 100;
        logger.info("\n\n ****** RTP : " + String.format("%.2f", rtp) + "% ******\n");
    }
}
