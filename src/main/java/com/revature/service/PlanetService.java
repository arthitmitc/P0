package com.revature.service;

import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(){
		this.dao = new PlanetDao();
	}

	public List<Planet> getAllPlanets() {
  return dao.getAllPlanets();
	}

	public Planet getPlanetByName(String owner, String planetName) {
		return dao.getPlanetByName(owner, planetName);
	}

	public Planet getPlanetById(String username, int planetId) {
		return dao.getPlanetById(username, planetId);
	}

	public Planet createPlanet(String username, Planet p) {
		return dao.createPlanet(username, p);
	}

	public void deletePlanetById(int planetId) {
  		dao.deletePlanetById(planetId);
	}
}
