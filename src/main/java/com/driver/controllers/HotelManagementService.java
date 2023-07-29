package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {
    @Autowired
    private HotelManagementRepository HMrepo;

    public String addHotel(Hotel hotel) {
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
        HashMap<String,Hotel> hotelDB=HMrepo.getHotelDB();

        if(hotel.getHotelName() == null||hotel==null){
            return "an empty a FAILURE";
        }else if(hotelDB.containsValue(hotel.getHotelName())){
            return "FAILURE";
        }else{
            hotelDB.put(hotel.getHotelName(),hotel);
            HMrepo.setHotelDB(hotelDB);
            return "SUCCESS";
        }

    }

    public Integer addUser(User user) {
        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user
        HashMap<Integer, User>UserDB=HMrepo.getUserDB();
        UserDB.put(user.getaadharCardNo(),user);
        HMrepo.setUserDB(UserDB);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)
        String ans="";
        int facilites=0;
        HashMap<String,Hotel> hotelDB=HMrepo.getHotelDB();
        for(String name: hotelDB.keySet()){
            if(hotelDB.get(name).getFacilities().size()>facilites){
                ans=name;
            }else if(hotelDB.get(name).getFacilities().size()==facilites){
                if(ans==null){
                    ans=name;
                }else{
                    if(ans.compareTo(name) >0){
                        ans=name;
                    }
                }
            }
        }
        return ans;
    }

    public int getbookARoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid
        UUID bookingId= UUID.randomUUID();
        booking.setBookingId(bookingId.toString());
        HashMap<String,Hotel> hotelDB=HMrepo.getHotelDB();
        int roomsneeded=booking.getNoOfRooms();
        String hotelname=booking.getHotelName();
        Hotel hotel=hotelDB.get(hotelname);
        int roomsavilable=hotel.getAvailableRooms();
        int price=hotel.getPricePerNight();
        int amountToPay=price*roomsneeded;
        booking.setAmountToBePaid(amountToPay);
        HashMap<String,Booking>bookingDB=HMrepo.getBookingDB();
        bookingDB.put(bookingId.toString(),booking);
        HMrepo.setBookingDB(bookingDB);

        if(roomsavilable>=roomsneeded){
            roomsavilable-=roomsneeded;
            hotel.setAvailableRooms(roomsavilable);
            hotelDB.put(hotelname,hotel);
            HMrepo.setHotelDB(hotelDB);
            return amountToPay;
        }else{
            hotelDB.put(hotelname,hotel);
            HMrepo.setHotelDB(hotelDB);
           return -1;
        }
    }

    public int getBookingPerson(Integer aadharCard) {
        //In this function return the bookings done by a person
        HashMap<String,Booking>bookingDB=HMrepo.getBookingDB();
        int bookings=0;
        for(String Bid: bookingDB.keySet()){
            if(bookingDB.get(Bid).getBookingAadharCard()==aadharCard){
                bookings++;
            }
        }
        return bookings;
    }

    public Hotel updateFacilites(List<Facility> newFacilities, String hotelName) {
        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        HashMap<String,Hotel> hotelDB=HMrepo.getHotelDB();
        Hotel hotel=hotelDB.get(hotelName);
        List<Facility>oldlist=hotel.getFacilities();
        for(Facility f:newFacilities){
            if(!oldlist.contains(f)){
                oldlist.add(f);
            }
        }
        hotel.setFacilities(oldlist);
        hotelDB.put(hotelName,hotel);
        HMrepo.setHotelDB(hotelDB);
        return hotel;
    }
}
