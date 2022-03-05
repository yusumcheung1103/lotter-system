package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.demo.model.Buyer;
import com.demo.model.Ticket;
import com.demo.model.Winner;
import com.demo.repository.TicketsRepo;
import com.demo.utils.LotterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LotterService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LotterService.class);
    @Autowired
    TicketsRepo tickets;

    Winner currentWinner;


    public Buyer purchaseTicket(final String buyerName)
    {
        Optional<Ticket> ticket = tickets.getTickets().stream().filter(t -> !t.isPurchased()).findAny();
        if (ticket.isPresent())
        {
            ticket.get().setPurchased(true);
            ticket.get().setNameOfBuyer(buyerName);
            Buyer buyer = new Buyer(buyerName, ticket.get());
            LOGGER.info("{} bought a ticket number {}", buyerName, ticket.get().getTicketNumber());
            return buyer;
        }
        return null;
    }



    protected void populateCurrentWinners(final Winner currentWinner)
    {
        this.currentWinner = currentWinner;

    }

    public Winner randomDraw()
    {
        LOGGER.info("The Lottery Game is drawing for winners");
        List<Integer> randomList = new ArrayList();
        ThreadLocalRandom.current().ints(1, 50).distinct().limit(1).forEach(randomList::add);
        Ticket winningTicket = (Ticket) tickets.getTickets().toArray()[randomList.get(0)];

        Winner winner = new Winner(winningTicket.getNameOfBuyer(), winningTicket);

        populateCurrentWinners(winner);
        LotterUtil.logWinner(winner);
        LOGGER.info("Draw is complete");
        LOGGER.info("The Lottery Game is resetting the Tickets");
        tickets.getTickets().clear();
        tickets.generateTickets();

        return winner;
    }

    public void resetProcess()
    {
        tickets.getTickets().clear();
        tickets.generateTickets();
        currentWinner = null;
        LOGGER.info("The Lottery Game is reset");
    }

    public Set<Ticket> getAllTickets()
    {
        return tickets.getTickets();
    }


    public Set<Ticket> getPurchasedTickets()
    {
        return tickets.getTickets().stream().filter(Ticket::isPurchased).collect(Collectors.toSet());
    }

    public Winner getCurrentWinner()
    {
        Winner result = this.currentWinner;
        if (result != null)
        {
            LOGGER.info("Winners from the latest draw were: ");
            LotterUtil.logWinner(this.currentWinner);
        }
        else
        {
            LOGGER.info("There are no winners before a draw");
        }

        return result;
    }
}

