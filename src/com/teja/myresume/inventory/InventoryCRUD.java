package com.teja.myresume.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.teja.dao.DatabaseSchema;

@Path("/v3/inventory")
public class InventoryCRUD {
	
	/**
	 * This method will return all the skills that are listed
	 * PERSON_SKILL_SET table.
	 * 
	 * TODO: Need to add logic to retrieve skill set based on an individual person Id 
	 * 
	 * @return - JSON array string
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSkillDetails() throws Exception {
		
		String returnString = null;
		Response rb = null;	
		JSONArray json = new JSONArray();
		
		try {
			
			DatabaseSchema dao = new DatabaseSchema();
			
			json = dao.getSkillSets();
			returnString = json.toString();
			
			rb = Response.ok(returnString).build();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return rb;
	}

	/**
	 * This method will allow to insert data the PERSON_SKILL_SET table.
	 *
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewSkill(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		DatabaseSchema dao = new DatabaseSchema();
		jsonObject.put("MSG", "In the response");
		
		try {
			
			JSONObject skillData = new JSONObject(incomingData);
			System.out.println( "jsonData: " + skillData.toString() );
			
			int http_code = dao.insertSkillSet(skillData.optString("SPECIFICATION"), 
														skillData.optString("SKILL_NAME"), 
														skillData.optString("PROFICIENCY_LEVEL"));
			
			if( http_code == 200 ) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been entered successfully, Version 3");
				returnString = jsonArray.put(jsonObject).toString();
			} else {
				return Response.status(500).entity("Unable to enter Item").build();
			}
			
			System.out.println( "returnString: " + returnString );
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/**
	 * This method will allow you to update data in the PERSON_SKILL_SET table.
	 * 
	 * @param skill
	 * @param prof_level
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{skill}/{prof_level}")
	@PUT
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@PathParam("skill") String skill,
									@PathParam("prof_level") int prof_level,
									String incomingData) 
								throws Exception {
		
		int pk;
		int profLevel;
		String skillSetName;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		DatabaseSchema dao = new DatabaseSchema();
		
		try {
			
			JSONObject skillData = new JSONObject(incomingData); 
			pk = 12345;
			skillSetName = skill;
			profLevel = skillData.optInt("PROFICIENCY_LEVEL", 0);
			
			http_code = dao.updateSkillSet(pk, skillSetName, profLevel);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been updated successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}