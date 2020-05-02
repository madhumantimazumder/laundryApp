package com.iiitb.laundry.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.utils.DBUtils;

public class LaundryBookingRepository {

	public List<LaundryBooking> fetchAllBookedSlots(String bookingDate) throws Exception{
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=format.parse(bookingDate);
        String hql = "FROM LaundryBooking WHERE  bookingDate = : date";
        Query query = session.createQuery(hql);
        query.setParameter("date", date);
        List<LaundryBooking> list= (List<LaundryBooking>) query.getResultList();
        transaction.commit();
        session.close();
		return list;
	}
	
	public List<LaundryBooking> fetchBookedSlots(String startbookingDate,String endbookingDate) throws Exception{
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start_date=format.parse(startbookingDate);
		Date end_date=format.parse(endbookingDate);
		String hql = "FROM LaundryBooking WHERE  bookingDate between : start_date and : end_date";
		Query query = session.createQuery(hql);
		query.setParameter("start_date", start_date);
		query.setParameter("end_date", end_date);
		List<LaundryBooking> list= (List<LaundryBooking>) query.getResultList();
		transaction.commit();
		session.close();
		return list;
	}
	
	public void saveLaundryBooking(LaundryBooking laundryBooking) throws Exception {
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String hql = "FROM LaundryBooking WHERE  slot = : laundry_slot and bookingDate = : booking_date";
        Query query = session.createQuery(hql);
        query.setParameter("laundry_slot", laundryBooking.getSlot());
        query.setParameter("booking_date", laundryBooking.getBookingDate());
        List<LaundryBooking> list= (List<LaundryBooking>) query.getResultList();
        if(list.size()>=laundryBooking.getSlot().getNoOfMachines())
        	throw new Exception();
        
		session.save(laundryBooking);
		transaction.commit();
        session.close();
	}
}
