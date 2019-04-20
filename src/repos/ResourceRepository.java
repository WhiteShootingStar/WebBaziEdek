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

	public List<Integer> getResourceIds(int id) {
		List<Integer> resourceIDs = new ArrayList<>();
		final String query = "select CNT from CON_USE where US = ?";
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				resourceIDs.add(set.getInt("CNT"));
			}
			getConnection().close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resourceIDs;
	}

	public List<Resource> getResoursesForId(int id) {
		Connection conn = null;
		List<Resource> resources = new ArrayList<>();
		final String Query = "select * from CT where IDC = ?";
		try {
			conn = getConnection();
			PreparedStatement statement = getConnection().prepareStatement(Query);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				resources.add(new Resource(set.getInt("IDC"), set.getString("RNAME"), set.getString("CONT")));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resources;
	}

	
	public String getResDescrById(int id) {
		Connection conn = null;
		
		final String Query = "select Cont from CT where IDC = ?";
		try {
			conn = getConnection();
			PreparedStatement statement = getConnection().prepareStatement(Query);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				return set.getString("CONT");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private Connection getConnection() throws SQLException {
		if (source == null) {
			System.out.println("Null source");
		}
		return source.getConnection();
	}

	public List<Resource> getList(int id) {

		List<Integer> ids = getResourceIds(id);
		List<Resource> list = new ArrayList<>();
		ids.stream().map(e -> getResoursesForId(e)).sequential().forEach(e -> list.addAll(e));
		return list;
	}
}
