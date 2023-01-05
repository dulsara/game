package com.dulsara.game.bet.service;


import com.dulsara.game.bet.dto.BetDTO;
import com.dulsara.game.bet.model.Bet;
import com.dulsara.game.exception.BadRequestException;
import com.dulsara.game.util.GlobalConstant;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class BetService {


    public Bet processBet(BetDTO betDTO) throws Exception {
        // validate user input
        validateBet(betDTO);
        // if user input is valid, perform bet operation
        Bet validBet = new Bet();
        validBet.setBet(betDTO.getBet());
        validBet.setNumber(betDTO.getNumber());
        // set user win value, if use wins calculate win value based on formula, user loose win value should be zero
        validBet.setWinValue(calculateWinValue(validBet.getNumber(),generateServerBet(),validBet.getBet()));
        return validBet;
    }


    private void validateBet(BetDTO betDTO) throws Exception {

        if (betDTO.getNumber() == null) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_NUMBER_MANDATORY_ERROR);
        }
        if (betDTO.getBet() == null || betDTO.getBet().isNaN()) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_VALUE_MANDATORY_ERROR);
        }

        // user value should less than 100 and more than 0
        if (betDTO.getNumber() > 99 || betDTO.getNumber() < 1) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_NUMBER_INVALID_ERROR);
        }

        // bet value can't be minus value
        if (betDTO.getBet() <= 0.00 ) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_INVALID_ERROR);
        }
    }

    public Double calculateWinValue (Integer userBetNumber, Integer serverNumber, Double userBet) {

        // execute win value formula
        if (userBetNumber >= serverNumber) {
            Double win = (userBet * 99) / (100 - userBetNumber);

            BigDecimal winDecimalValue = new BigDecimal(win).setScale(2, RoundingMode.HALF_UP);

            return winDecimalValue.doubleValue();
        }
        else {
            return 0.00;
        }

    }

    private Integer generateServerBet () {
        Random r = new Random();
        Integer serverNumber = r.nextInt(GlobalConstant.RandomValueGenerator.MAX_VALUE-GlobalConstant.RandomValueGenerator.MIN_VALUE) + GlobalConstant.RandomValueGenerator.MIN_VALUE;
        return serverNumber;
    }

}
