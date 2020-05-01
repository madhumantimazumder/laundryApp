package com.iiitb.laundry.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class LaundryBooking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "slot_id")
	private LaundrySlot slot;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date bookingDate;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public LaundrySlot getSlot() {
		return slot;
	}

	public void setSlot(LaundrySlot slot) {
		this.slot = slot;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
}
