package com.iiitb.laundry.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.iiitb.laundry.beans.Admin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.iiitb.laundry.beans.LaundryBooking;
import com.iiitb.laundry.beans.LaundrySlot;
import com.iiitb.laundry.beans.Student;

public class DBUtils {
    private static final SessionFactory sessionFactory;
    
    static {
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        try {
            Configuration configuration = new Configuration();
            
			configuration.addAnnotatedClass(Student.class).addAnnotatedClass(LaundrySlot.class)
					.addAnnotatedClass(LaundryBooking.class).addAnnotatedClass(Admin.class);
            
            sessionFactory = configuration.buildSessionFactory();
        }
        catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
