package com.iiitb.laundry.controller;

import com.iiitb.laundry.service.BookingDataService;
import com.iiitb.laundry.service.LoginService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
        String expected="{\"status\":200,\"username\":\"M Mazumder\"}";
        when(loginService.getAdmin(Mockito.anyString())).thenReturn("M Mazumder");
        String actual=loginController.addStudentPost(request);
        assertEquals(expected,actual);
    }
}
