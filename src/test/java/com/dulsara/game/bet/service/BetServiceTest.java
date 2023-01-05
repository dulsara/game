package com.dulsara.game.bet.service;

import com.dulsara.game.bet.dto.BetDTO;
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

        // checking that bet has executed properly
        BetDTO betDTO = new BetDTO();
        betDTO.setBet(20.5);
        betDTO.setNumber(99);
        Bet processBet = betService.processBet(betDTO);
        Assertions.assertTrue(processBet.getWinValue() > -1, "Checking that bet is having proper winValue");

        // checking that number invalid validation
        BetDTO betDTO1 = new BetDTO();
        betDTO1.setBet(20.5);
        betDTO1.setNumber(100);
        Exception exception1 = assertThrows(Exception.class, () -> {
            Bet processBet1 = betService.processBet(betDTO1);
            ;
        });
        String expectedMessage1 = GlobalConstant.BetErrors.BET_NUMBER_INVALID_ERROR;
        String actualMessage1 = exception1.getMessage();
        assertTrue(expectedMessage1.contains(actualMessage1));

        // checking that bet value mandatory validation

        BetDTO betDTO2 = new BetDTO();
        betDTO2.setBet(null);
        betDTO2.setNumber(100);
        Exception exception2 = assertThrows(Exception.class, () -> {
            Bet processBet2 = betService.processBet(betDTO2);
            ;
        });
        String expectedMessage2 = GlobalConstant.BetErrors.BET_VALUE_MANDATORY_ERROR;
        String actualMessage2 = exception2.getMessage();
        assertTrue(expectedMessage2.contains(actualMessage2));

        // checking that bet number Mandatory  validation
        BetDTO betDTO3 = new BetDTO();
        betDTO3.setBet(50.4);
        betDTO3.setNumber(null);
        Exception exception3 = assertThrows(Exception.class, () -> {
            Bet processBet3 = betService.processBet(betDTO3);
            ;
        });
        String expectedMessage3 = GlobalConstant.BetErrors.BET_NUMBER_MANDATORY_ERROR;
        String actualMessage3 = exception3.getMessage();
        assertTrue(expectedMessage3.contains(actualMessage3));

        // checking that bet invalid value   validation
        BetDTO betDTO4 = new BetDTO();
        betDTO4.setBet(-10.4);
        betDTO4.setNumber(45);
        Exception exception4 = assertThrows(Exception.class, () -> {
            Bet processBet4 = betService.processBet(betDTO4);
            ;
        });
        String expectedMessage4 = GlobalConstant.BetErrors.BET_INVALID_ERROR;
        String actualMessage4 = exception4.getMessage();
        assertTrue(expectedMessage4.contains(actualMessage4));


    }

    @Test
    void calculateWinValue() {

        // check win value when userValue greater than Server value
        Integer userNumber = 50;
        Integer serverNumber = 40;
        Double userBet = 40.5;
        Double winValue = betService.calculateWinValue(userNumber, serverNumber, userBet);
        assertEquals(80.19, winValue);

        // check win value when userValue equal  Server value

        userNumber = 50;
        serverNumber = 50;
        userBet = 40.5;
        Double winValue2 = betService.calculateWinValue(userNumber, serverNumber, userBet);
        assertEquals(80.19, winValue2);

        // check win value when userValue less than Server value
        userNumber = 50;
        serverNumber = 60;
        userBet = 40.5;
        Double winValue3 = betService.calculateWinValue(userNumber, serverNumber, userBet);
        assertEquals(0.00, winValue3);
    }

}