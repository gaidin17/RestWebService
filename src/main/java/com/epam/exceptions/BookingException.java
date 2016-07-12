package com.epam.exceptions;

/**
 * Created by Evgeny_Akulenko on 7/12/2016.
 */
public class BookingException extends Exception {

    private String message;
    public BookingException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
