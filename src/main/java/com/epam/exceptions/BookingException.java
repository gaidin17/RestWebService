package com.epam.exceptions;

/**
 * Created by Evgeny_Akulenko on 7/6/2016.
 */
public class BookingException extends RuntimeException {
    public BookingException(String message) {
        super(message);
    }
}
