package Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {
    //statement reference
    private static Statement statement;

    /**
     * Used to create a statement object. Used to delete customers but a preparedStatement is mostly used in this
     * application because it is more secure.
     * @param conn uses connection to set statement.
     * @throws SQLException possible database access error.
     */
    //Create Statement Object
    public static void setStatment(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /**
     * Returns statement object.
     * @return returns statment.
     */
    public static Statement getStatement() {
        return statement;
    }
}
