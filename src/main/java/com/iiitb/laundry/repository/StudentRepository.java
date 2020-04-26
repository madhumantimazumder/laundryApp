package com.iiitb.laundry.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.utils.DBUtils;

public class StudentRepository {
	
	public Student findByMobileNumber(long mobileNo)throws Exception {
		Session session = DBUtils.getSession();
        Transaction transaction = session.beginTransaction();
        
        String hql = "FROM Student WHERE  mobileNo = :mobile_no";
        Query query = session.createQuery(hql);
        query.setParameter("mobile_no", mobileNo);
        Student student = (Student) query.getSingleResult();
        
        transaction.commit();
        session.close();
        return student;
	}
}
