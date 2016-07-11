package com.epam.client;

import com.epam.domain.Person;
import com.epam.domain.Ticket;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by Evgeny_Akulenko on 7/8/2016.
 */
public class RestClient {
    private static final String ALL_AVAILABLE_TICKETS = "http://localhost:8033/RestServiceTask/rest/getAllTickets";
    private static final String BOOK_TICKET = "http://localhost:8033/RestServiceTask/rest/bookTicket/";
    private static final String PAY_TICKET = "http://localhost:8033/RestServiceTask/rest/payTicket/";
    private static final String RETURN_TICKET = "http://localhost:8033/RestServiceTask/rest/returnTicket/";

    public static void main(String[] args) {

        Client client = Client.create();

        Person person = new Person("Vasiliy", "Alibabaevich", "Abdulaev", new Date());
        List<Ticket> tickets = getTickets(client);
        for (Ticket ticket : tickets) {
            System.out.println("ticketId: " + ticket.getTicketId());
        }
        System.out.println();

        try {
            Ticket ticket = bookTicket(client, 0, person);
            int ticketId = ticket.getTicketId();
            int bookingId = ticket.getBookingId();
            System.out.println("ticketId: " + ticketId);
            System.out.println("bookingId: " + bookingId);
            System.out.println();

            System.out.println("Ticket is paid: " + payTicket(client, bookingId));

            System.out.println("Ticket is returned: " + returnTicket(client, bookingId));
        } catch (Exception ex) {
            ex.getMessage();
        }


    }

    private static List<Ticket> getTickets(Client client) {
        WebResource webResource = client.resource(ALL_AVAILABLE_TICKETS);
        ClientResponse response = webResource.type("application/xml").get(ClientResponse.class);
        return response.getEntity(new GenericType<List<Ticket>>() {
        });
    }


    private static Ticket bookTicket(Client client, int id, Person person) {
        WebResource webResource = client.resource(BOOK_TICKET + id);
        ClientResponse response = webResource.type(MediaType.APPLICATION_XML).put(ClientResponse.class, person);
        return response.getEntity(Ticket.class);
    }

    private static Boolean payTicket(Client client, int bookingId) {
        WebResource webResource = client.resource(PAY_TICKET + bookingId);
        System.out.println(PAY_TICKET);
        ClientResponse response = webResource.put(ClientResponse.class);
        return (Boolean.getBoolean(response.getEntity(String.class)));
    }

    private static Boolean returnTicket(Client client, int bookingId) {
        WebResource webResource = client.resource(RETURN_TICKET + bookingId);
        System.out.println(RETURN_TICKET + bookingId);
        ClientResponse response = webResource.delete(ClientResponse.class);
        return Boolean.getBoolean(response.getEntity(String.class));
    }

    private static boolean isIdInteger(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
