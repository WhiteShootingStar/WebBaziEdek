package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ResourceRepository {
	private DataSource source;

	public ResourceRepository(DataSource source) {
		this.source = source;
	}

	public List<Integer> getResourceIds(int id){
		 List<Integer> resourceIDs = new ArrayList<>();
		final String query = "select CNT from CON_USE where US = ?";
		try{
			PreparedStatement statement = getConnection().prepareStatement(query);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			while(set.next()) {
				resourceIDs.add(set.getInt("CNT"));
			}
			getConnection().close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return resourceIDs;
	}
	public  List<Resource> getResoursesForId(int id ){
		List<Resource> resources = new ArrayList<>();
		final String Query = "select * from CT where IDC = ?";
		try {
			PreparedStatement statement = getConnection().prepareStatement(Query);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			while(set.next()) {
				resources.add(new Resource(set.getInt("IDC"), set.getString("RNAME"), set.getString("CONT")));
			}
			getConnection().close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return resources;
	}

	private Connection getConnection() throws SQLException {
		return source.getConnection();
	}
}
