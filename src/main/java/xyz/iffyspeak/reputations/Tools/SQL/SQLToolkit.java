package xyz.iffyspeak.reputations.Tools.SQL;

import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLToolkit {

    public static void createTable(MySQL sql)
    {
        PreparedStatement ps;
        try {
            ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS reputations "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),REPUTATION INT,PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
    }

    public static void addPlayer(MySQL sql, String uuid, String name, int rep)
    {
        try {
            if (uuidExists(sql, uuid))
            {
                PreparedStatement ps = sql.getConnection().prepareStatement("INSERT IGNORE INTO reputations(NAME,UUID,REPUTATION) VALUES (?,?,?)");
                ps.setString(1, name);
                ps.setString(2, uuid);
                ps.setInt(3, rep);
                ps.executeUpdate();
            }

        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
    }

    public static void setPlayerRep(MySQL sql, String uuid, int rep)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE reputations SET REPUTATION=? WHERE UUID=?");
            ps.setInt(1, rep);
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
    }

    public static int getPlayerRep(MySQL sql, String uuid)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT REPUTATION FROM reputations WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            int rslives;

            if (rs.next())
            {
                rslives = rs.getInt("REPUTATION");
                return rslives;
            }
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
        return -1;
    }

    public static boolean uuidExists(MySQL sql, String uuid)
    {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM reputations WHERE UUID=?");
            ps.setString(1, uuid);

            ResultSet results = ps.executeQuery();
            // There is a player
            return !results.next();
            // No player
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
            //e.printStackTrace();
        }
        return true;
    }

}
