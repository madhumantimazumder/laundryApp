package com.iiitb.laundry.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import iiitb.ss.admin.bean.Employee;
import iiitb.ss.admin.util.SessionUtil;

public class AdminDAO {
	private List<Employee> admin=new ArrayList<Employee>();
	public Employee getEmployeeLogin(String username,String password)
	{
		Session session = SessionUtil.getSession(); 
		session.beginTransaction();
		Query q = session.createQuery("from Employee where Username = :EmpName and Password = :Password ");
		q.setParameter("EmpName", username);  
		q.setParameter("Password", password); 
		admin = q.list();
		if(admin.size()==0) {
			return null;
		}
		int dept_id=admin.get(0).getDept().getDeptID();
		session.close();
		//if(searchDept(dept_id).equals("Admin")) {
			return admin.get(0);
		//}
		//return null;
	}
	private String searchDept(int dept_id) {
		DepartmentDAO deptdao=new DepartmentDAO();
		String deptname=deptdao.getDepartmentNameById(dept_id);
		System.out.println("----------" + deptname);
		return deptname;		
	}

}
