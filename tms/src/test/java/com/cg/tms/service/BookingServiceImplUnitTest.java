package com.cg.tms.service;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.tms.entities.Booking;
import com.cg.tms.exceptions.BookingNotFoundException;
import com.cg.tms.exceptions.InvalidBookingException;
import com.cg.tms.repository.IBookingRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplUnitTest {
	
	@Mock
	IBookingRepository repo;
	
	
	@Spy
	@InjectMocks
	BookingServiceImpl service;


	/**
	 * scenario: booking is successful
	 *
	 */
	@Test
	void testMakeBooking_1() {
		String bookingType="Vacation";
		String bookingTitle="Goa Trip";
		int userId=1;
		Booking book = new Booking(bookingType,bookingTitle,userId);
		Booking saved=mock(Booking.class);
		Mockito.when(repo.save(book)).thenReturn(saved);
		doNothing().when(service).validateBookingType(bookingType);
		doNothing().when(service).validateBookingTitle(bookingTitle);
		doNothing().when(service).validateUserId(userId);
		Booking result = service.makeBooking(book);
		Assertions.assertNotNull(result);
		Assertions.assertSame(saved,result);
		verify(repo).save(book);
		verify(service).validateBookingTitle(bookingTitle);
		verify(service).validateUserId(userId);
		verify(service).validateBookingType(bookingType);
	}

	/*

	// id cannot be negative testcase
	@Test
	void testMake_2() {
		int userId=-1;
		Booking book = new Booking("Vacation","Goa Trip",userId);		
		Executable executable = ()->service.makeBooking(book);
		Assertions.assertThrows(InvalidIdException.class, executable);
	}
	*/

	// booking type cannot be empty
	@Test
	void testMakeBooking_3() {
		String bookingType="";
		Booking book = new Booking(bookingType,"Goa Trip",1);
		doThrow(InvalidBookingException.class).when(service).validateBookingType(bookingType);
		Executable executable = ()->service.makeBooking(book);
		Assertions.assertThrows(InvalidBookingException.class, executable);
	    verify(repo,never()).save(book);
	}
	/*
	//booking title cannot be null
	@Test
	void testMake_4() {
		String bookingTitle="";
		Booking book = new Booking("Vacation",bookingTitle,1);
		Executable executable = ()->service.makeBooking(book);
		Assertions.assertThrows(InvalidBookingException.class, executable);
		
	}
	
	
	*/


	/**
	 * scenario : booking found
	 */
	@Test
	void testFind_1() {
		int id=1;
		Booking book = mock(Booking.class);
		Optional<Booking> optional = Optional.of(book);
		when(repo.findById(id)).thenReturn(optional);
		Booking result = service.viewBooking(id);
		Assertions.assertSame(result, book);
		verify(repo).findById(id);
	}

	/**
	 * scenario: Booking not found
	 *
	 */
	@Test
	void testFind_2() {
		int id=12;		
		Optional<Booking> optional = Optional.empty();
		when(repo.findById(id)).thenReturn(optional);
		Executable executable =()->service.viewBooking(id);
		Assertions.assertThrows(BookingNotFoundException.class,executable);
		
	}

	
	

}
