package com.iiitb.laundry.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.iiitb.laundry.beans.Hostel;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.utils.DBUtils;

public class LaundrySlotRepository {
	
	public List<LaundrySlot> findSlotByHostel(Hostel hostel)throws Exception {
		Session session = DBUtils.getSession();
		Transaction transaction = session.beginTransaction();
        
        String hql = "FROM LaundrySlot WHERE  hostel = :hostel_name";
        Query query = session.createQuery(hql);
        query.setParameter("hostel_name", hostel);
        List<LaundrySlot> slots=  (List<LaundrySlot>) query.getResultList();
        transaction.commit();
        session.close();
        return slots;
	}

}
