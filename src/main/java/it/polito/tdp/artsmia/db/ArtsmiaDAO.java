package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.CoppiaArtObjects;

public class ArtsmiaDAO {

	public void listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		//List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("object_id"))) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				//result.add(artObj);
				idMap.put(artObj.getId(), artObj); 
				}
			}
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//return null;
		}
	}
	
	/**
	 * Ottenere le coppie di {@code ArtObject} che sono state esibite contemporaneamente
	 */
	public List<CoppiaArtObjects> getArtObjectsContemporanei(Map<Integer, ArtObject> idMap){
		String sql="SELECT ob1.object_id, ob2.object_id, COUNT(ob1.exhibition_id) AS esib " + 
				"FROM exhibition_objects ob1, exhibition_objects ob2 " + 
				"WHERE ob1.exhibition_id=ob2.exhibition_id AND " + 
				"ob1.object_id > ob2.object_id " + 
				"GROUP BY ob1.object_id, ob2.object_id "; 
		List<CoppiaArtObjects> result= new ArrayList<>(); 
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				ArtObject a1= idMap.get(res.getInt("ob1.object_id")); 
				ArtObject a2= idMap.get(res.getInt("ob2.object_id")); 
				
				CoppiaArtObjects c= new CoppiaArtObjects(a1, a2, res.getInt("esib"));
				result.add(c); 
				}
			
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return result; 
	}
	
}
