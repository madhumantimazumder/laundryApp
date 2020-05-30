package com.iiitb.laundry.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.iiitb.laundry.repository.AdminDAO;
import com.iiitb.laundry.beans.Admin;

public class LoginService {
	AdminDAO adminDao=new AdminDAO();
	public String getAdmin(String data) {
		JSONObject jObj;
		Admin admin=new Admin();
		try {
			jObj = (JSONObject)new JSONParser().parse(data);
			String username = (String) jObj.get("name");
			String password = (String) jObj.get("password");
			admin=adminDao.getEmployeeLogin(username, password);

			if(admin==null) {
				return null;
			}
			else if(admin.getPassword().equals(password) && admin.getUsername().equals(username)) {
				return admin.getEmpName();
			}
			else {
				return null;
			}
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return admin.getEmpName();

	}

}