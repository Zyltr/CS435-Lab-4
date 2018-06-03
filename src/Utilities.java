import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Utilities
{
    static SimpleDateFormat sqlDateFormat = new SimpleDateFormat ( "yyyy-MM-dd" );
    static SimpleDateFormat timeFormat = new SimpleDateFormat ( "h:mm a" );
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "EEE, MMM d, yyyy" );

    static boolean isDateInCurrentWeek ( final Date date, final Date referenceDate )
    {
        Calendar currentCalendar = Calendar.getInstance ();
        currentCalendar.setTime ( referenceDate );
        int week = currentCalendar.get ( Calendar.WEEK_OF_YEAR );
        int year = currentCalendar.get ( Calendar.YEAR );

        Calendar targetCalendar = Calendar.getInstance ();
        targetCalendar.setTime ( date );
        int targetWeek = targetCalendar.get ( Calendar.WEEK_OF_YEAR );
        int targetYear = targetCalendar.get ( Calendar.YEAR );

        return week == targetWeek && year == targetYear;
    }
}

