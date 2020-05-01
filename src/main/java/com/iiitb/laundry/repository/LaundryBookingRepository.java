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
}
