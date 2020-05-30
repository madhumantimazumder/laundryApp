package com.iiitb.laundry.controller;

import com.iiitb.laundry.service.BookingDataService;
import com.iiitb.laundry.service.LoginService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class LoginControllerTest {

    private static final String request;

    static {
        request= "{\"name\":\"admin\", \"password\":\"admin\" }";
    }
    @InjectMocks
    LoginController loginController;
    @Mock
    LoginService loginService;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testForAdminLogin() throws Exception{
        String expected="M Mazumder";
        LoginService loginService=new LoginService();
        String actual= loginService.getAdmin(request);
        assertEquals(expected,actual);
    }
}
