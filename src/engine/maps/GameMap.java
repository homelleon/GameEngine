package engine.maps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.audio.Source;
import engine.entities.Entity;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class GameMap {
	
	private String name;
	private List<Entity> entities;
	private List<Entity> normalEntities;
	private List<Terrain> terrains;
	private List<Source> audios;
	
	public GameMap(String name){
		this.name = name;
	}	
	
	public String getName() {
		return name;
	}
		
	

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public List<Entity> getNormalEntities() {
		return normalEntities;
	}

	public void setNormalEntities(List<Entity> normalEntities) {
		this.normalEntities = normalEntities;
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}

	public void setTerrains(List<Terrain> terrains) {
		this.terrains = terrains;
	}

	public List<Source> getAudios() {
		return audios;
	}

	public void setAudios(List<Source> audios) {
		this.audios = audios;
	}

}
