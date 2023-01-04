package com.dulsara.game.bet.service;

import com.dulsara.game.bet.model.Bet;
import com.dulsara.game.util.GlobalConstant;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BetServiceTest {

    @InjectMocks
    private BetService betService;

    @Test
    @SneakyThrows
    void processBet() {

        Bet bet = new Bet();
        bet.setBet(20.5);
        bet.setNumber(99);
        Bet processBet = betService.processBet(bet);
        Assertions.assertTrue(processBet.getWinValue() > 0, "Checking that bet is having proper winValue");

        Bet bet1 = new Bet();
        bet1.setBet(20.5);
        bet1.setNumber(100);
        Exception exception1 = assertThrows(Exception.class, () -> {
            Bet processBet1= betService.processBet(bet1);;
        });
        String expectedMessage1 = GlobalConstant.BetErrors.BET_NUMBER_INVALID_ERROR;
        String actualMessage1 = exception1.getMessage();
        assertTrue(expectedMessage1.contains(actualMessage1));

        Bet bet2 = new Bet();
        bet2.setBet(null);
        bet2.setNumber(100);
        Exception exception2 = assertThrows(Exception.class, () -> {
            Bet processBet2= betService.processBet(bet2);;
        });
        String expectedMessage2 = GlobalConstant.BetErrors.BET_VALUE_MANDATORY_ERROR;
        String actualMessage2 = exception2.getMessage();
        assertTrue(expectedMessage2.contains(actualMessage2));

        Bet bet3 = new Bet();
        bet3.setBet(50.4);
        bet3.setNumber(null);
        Exception exception3 = assertThrows(Exception.class, () -> {
            Bet processBet3= betService.processBet(bet3);;
        });
        String expectedMessage3 = GlobalConstant.BetErrors.BET_NUMBER_MANDATORY_ERROR;
        String actualMessage3 = exception3.getMessage();
        assertTrue(expectedMessage3.contains(actualMessage3));

        Bet bet4 = new Bet();
        bet4.setBet(-10.4);
        bet4.setNumber(45);
        Exception exception4 = assertThrows(Exception.class, () -> {
            Bet processBet4= betService.processBet(bet4);;
        });
        String expectedMessage4 = GlobalConstant.BetErrors.BET_INVALID_ERROR;
        String actualMessage4 = exception4.getMessage();
        assertTrue(expectedMessage4.contains(actualMessage4));

    }

    @Test
    void calculateWinValue() {
        Bet bet5 = new Bet();
        bet5.setNumber(50);
        bet5.setBet(40.5);
        Double winValue = betService.calculateWinValue(bet5,20);
        assertEquals(80.19,winValue);

        Bet bet7 = new Bet();
        bet7.setNumber(50);
        bet7.setBet(40.5);
        Double winValue3 = betService.calculateWinValue(bet7,50);
        assertEquals(80.19,winValue3);

        Bet bet6 = new Bet();
        bet6.setNumber(40);
        bet6.setBet(40.5);
        Double winValue2 = betService.calculateWinValue(bet6,50);
        assertEquals(0.00,winValue2);
    }

    @Test
    void generateServerBet() {
        Integer generatedNumber = betService.generateServerBet();
        assertTrue(generatedNumber > 0);
        assertTrue(generatedNumber < 100);

    }
}