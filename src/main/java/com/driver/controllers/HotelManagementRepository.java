package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class HotelManagementRepository {
    private HashMap<String, Hotel>HotelDB;
    private HashMap<Integer, User>UserDB;
    private HashMap<String, Booking>bookingDB;
    public HashMap<Integer, User> getUserDB() {
        return UserDB;
    }

    public HashMap<String, Booking> getBookingDB() {
        return bookingDB;
    }

    public void setBookingDB(HashMap<String, Booking> bookingDB) {
        this.bookingDB = bookingDB;
    }

    public void setUserDB(HashMap<Integer, User> userDB) {
        UserDB = userDB;
    }

    public HotelManagementRepository() {
        HotelDB=new HashMap<String, Hotel>();
        UserDB=new HashMap<>();
        bookingDB=new HashMap<>();
    }

    public HashMap<String, Hotel> getHotelDB() {
        return HotelDB;
    }

    public void setHotelDB(HashMap<String, Hotel> hotelDB) {
        HotelDB = hotelDB;
    }
}
