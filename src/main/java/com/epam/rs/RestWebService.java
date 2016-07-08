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

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/bookTicket/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllAvailableTickets(@PathParam("id") String id, Person person) {
        try {
            Ticket ticket = bookingService.getTicketById(Integer.parseInt(id));
            return String.valueOf(bookingService.bookTicket(ticket, person));
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @POST
    @Path("/payTicket/")
    @Produces(MediaType.TEXT_PLAIN)
    public String payTicket(String bookingId) {
        try {
            bookingService.payTicket(Integer.parseInt(bookingId));
            return "Ticket with id = " + bookingId + "was paid sucesifully";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @GET
    @Path("/returnTicket/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String returnTicket(@PathParam("id") String bookingId) {
        try {
            bookingService.returnTicket(Integer.parseInt(bookingId));
            return "Ticket with id = " + bookingId + "was returned sucesifully";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}