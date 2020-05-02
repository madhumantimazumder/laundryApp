package com.iiitb.laundry.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.iiitb.laundry.service.LoginService;


@Path("/api/login")
public class LoginController {
	@GET  @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "working...";
    }
	
	@POST 
    @Path("/validatelogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addStudentPost(String json) {
        JSONObject jo = new JSONObject();
        LoginService limpl=new LoginService();
        String username = limpl.getAdmin(json);
        if(username==null) {
        	jo.put("status", 201);
        }
        else {
        	jo.put("status", 200);
        }
        jo.put("username", username);
        return jo.toJSONString();  
    }
}
