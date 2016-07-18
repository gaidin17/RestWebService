package com.epam.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Evgeny_Akulenko on 7/6/2016.
 */

public class WebApplicationBookingException extends WebApplicationException {
    public WebApplicationBookingException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
