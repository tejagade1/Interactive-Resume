/**
 * js file for put.html
 */
$(document).ready(function() {
	
	var $put_example = $('#put_example')
		, $SET_SKILL_NAME = $('#SET_SKILL_NAME')
		, $SET_PROFICIENCY_LEVEL = $('#SET_PROFICIENCY_LEVEL');
	
	getInventory();
	
	$(document.body).on('click', ':button, .UPDATE_BTN', function(e) {
		//console.log(this);
		var $this = $(this)
			, PERSON_ID = $this.val()
			, $tr = $this.closest('tr')
			, SKILL_NAME = $tr.find('.CL_SKILL_NAME').text()
			, PROFICIENCY_LEVEL = $tr.find('.CL_PROFICIENCY_LEVEL').text();
		
		$('#SET_PERSON_ID').val(PERSON_ID);
		$SET_SKILL_NAME.text(SKILL_NAME);
		$SET_PROFICIENCY_LEVEL.text(PROFICIENCY_LEVEL);
		
		$('#update_response').text("");
	});
	
	$put_example.submit(function(e) {
		e.preventDefault(); //cancel form submit
		
		var obj = $put_example.serializeObject()
			, SKILL_NAME = $SET_SKILL_NAME.text()
			, PROFICIENCY_LEVEL = $SET_PROFICIENCY_LEVEL.text();
		
		updateInventory(obj, SKILL_NAME, PROFICIENCY_LEVEL);
	});
});

function updateInventory(obj, skill, level) {
	
	ajaxObj = {  
			type: "PUT",
			url: "http://localhost:7001/com.teja.myresume/api/v3/inventory/" + skill + "/" + level,
			data: JSON.stringify(obj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) {
				//console.log(data);
				$('#update_response').text( data[0].MSG );
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
				getInventory();
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

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
					"<tr> <th> Skill Name </th> <th> Level of Comfort</th> <th> </th></tr> " +
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
				'<td class="CL_SKILL_NAME">' + param.SKILL_NAME + '</td>' +
				'<td class="CL_PROFICIENCY_LEVEL">' + param.PROFICIENCY_LEVEL + '</td>' +
				'<td class="CL_SKILL_SET_BTN"> <button class="UPDATE_BTN" value=" ' + param.PERSON_ID + ' " type="button">Update</button> </td>' +
			'</tr>';
}

