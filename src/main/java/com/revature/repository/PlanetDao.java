package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
        List<Planet> lp= new ArrayList<Planet>();
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from planets";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() )
            {
                Planet planet = new Planet();
                planet.setId(rs.getInt(1));
                planet.setName(rs.getString(2));
                planet.setOwnerId(rs.getInt(3));
                lp.add(planet);
            }
            return lp;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
	}

	public Planet getPlanetByName(String owner, String planetName) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select id, name, ownerid from planets where name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, planetName);
            ResultSet rs = ps.executeQuery();
            Planet planet = new Planet();
            if ( rs.next() )
            {
                planet.setId(rs.getInt(1));
                planet.setName(rs.getString(2));
                planet.setOwnerId(rs.getInt(3));
            }
            return planet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Planet();
        }
	}

	public Planet getPlanetById(String username, int planetId) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select id, name, ownerid from planets where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            ResultSet rs = ps.executeQuery();
            Planet planet = new Planet();
            if ( rs.next() )
            {
                planet.setId(rs.getInt(1));
                planet.setName(rs.getString(2));
                planet.setOwnerId(rs.getInt(3));
            }
            return planet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Planet();
        }
	}

	public Planet createPlanet(String username, Planet p) {
        Planet planet= new Planet();
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "insert into planets values (default, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getOwnerId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if ( rs.next() )
            {
                planet.setId(rs.getInt(1));
                planet.setName(rs.getString(2));
                planet.setOwnerId(rs.getInt(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return planet;
	}

	public void deletePlanetById(int planetId) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "delete from planets where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}
