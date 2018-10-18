package tikape.dao;

import java.util.*;
import java.sql.*;
import tikape.labsampledatabase.*;

public class SampleDao implements Dao<Sample, String> {

    private Database database;

    public SampleDao(Database database) {
        this.database = database;
    }

    @Override
    public Sample findOne(String code) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT code, "
                    + "host_code, organ, dna_isolated, rna_isolated "
                    + "FROM Sample WHERE code = ?");
            stmt.setString(1, code);
            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return null;
            }

            Sample sample = new Sample(result.getString("code"), result.getString("host_code"),
                    result.getString("organ"), result.getBoolean("dna_isolated"),
                    result.getBoolean("rna_isolated"));
            return sample;
        }
    }

    @Override
    public List<Sample> findAll() throws SQLException {
        List<Sample> samples = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            ResultSet result = conn.prepareStatement("SELECT code, host_code, "
                    + "organ, dna_isolated, rna_isolated FROM Sample").executeQuery();

            while (result.next()) {
                samples.add(new Sample(result.getString("code"), result.getString("host_code"),
                        result.getString("organ"), result.getBoolean("dna_isolated"),
                        result.getBoolean("rna_isolated")));
            }
        }

        return samples;
    }

    @Override
    public Sample saveOrUpdate(Sample object) throws SQLException {
        // supporting only saving, disallow saving if host with the same code exists
        Sample foundSample = findOne(object.getCode());

        if (foundSample != null) {
            return foundSample;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Sample (code, host_code, organ, dna_isolated, rna_isolated) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, object.getCode());
            stmt.setString(2, object.getHostCode());
            stmt.setString(3, object.getOrgan());
            stmt.setBoolean(4, object.getDnaIsolated());
            stmt.setBoolean(5, object.getRnaIsolated());

            stmt.executeUpdate();
        }

        return findOne(object.getCode());
    }

    @Override
    public void delete(String code) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Sample WHERE code = ?");
        stmt.setString(1, code);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Sample> findAllForHost(String hostCode) throws SQLException {
        List<Sample> samples = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT code, host_code, "
                    + "organ, dna_isolated, rna_isolated FROM Sample WHERE host_code = ?");
            stmt.setString(1, hostCode);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                samples.add(new Sample(result.getString("code"), result.getString("host_code"),
                        result.getString("organ"), result.getBoolean("dna_isolated"),
                        result.getBoolean("rna_isolated")));
            }
        }

        return samples;
    }

}
