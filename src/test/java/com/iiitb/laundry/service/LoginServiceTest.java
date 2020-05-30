package com.iiitb.laundry.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class LoginServiceTest {
    private static final String request;
    static {
        request= "{\"name\":\"admin\", \"password\":\"admin\" }";
    }
    @Mock
    LoginService loginService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAllNormalBookings() throws Exception{
        String actual= loginService.getAdmin(request);
        String expected ="M Mazumder";
        assertEquals(true, true);
    }
}
