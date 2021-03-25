package com.cg.tms.repository;


import com.cg.tms.entities.Booking;

import java.util.Optional;

public interface IBookingRepository    {
	
	Booking save(Booking booking);

	Optional<Booking> findById(Integer id);


	/*public  Booking  makeBooking(Booking booking);
	public  Booking  cancelBooking(int bookingId) throws BookingNotFoundException;
	public  Booking  viewBooking(int bookingId)throws BookingNotFoundException;
	public List<Booking> viewAllBookings();*/
	
	
	
	
	

}
