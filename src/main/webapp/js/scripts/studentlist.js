$(document).ready(function() {
	if(window.localStorage.getItem(username)=="undefined")
		window.location.href = "/";
	window.localStorage.removeItem('username');
	getData();
	exportDataToCSV();
});
$(function() {
	var tomorrow = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()+1);
	$('#startDate').datepicker({
		uiLibrary: 'bootstrap4',
		iconsLibrary: 'fontawesome',
		maxDate: function () {
			return $('#endDate').val();
		}
	});
	$('#endDate').datepicker({
		uiLibrary: 'bootstrap4',
		iconsLibrary: 'fontawesome',
		maxDate:tomorrow,
		minDate: function () {
			return $('#startDate').val();
		}
	});
});
function getData(){
	var d = new Date();
	var date =   d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();;
	getBookingList(date,date);
	$('#date-btn').click(function (event) {
		event.preventDefault();
		$('.table').find("tr:gt(0)").remove();
		var startDate = $('#startDate').val();
		var endDate =$('#endDate').val();
		startDate = moment(startDate, "MM/DD/YYYY").format("YYYY-MM-DD");
		endDate = moment(endDate, "MM/DD/YYYY").format("YYYY-MM-DD");
		getBookingList(startDate,endDate);
	});

}
function getBookingList(startDate,endDate){
	var json =  {'startDate':startDate,
		'endDate':endDate
	};
	$.ajax({
		url: "/webapi/api/bookings/getdata",
		method: "POST",
		dataType: "JSON",
		contentType: "application/json",
		data: JSON.stringify(json),
		cache: false,
		async: true,
		timeout: 600000,
		success: function(response) {
			if(response.dataList.length>0){
				$('.empty-table').hide();
			}
			else
				$('.empty-table').show();
			for ( var i = 0; i < response.dataList.length; i++){
				var data=response.dataList[i];
				var html="<tr><th scope='row'>"+ (i+1)+"</th><td>"+data.card+"</td><td>"+data.date+"</td><td>"+data.slotStart+"</td></tr>";
				$('.table > tbody:last-child').append(html);
			}

		},
		error : function(response){
			$('.empty-table').show();
		}
	});

}
function exportDataToCSV(){
	$('#export-btn').on('click', function(e) {
		e.preventDefault();
		$("#data-table").table2excel({
			name: "Results",
			filename: "bookinglist.xls"
		});
	});
}
