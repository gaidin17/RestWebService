package com.epam.services;

import com.epam.domain.Ticket;
import com.epam.exceptions.BookingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Evgeny_Akulenko on 7/6/2016.
 */
public class TicketService {
    private static final TicketService instance = new TicketService();
    private List<Ticket> tickets = new ArrayList<>();

    private TicketService() {
        Ticket ticket1 = new Ticket("Tver", "Moscow", new Date(), new Date(), 1000.0);
        Ticket ticket2 = new Ticket("Moscow", "Tver", new Date(), new Date(), 1000.0);
        Ticket ticket3 = new Ticket("Tula", "Moscow", new Date(), new Date(), 1100.0);
        Ticket ticket4 = new Ticket("Tula", "Moscow", new Date(), new Date(), 1100.0);
        Ticket ticket5 = new Ticket("Moscow", "Tula", new Date(), new Date(), 1100.0);
        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);
        tickets.add(ticket4);
        tickets.add(ticket5);
    }

    public static TicketService getInstance() {
        return instance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket getTicketById(int id) throws BookingException {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                return ticket;
            }
        }
        throw new BookingException("Ticket is booked allready or not exist");
    }

    public void removeTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                tickets.remove(ticket);
                return;
            }
        }
    }

}
