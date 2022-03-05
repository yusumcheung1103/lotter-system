package com.demo.controller;


import javax.validation.constraints.NotNull;

import java.util.Set;

import com.demo.model.Buyer;
import com.demo.model.Ticket;
import com.demo.model.Winner;
import com.demo.service.LotterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/lotter")
public class LotterController
{
    private final Logger LOGGER = LoggerFactory.getLogger(LotterController.class);

    @Autowired
    private LotterService lotterService;

    @RequestMapping(value = "/purchase/{nameOfBuyer}", method = RequestMethod.POST)
    public ResponseEntity<Buyer> purchaseTicket(@PathVariable @NotNull String nameOfBuyer)
    {
        return new ResponseEntity<>(lotterService.purchaseTicket(nameOfBuyer), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/checkContestantStatus/{ticketNumber}", method = RequestMethod.POST)
    public ResponseEntity checkContestantStatus(@PathVariable @NotNull long ticketNumber){
        Winner winner = lotterService.getCurrentWinner();
        if(winner.getTicket().getTicketNumber() == ticketNumber){
            return ResponseEntity.ok("Win");
        }else{
            return ResponseEntity.ok("Lose");
        }
    }


    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity resetProcess()
    {
        lotterService.resetProcess();
        return ResponseEntity.ok("The game is reset");
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public ResponseEntity<Set<Ticket>> getAllTickets()
    {
        return new ResponseEntity<>(lotterService.getAllTickets(), HttpStatus.OK);
    }

    @RequestMapping(value = "/purchased-tickets", method = RequestMethod.GET)
    public ResponseEntity<Set<Ticket>> getPurchasedTickets()
    {
        Set<Ticket> boughtTickets = lotterService.getPurchasedTickets();
        return new ResponseEntity<>(boughtTickets, HttpStatus.OK);
    }

    @Scheduled(fixedRate = 5000)
    @RequestMapping(value = "/draw", method = RequestMethod.POST)
    public ResponseEntity<Winner> draw()
    {
        Winner winner = lotterService.randomDraw();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }

    @RequestMapping(value = "/winner", method = RequestMethod.GET)
    public ResponseEntity<Winner> getCurrentWinner()
    {
        return new ResponseEntity<>(lotterService.getCurrentWinner(), HttpStatus.OK);
    }
}
