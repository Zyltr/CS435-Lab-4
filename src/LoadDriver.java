import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class LoadDriver
{
    Connection loadDriverUsingProperties ( Properties properties )
    {
        try
        {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName ( "com.mysql.cj.jdbc.Driver" ).newInstance ();

            // If 'serverTimezone' key is not present, then create.
            // Known Issues and Limitations
            // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-usagenotes-known-issues-limitations.html
            if ( ! properties.containsKey ( "serverTimezone" ) )
            {
                properties.setProperty ( "serverTimezone", "UTC" );
            }

            String hosts = properties.getProperty ( "hosts", "localhost" );
            String database = properties.getProperty ( "database", "" );

            try
            {
                return DriverManager.getConnection ( "jdbc:mysql://" + hosts + "/" + database + "?", properties );
            }
            catch ( SQLException ex )
            {
                System.out.println ( "SQLException: " + ex.getMessage () );
                System.out.println ( "SQLState: " + ex.getSQLState () );
                System.out.println ( "VendorError: " + ex.getErrorCode () );
            }
        }
        catch ( Exception exception )
        {
            exception.printStackTrace ();
        }

        return null;
    }
}