package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserRepositiry {
	private DataSource source;

	public UserRepositiry(DataSource source) {
		this.source = source;
	}

	public User verifyUser(String login, String password) {
		User user =null;
		final String query =" select * from USR where UNAME= ? and PASSWORD =?";
		try {
			PreparedStatement statement = getConnection().prepareStatement(query);
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet set = statement.executeQuery();
			if(set.next()) {
				user = new User(set.getInt("IDU"), set.getString("UNAME"), set.getString("PASSWORD"));
			}
			getConnection().close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	private Connection getConnection() throws SQLException {
		return source.getConnection();
	}
}
