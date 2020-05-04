package com.iiitb.laundry.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.Status;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.utils.DBUtils;

public class LaundryBookingRepository {
	
	public List<LaundryBooking> fetchAllBookedSlots(String bookingDate) throws Exception{
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=format.parse(bookingDate);
        String hql = "FROM LaundryBooking WHERE  bookingDate = : date and status = : status_field";
        Query query = session.createQuery(hql);
        query.setParameter("date", date);
        query.setParameter("status_field", Status.NORMAL);
        List<LaundryBooking> list= (List<LaundryBooking>) query.getResultList();
        transaction.commit();
        session.close();
		return list;
	}
	
	public LaundryBooking fetchBookedSlot(Student student,String bookingDate) throws Exception {
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=format.parse(bookingDate);
		String hql = "FROM LaundryBooking WHERE  bookingDate = : date and student = : student_id and status = : status_field";
        Query query = session.createQuery(hql);
        query.setParameter("date", date);
        query.setParameter("student_id", student);
        query.setParameter("status_field", Status.NORMAL);
        LaundryBooking laundryBooking;
		try {
			laundryBooking = (LaundryBooking) query.getSingleResult();
		} catch (NoResultException e) {
			laundryBooking=null;
		}
		transaction.commit();
		session.close();
		return laundryBooking;
	}
	
	public List<LaundryBooking> fetchAllNormalBookings(String startbookingDate,String endbookingDate) throws Exception{
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start_date=format.parse(startbookingDate);
		Date end_date=format.parse(endbookingDate);
		String hql = "FROM LaundryBooking WHERE  status = : status_field and bookingDate between : start_date and : end_date";
		Query query = session.createQuery(hql);
		query.setParameter("start_date", start_date);
		query.setParameter("end_date", end_date);
		query.setParameter("status_field", Status.NORMAL);
		List<LaundryBooking> list= (List<LaundryBooking>) query.getResultList();
		transaction.commit();
		session.close();
		return list;
	}
	
	public void saveLaundryBooking(LaundryBooking laundryBooking) throws Exception {
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String hql = "FROM LaundryBooking WHERE  slot = : laundry_slot and bookingDate = : booking_date and status = : status_field";
        Query query = session.createQuery(hql);
        query.setParameter("laundry_slot", laundryBooking.getSlot());
        query.setParameter("booking_date", laundryBooking.getBookingDate());
        query.setParameter("status_field", Status.NORMAL);
        List<LaundryBooking> list= (List<LaundryBooking>) query.getResultList();
        if(list.size()>=laundryBooking.getSlot().getNoOfMachines())
        	throw new Exception();
        
		session.save(laundryBooking);
		transaction.commit();
        session.close();
	}
	
	public void cancelLaundryBooking(String bookingDate,Student student) throws Exception{
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date=dateFormatter.parse(bookingDate);
		CriteriaBuilder builder= session.getCriteriaBuilder();
		CriteriaUpdate<LaundryBooking> criteria=builder.createCriteriaUpdate(LaundryBooking.class);
		Root<LaundryBooking> root=criteria.from(LaundryBooking.class);
		Predicate predicates[]=new Predicate[3];
		predicates[0]=builder.equal(root.get("student"), student);
		predicates[1]=builder.equal(root.get("bookingDate"), date);
		predicates[2]=builder.equal(root.get("status"), Status.NORMAL);
		criteria.set(root.get("status"), Status.CANCEL);
		criteria.where(predicates);
		session.createQuery(criteria).executeUpdate();
		transaction.commit();
        session.close();
	}
}
