/**
 * js file for technical information in index.html
 */
$(document).ready(function() {
	
	getInventory();
	
});

function getInventory() {
	
	var d = new Date()
		, n = d.getTime();
	
	ajaxObj = {  
			type: "GET",
			url: "http://localhost:7001/com.teja.myresume/api/v3/inventory", 
			data: "ts="+n, 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) { 
				//console.log(data);
				var html_string = "";
				
				$.each(data, function(index1, val1) {
					//console.log(val1);
					html_string = html_string + templateGetInventory(val1);
				});
				
				$('#get_inventory').html("<table border='1' class='languages'>" + 
					"<tr> <th> Specification </th> <th> Skill Name </th> <th> Level of Comfort</th> </tr> " +
					html_string + "</table>");
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function templateGetInventory(param) {
	return '<tr>' +
				'<td class="CL_SPECIFICATION">' + param.SPECIFICATION + '</td>' +
				'<td class="CL_SKILL_NAME">' + param.SKILL_NAME + '</td>' +
				'<td class="CL_PROFICIENCY_LEVEL">' + param.PROFICIENCY_LEVEL + '</td>' +
			'</tr>';
}

