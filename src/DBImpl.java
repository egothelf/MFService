
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBImpl
{
    private Connection conn = null;
    private PreparedStatement preparedStmt = null;

    DBImpl()
    {

    }


    public void connect()
    {
        if (conn != null)
            return;

        // create a mysql database connection
        String myDriver = "org.gjt.mm.mysql.Driver";
        String myUrl = "jdbc:mysql://127.0.0.1:3306/fencingdb";
        try {
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, "root", "P0rsche1");
        }
        catch (ClassNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void closePStmt()
    {

        if (preparedStmt != null) {
            try {
                preparedStmt.close();
                preparedStmt = null;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void close()
    {

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public ArrayList<Event> getEvents(int fencerExternalId, boolean includePast, boolean includeCurrent, boolean includeFuture)
    {
        ArrayList<Event> events = new ArrayList<Event>();
        if (fencerExternalId == 0)
            return events;

        if (!includePast && !includeCurrent && !includeFuture)
            return events;

        java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

        String sql = "select attendees.fencerId, events.*, tournaments.tournamentName " +
                "from events, tournaments, attendees, fencers " +
                "where events.tournamentId = tournaments.Id " +
                "and attendees.eventId = events.Id " +
                "and attendees.fencerId=fencers.Id "+
                "and fencers.externalId=? ";

        String s = "";
        if (includePast && includeCurrent && includeFuture)
        {
            // Do Nothing
        }
        else if (includePast && includeCurrent)
        {
            s = "and events.date <= ?";
        }
        else if (includeCurrent && includeFuture)
        {
            s = "and events.date >= ?";
        }
        else if (includePast)
        {
            s = "and events.date < ?";
        }
        else if (includeCurrent)
        {
            s = "and events.date = ?";
        }
        else if (includeFuture)
        {
            s = "and events.date > ?";
        }

        String order = " ORDER BY events.date, events.time asc";

        try {
            // create the java statement
            preparedStmt = conn.prepareStatement(sql+s+order);
            preparedStmt.setInt(1, fencerExternalId);
            if (s.length() > 0)
                preparedStmt.setDate(2, now);
            ResultSet rs = preparedStmt.executeQuery();

            Event event;
            while (rs.next()) {
                event = new Event();
                event.setId(rs.getInt("id"));
                event.setExternalId(rs.getInt("externalId"));
                event.setTournamentId(rs.getInt("tournamentId"));
                event.setEventName(rs.getString("eventName"));
                event.setDate(rs.getDate("date"));
                event.setTime(rs.getTime("time"));
                event.setRating(rs.getString("rating"));
                event.setWeapon(rs.getString("weapon"));
                event.setSex(rs.getString("sex"));
                event.setClassification(rs.getString("classification"));
                event.setTournamentName(rs.getString("tournamentName"));
                events.add(event);
            }

            closePStmt();
            rs.close();
            return events;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            closePStmt();
            return events;
        }
    }

    public ArrayList<Fencer> getFencers(ArrayList<Integer> ids)
    {
        ArrayList<Fencer> fencers = new ArrayList<Fencer>();

        if (ids == null || ids.size() <= 0)
            return fencers;

        StringBuffer s = new StringBuffer();
        for (int i=0; i<ids.size(); i++)
        {
            if (ids.get(i) > 0) {
                if (s.length() > 0)
                    s.append(",");
                s.append(ids.get(i));
            }
        }
        String sql = "select * from fencers where externalId IN ("+s.toString()+")";  // Get highest ID
        System.out.print(s.toString());
        try {
            // create the java statement
            preparedStmt = conn.prepareStatement(sql);

            ResultSet rs = preparedStmt.executeQuery();

            Fencer fencer;
            while (rs.next()) {
                fencer = new Fencer();
                fencer.setId(rs.getInt("id"));
                fencer.setFirstName(rs.getString("firstName"));
                fencer.setLastName(rs.getString("lastName"));
                fencer.setRating(rs.getString("rating"));
                fencer.setRating2(rs.getString("rating2"));
                fencer.setRating3(rs.getString("rating3"));
                fencer.setClubPrimary(rs.getString("club"));
                fencer.setWeapon(rs.getString("weapon"));
                fencer.setWeapon2(rs.getString("weapon2"));
                fencer.setWeapon3(rs.getString("weapon3"));
                fencer.setExternalId(rs.getInt("externalId"));
                fencers.add(fencer);
            }

            closePStmt();
            rs.close();
            return fencers;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            closePStmt();
            return fencers;
        }
    }

    public ArrayList<Fencer> getFencers(int externalId)
    {
        ArrayList<Fencer> fencers = new ArrayList<Fencer>();

        String sql = "select * from fencers where externalId=?";  // Get highest ID

        try {
            // create the java statement
            preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setInt(1, externalId);

            ResultSet rs = preparedStmt.executeQuery();

            Fencer fencer;
            while (rs.next()) {
                fencer = new Fencer();
                fencer.setId(rs.getInt("id"));
                fencer.setFirstName(rs.getString("firstName"));
                fencer.setLastName(rs.getString("lastName"));
                fencer.setRating(rs.getString("rating"));
                fencer.setRating2(rs.getString("rating2"));
                fencer.setRating3(rs.getString("rating3"));
                fencer.setClubPrimary(rs.getString("club"));
                fencer.setWeapon(rs.getString("weapon"));
                fencer.setWeapon2(rs.getString("weapon2"));
                fencer.setWeapon3(rs.getString("weapon3"));
                fencer.setExternalId(rs.getInt("externalId"));
                fencers.add(fencer);
            }

            closePStmt();
            rs.close();
            return fencers;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            closePStmt();
            return fencers;
        }
    }

    public ArrayList<Fencer> getFencers(String search)
    {
        ArrayList<Fencer> fencers = new ArrayList<Fencer>();
        String s;
        if (search == null)
            return fencers;

        s = "%" + search + "%";

        String sql = "select * from fencers where CONCAT(firstName, ' ', lastName) like ? ORDER BY externalId DESC";  // Get highest ID

        try {
            // create the java statement
            preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, s);

            ResultSet rs = preparedStmt.executeQuery();

            Fencer fencer;
            while (rs.next()) {
                fencer = new Fencer();
                fencer.setId(rs.getInt("id"));
                fencer.setFirstName(rs.getString("firstName"));
                fencer.setLastName(rs.getString("lastName"));
                fencer.setRating(rs.getString("rating"));
                fencer.setRating2(rs.getString("rating2"));
                fencer.setRating3(rs.getString("rating3"));
                fencer.setClubPrimary(rs.getString("club"));
                fencer.setWeapon(rs.getString("weapon"));
                fencer.setWeapon2(rs.getString("weapon2"));
                fencer.setWeapon3(rs.getString("weapon3"));
                fencer.setExternalId(rs.getInt("externalId"));
                fencers.add(fencer);
            }

            closePStmt();
            rs.close();
            return fencers;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            closePStmt();
            return fencers;
        }
    }
}
