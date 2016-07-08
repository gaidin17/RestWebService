package com.epam.client;


import com.epam.domain.Person;
import com.epam.domain.Ticket;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        System.out.println(getTickets(client));

        List<Ticket> tickets = getTickets(client);
        System.out.println(tickets.size());

        Person person = new Person("Vasiliy", "Alibabaevich", "Abdulaev", new Date());

        String bookingId = bookTicket(client, String.valueOf(tickets.get(0).getTicketId()), person);
        System.out.println(bookingId);

        if (isIdInteger(bookingId)) {
            String response = payTicket(client, bookingId);
            System.out.println(response);

            response = returnTicket(client, bookingId);
            System.out.println(response);
        }
    }

    private static List<Ticket> getTickets(Client client) {
        WebResource webResource = client.resource(ALL_AVAILABLE_TICKETS);
        ClientResponse response = webResource.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
        return response.getEntity(new GenericType<List<Ticket>>() {
        });
    }

    private static String bookTicket(Client client, String id, Person person) {
        WebResource webResource = client.resource(BOOK_TICKET + id);
        ClientResponse response = webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, person);
        return response.getEntity(String.class);
    }

    private static String payTicket(Client client, String bookingId) {
        WebResource webResource = client.resource(PAY_TICKET);
        System.out.println(PAY_TICKET);
        ClientResponse response = webResource.type(MediaType.TEXT_HTML).post(ClientResponse.class, bookingId);
        return response.getEntity(String.class);
    }

    private static String returnTicket(Client client, String bookingId) {
        WebResource webResource = client.resource(RETURN_TICKET + bookingId);
        System.out.println(RETURN_TICKET + bookingId);
        ClientResponse response = webResource.type(MediaType.TEXT_HTML).get(ClientResponse.class);
        return response.getEntity(String.class);
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
