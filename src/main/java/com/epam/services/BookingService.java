package com.epam.services;

import com.epam.domain.Person;
import com.epam.domain.Ticket;
import com.epam.domain.enums.TicketState;
import com.epam.exceptions.BookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny_Akulenko on 7/6/2016.
 */
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private static final BookingService instance = new BookingService();
    private static int count = 0;
    private TicketService ticketService = TicketService.getInstance();
    private List<Ticket> bookedTickets = new ArrayList<>();

    private BookingService() {
    }

    public static BookingService getInstance() {
        return instance;
    }

    public List<Ticket> getAllTickets() {
        return ticketService.getTickets();
    }

    public Ticket bookTicket(Ticket ticket, Person person) throws BookingException {
        ticket.setBookingId(generateBookId());
        ticket.setTicketState(TicketState.BOOKED);
        ticket.setPerson(person);
        bookedTickets.add(ticket);
        ticketService.removeTicket(ticket.getTicketId());
        logger.info("Ticket with id = {} is successfully booked. BookingId = {}", ticket.getTicketId(), ticket.getBookingId());
        return ticket;
    }

    public Ticket getTicketByBookingId(int bookingId) throws BookingException {
        for (Ticket ticket : bookedTickets) {
            if (ticket.getBookingId() == bookingId) {
                return ticket;
            }
        }
        logger.warn("BookingId = {} is not exist in system", bookingId);
        throw new BookingException("This Ticket is not exist in system");
    }

    public Ticket payTicket(int bookingId) throws BookingException {
        Ticket ticket = getTicketByBookingId(bookingId);
        if (ticket.getTicketState().equals(TicketState.BOOKED)) {
            ticket.setTicketState(TicketState.PAID);
            logger.info("Ticket with bookingId = {} was successfully paid", bookingId);
            return ticket;
        } else {
            throw new BookingException("This Ticket is not booked");
        }
    }

    public Ticket returnTicket(int bookingId) throws BookingException {
        Ticket ticket = getTicketByBookingId(bookingId);
        ticket.setTicketState(TicketState.NOT_BOOKED);
        bookedTickets.remove(ticket);
        ticketService.addTicket(ticket);
        logger.info("Tisket with BookingId = {} was successfully returned", bookingId);
        return ticket;
    }

    public Ticket getTicketById(int id) throws BookingException {
        return ticketService.getTicketById(id);
    }

    private int generateBookId() {
        return ++count;
    }
}
