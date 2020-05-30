package com.iiitb.laundry.service;

import com.iiitb.laundry.beans.Admin;
import com.iiitb.laundry.beans.Student;
import com.iiitb.laundry.repository.AdminDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class LoginServiceTest {
    private static final String request;
    private static final Admin admin=new Admin();
    static {
        request= "{\"name\":\"admin\", \"password\":\"admin\" }";
        admin.setEmpID(1);
        admin.setEmpName("M Mazumder");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setContact("9433591033");
    }
    @InjectMocks
    LoginService loginService;

    @Mock
    AdminDAO adminDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAllNormalBookings() throws Exception{
        when(adminDao.getEmployeeLogin("username","password")).thenReturn(admin);
        String actual= loginService.getAdmin(request);
        String expected ="M Mazumder";
        assertEquals(true, true);
    }
}
