package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
        List<Moon> lm= new ArrayList<Moon>();
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from moons";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() )
            {
                Moon moon = new Moon();
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
                lm.add(moon);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lm;
	}

	public Moon getMoonByName(String username, String moonName) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select id, name, myplanetid from moons where name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, moonName);
            ResultSet rs = ps.executeQuery();
            Moon moon = new Moon();
            if ( rs.next() )
            {
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
            }
            return moon;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Moon();
        }
  
	}

	public Moon getMoonById(String username, int moonId) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select id, name, myplanetid from moons where moons.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            ResultSet rs = ps.executeQuery();
            Moon moon = new Moon();
            if ( rs.next() )
            {
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
            }
            return moon;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Moon();
        }
	}

	public Moon createMoon(String username, Moon m) {
        Moon moon= new Moon();
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "insert into moons values (default, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getName());
            ps.setInt(2, m.getMyPlanetId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if ( rs.next() )
            {
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return moon;
  
	}

	public void deleteMoonById(int moonId) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "delete from moons where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
        List<Moon> lm= new ArrayList<>();
        try(Connection connection= ConnectionUtil.createConnection()) {
            String sql= "select id, name, myplanetid from moons where myplanetid = ?";
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            ResultSet rs= ps.executeQuery();
            while ( rs.next() )
            {
                Moon moon= new Moon();
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
                lm.add(moon);
            }
            return lm;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
  
	}
}
