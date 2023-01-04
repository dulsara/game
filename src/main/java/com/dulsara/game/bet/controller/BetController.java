package com.dulsara.game.bet.controller;

import com.dulsara.game.bet.model.Bet;
import com.dulsara.game.bet.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/bet")
@RequiredArgsConstructor

public class BetController {

    private final BetService betService;

    @GetMapping
    public ResponseEntity<Bet> processBet(@RequestBody  @Valid Bet userBet) throws Exception {
        return ResponseEntity.ok().body(betService.processBet(userBet));
    }

}
