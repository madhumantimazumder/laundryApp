package com.iiitb.laundry.beans;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id  @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int EmpID;

    @Column(nullable=false)
    private String EmpName;
    @Column(nullable=false)
    private String Username;
    @Column(nullable=false)
    private String Password;
    @Column(nullable=false)
    private String Contact;


    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public int getEmpID() {
        return EmpID;
    }
    public void setEmpID(int empID) {
        EmpID = empID;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getEmpName() {
        return EmpName;
    }
    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }


	/*@Override
	public String toString() {
		return "Admin [EmpID=" + EmpID + ", EmpName=" + EmpName + ", DeptID=" + DeptID + "]";
	}*/
}