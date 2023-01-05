package com.dulsara.game.bet.controller;

import com.dulsara.game.bet.dto.BetDTO;
import com.dulsara.game.bet.model.Bet;
import com.dulsara.game.bet.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bet")
@RequiredArgsConstructor

public class BetController {

    private final BetService betService;

    // rest GET method to process bet values
    @GetMapping
    public ResponseEntity<Bet> processBet(@RequestBody BetDTO userBetDTO) throws Exception {
        return ResponseEntity.ok().body(betService.processBet(userBetDTO));
    }

}
