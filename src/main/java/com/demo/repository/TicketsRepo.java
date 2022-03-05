package com.demo.repository;

import java.util.HashSet;
import java.util.Set;

import com.demo.model.Ticket;
import org.springframework.stereotype.Repository;


@Repository
public class TicketsRepo
{
    private Set<Ticket> tickets;

    TicketsRepo()
    {
        generateTickets();
    }

    public void generateTickets()
    {
        tickets = new HashSet<>();
        for (int i = 1; i < 51; i++)
        {
            Ticket ticket = new Ticket((long) i, null, false);
            tickets.add(ticket);
        }
    }

    public Set<Ticket> getTickets()
    {
        return tickets;
    }
}
