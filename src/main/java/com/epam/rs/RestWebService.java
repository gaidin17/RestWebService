package com.epam.rs;

import com.epam.domain.Person;
import com.epam.domain.Ticket;
import com.epam.exceptions.BookingException;
import com.epam.exceptions.WebApplicationBookingException;
import com.epam.services.BookingService;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RestWebService {
    BookingService bookingService = BookingService.getInstance();

    @GET
    @Path("/getAllTickets/")
    @Produces(MediaType.APPLICATION_XML)
    public List<Ticket> getAllAvailableTickets() {
        return bookingService.getAllTickets();
    }

    @PUT
    @Produces(MediaType.APPLICATION_XML)
    @Path("/bookTicket/{id}")
    public Ticket bookTicket(@PathParam("id") int id, Person person) {
        try {
            Ticket ticket = bookingService.getTicketById(id);
            return bookingService.bookTicket(ticket, person);
        } catch (BookingException ex) {
            throw new WebApplicationBookingException(ex.getMessage());
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_XML)
    @Path("/payTicket/{bookingId}")
    public Ticket payTicket(@PathParam("bookingId") int bookingId) {
        try {
            return bookingService.payTicket(bookingId);
        } catch (BookingException ex) {
            throw new WebApplicationBookingException(ex.getMessage());
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    @Path("/returnTicket/{bookingId}")
    public Ticket returnTicket(@PathParam("bookingId") int bookingId) {
        try {
            return bookingService.returnTicket(bookingId);
        } catch (BookingException ex) {
            throw new WebApplicationBookingException(ex.getMessage());
        }
    }
}