package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.BiggestExhibition;
import it.polito.tdp.artsmia.model.Exhibition;
import jdk.nashorn.internal.runtime.RewriteException;

public class ArtsmiaDAO {

	public List<ArtObject> listObject() {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> idListObjectForExhibition(Exhibition exhibition) {
		
		String sql = "SELECT object_id from exhibition_objects where exhibition_id = ?";

		List<Integer> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, exhibition.getId());

			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getInt("object_id"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Restitutisce tutte le mostre che iniziano lo stesso anno o gli anni seguenti dell'anno selezionato
	 * @param year
	 * @return
	 */
	public List<Exhibition> listExhitions(int year) {
		
		String sql = "SELECT * from exhibitions WHERE begin >= ?";

		List<Exhibition> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, year);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"),
						res.getString("exhibition_title"), res.getInt("begin"), res.getInt("end")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * Restitutisce tutti gli anni in cui si sono svolte delle mostre
	 * @return
	 */
	public List<Integer> listYearsOfExhitions() {
	
	String sql = "SELECT DISTINCT begin FROM exhibitions ORDER BY begin ASC";

	List<Integer> result = new ArrayList<>();

	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);

		ResultSet res = st.executeQuery();

		while (res.next()) {
			
			result.add(res.getInt("begin"));
		}

		conn.close();
		return result;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	}
	
	
	/**
	 * REstituisce la mostra con più opere esposte che è iniziata l'anno passato come parametro o nei seguenti
	 * @param exhibitionMap
	 * @param year
	 * @return
	 */
	public BiggestExhibition getBiggestExhitions(Map<Integer, Exhibition> exhibitionMap, int year) {
		
	String sql = "SELECT eo.exhibition_id, COUNT(eo.object_id) AS counter "
			+ "FROM exhibition_objects eo, exhibitions e "
			+ "WHERE eo.exhibition_id = e.exhibition_id AND e.begin >= ? "
			+ "GROUP BY eo.exhibition_id "
			+ "ORDER BY counter DESC";

	BiggestExhibition result;

	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, year);

		ResultSet res = st.executeQuery();

		if (res.next()) {
			Exhibition e = exhibitionMap.get(res.getInt("eo.exhibition_id"));
			if(e == null) {
				throw new RuntimeException("Non è stato possibile trovare la mostra");
			}
			result = new BiggestExhibition(e, res.getInt("counter"));
		} else{
			throw new RuntimeException("Non è stato possibile trovare la mostra");
		}

		conn.close();
		return result;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
}
	
}
