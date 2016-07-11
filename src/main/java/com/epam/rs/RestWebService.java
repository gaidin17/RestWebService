package com.epam.rs;

import com.epam.domain.Person;
import com.epam.domain.Ticket;
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
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/bookTicket/{id}")
    public Ticket bookTicket(@PathParam("id") String id, Person person) {
        Ticket ticket = bookingService.getTicketById(Integer.parseInt(id));
        Ticket ticket1 = bookingService.bookTicket(ticket, person);
        return ticket1;
    }

    @PUT
    @Path("/payTicket/{bookingid}")
    @Produces(MediaType.TEXT_PLAIN)
    public String payTicket(@PathParam("bookingId") String bookingId) {
        try {
            bookingService.payTicket(Integer.parseInt(bookingId));
            return String.valueOf(Boolean.TRUE);
        } catch (Exception ex) {
            return String.valueOf(Boolean.FALSE);
        }
    }

    @DELETE
    @Path("/returnTicket/{bookingid}")
    @Produces(MediaType.TEXT_PLAIN)
    public String returnTicket(@PathParam("bookingid") String bookingId) {
        try {
            bookingService.returnTicket(Integer.parseInt(bookingId));
            return String.valueOf(Boolean.TRUE);
        } catch (Exception ex) {
            return String.valueOf(Boolean.FALSE);
        }
    }
}