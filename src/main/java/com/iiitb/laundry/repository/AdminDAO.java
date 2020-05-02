package com.iiitb.laundry.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.iiitb.laundry.beans.Admin;
import com.iiitb.laundry.utils.DBUtils;

public class AdminDAO {
	private List<Admin> admin=new ArrayList<Admin>();
	public Admin getEmployeeLogin(String username,String password)
	{
		Session session = DBUtils.getSession();
		session.beginTransaction();
		Query q = session.createQuery("from Admin where Username = :EmpName and Password = :Password");
		q.setParameter("EmpName", username);  
		q.setParameter("Password", password); 
		admin = q.list();
		if(admin.size()==0) {
			return null;
		}
		session.close();
		return admin.get(0);

	}


}
