var admin;
$(document).ready(function() {
    validateLogin();
});
function validateLogin(){
    $('#login_btn').click(function(event) {
           event.preventDefault();
           var username = $('#username').val();
           var password = $('#password').val();
           var json =  {'name':username, 
                        'password':password
                        };
           $.ajax({
                url: "/webapi/api/login/validatelogin",
                method: "POST",
                dataType: "JSON",
                contentType: "application/json",
                data: JSON.stringify(json),
                cache: false,
                async: true,
                timeout: 600000,
                success: function(response) {
                	if(response.status=="200"){
                		window.localStorage.setItem('username', response.username);
                		window.location.href = "/studentlist.html";
                	}
                	else{
                		$('.login-error').show();
	                	$('#password').val("") ;
	                	$('#username').val("");
                	}
                },
                error : function(response){
                    
                }
            });  
    });
}
