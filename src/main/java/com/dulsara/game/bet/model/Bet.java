package com.dulsara.game.bet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bet {


    Integer number;
    Double bet;

    Double winValue;

}
