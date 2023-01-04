package com.dulsara.game.bet.service;


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


    public Bet processBet(Bet bet) throws Exception {
        validateBet(bet);
        bet.setWinValue(calculateWinValue(bet,generateServerBet()));
        return bet;
    }


    private void validateBet(Bet bet) throws Exception {

        if (bet.getNumber() == null) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_NUMBER_MANDATORY_ERROR);
        }
        if (bet.getBet() == null || bet.getBet().isNaN()) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_VALUE_MANDATORY_ERROR);
        }

        if (bet.getNumber() > 99 || bet.getNumber() < 1) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_NUMBER_INVALID_ERROR);
        }

        if (bet.getBet() <= 0.00 ) {
            throw new BadRequestException(GlobalConstant.BetErrors.BET_INVALID_ERROR);
        }
    }

    public Double calculateWinValue (Bet bet, Integer serverNumber) {

        if (bet.getNumber() >= serverNumber) {
            Double win = (bet.getBet() * 99) / (100 - bet.getNumber());

            BigDecimal winDecimalValue = new BigDecimal(win).setScale(2, RoundingMode.HALF_UP);

            return winDecimalValue.doubleValue();
        }
        else {
            return 0.00;
        }

    }

    public Integer generateServerBet () {
        Random r = new Random();
        Integer serverNumber = r.nextInt(GlobalConstant.RandomValueGenerator.MAX_VALUE-GlobalConstant.RandomValueGenerator.MIN_VALUE) + GlobalConstant.RandomValueGenerator.MIN_VALUE;
        return serverNumber;
    }

}
