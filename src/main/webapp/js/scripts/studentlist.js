$(document).ready(function() {
	validateLogin();
});
function validateLogin(){
		var startDate = '2020-04-11';
		var endDate ='2020-04-12';
		var json =  {'startDate':startDate,
			'endDate':endDate
		};
		$.ajax({
			url: "/laundry_webapp/webapi/api/getData",
			method: "POST",
			dataType: "JSON",
			contentType: "application/json",
			data: JSON.stringify(json),
			cache: false,
			async: true,
			timeout: 600000,
			success: function(response) {
				console.log(response);
			},
			error : function(response){
				console.log(response)
			}
		});

}