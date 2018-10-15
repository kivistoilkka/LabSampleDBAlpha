package tikape.dao;

import java.util.*;
import java.sql.*;
import tikape.labsampledatabase.*;

public class HostDao implements Dao<Host, String> {
    
    private Database database;

    public HostDao(Database database) {
        this.database = database;
    }

    @Override
    public Host findOne(String code) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT code, "
                    + "species, sex, age_group, capture_year, capture_site "
                    + "FROM Host WHERE code = ?");
            stmt.setString(1, code);
            ResultSet result = stmt.executeQuery();
            
            if (!result.next()) {
                return null;
            }
            
            Host host = new Host(result.getString("code"), result.getString("species"), 
                    result.getString("sex"), result.getString("age_group"), 
                    result.getInt("capture_year"), result.getString("capture_site"));
            return host;
        }
    }

    @Override
    public List<Host> findAll() throws SQLException {
        List<Host> hosts = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            ResultSet result = conn.prepareStatement("SELECT code, species, "
                    + "sex, age_group, capture_year, capture_site FROM Host").executeQuery();
            
            while (result.next()) {
                hosts.add(new Host(result.getString("code"), result.getString("species"), 
                    result.getString("sex"), result.getString("age_group"), 
                    result.getInt("capture_year"), result.getString("capture_site")));
            }
        }
        
        return hosts;
    }

    @Override
    public Host saveOrUpdate(Host object) throws SQLException {
        // supporting only saving, disallow saving if host with the same code exists
        Host foundHost = findOne(object.getCode());
        
        if (foundHost != null) {
            return foundHost;
        }
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Host (code, species, sex, age_group, capture_year, capture_site) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, object.getCode());
            stmt.setString(2, object.getSpecies());
            stmt.setString(3, object.getSex());
            stmt.setString(4, object.getAgeGroup());
            stmt.setInt(5, object.getCaptureYear());
            stmt.setString(6, object.getCaptureSite());
            
            stmt.executeUpdate();
        }
        
        return findOne(object.getCode());
    }

    @Override
    public void delete(String code) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Host WHERE code = ?");
        stmt.setString(1, code);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }    
    
}
