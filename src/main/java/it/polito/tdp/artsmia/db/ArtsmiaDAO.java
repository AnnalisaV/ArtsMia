package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenti;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public void listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		
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
				idMap.put(artObj.getId(),artObj); 
				}
				
			}
			
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}

	public int getPeso(ArtObject a, ArtObject aa) {
		String sql="SELECT COUNT(*) as peso FROM exhibition_objects as ex1 ,exhibition_objects as ex2 WHERE " + 
				"ex1.exhibition_id = ex2.exhibition_id AND ex1.object_id=?  and ex2.object_id=? "; 
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,  a.getId());
			st.setInt(2,  aa.getId());
			ResultSet res = st.executeQuery();
			
			//mi aspetto un solo risultato -> il count(*)
			if (res.next()) {
				int peso= res.getInt("peso"); 
				conn.close();
				return peso; 
			}
			
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		
		return -1; // per completezza e non far arrabbiare il compilatore
		
	}
	
	public List<Adiacenti> getAdiacenti(){
		String sql="SELECT ex1.object_id as obj1, ex2.object_id as obj2, COUNT(*) as peso " + 
				"FROM exhibition_objects as ex1, exhibition_objects as ex2 " + 
				" WHERE ex1.exhibition_id= ex2.exhibition_id  AND ex1.object_id > ex2.object_id " + 
				"GROUP BY ex1.object_id, ex2.object_id "; 
		
		List<Adiacenti> result= new ArrayList<Adiacenti>(); 
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
		
			while(res.next()) {
				result.add(new Adiacenti(res.getInt("obj1"), res.getInt("obj2"), res.getInt("peso"))); 
			}
			
			conn.close();
			return result; 
			
		} catch (SQLException e) {
			e.printStackTrace();
		return null; 
		}
	
	}
	
}
