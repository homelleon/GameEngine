package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioSourceInterface;
import renderEngine.Loader;
import scene.ES;
import terrains.TerrainInterface;
import toolbox.EngineUtils;

public class MapsXMLParser implements MapsParser {

	@Override
	public GameMap readMap(String fileName, BufferedReader reader, Loader loader) {
        String line;
        
        System.out.println("Start loading map '" + fileName + "'...");
        /* entities */
		List<String> eNames = new ArrayList<String>();
        List<String> eModels = new ArrayList<String>();
        List<String> eTextures = new ArrayList<String>();
        List<Vector3f> eCoords = new ArrayList<Vector3f>();
        List<Float> eScales = new ArrayList<Float>();  
        List<Boolean> eTypes = new ArrayList<Boolean>();
        List<String> eNormTexts = new ArrayList<String>();
        List<String> eSpecMaps = new ArrayList<String>();
        List<Float> eShineDumpers = new ArrayList<Float>();
        List<Float> eReflectivities = new ArrayList<Float>();
        
        /* terrains */
        List<String> tNames = new ArrayList<String>();
        List<Vector2f> tCoords = new ArrayList<Vector2f>();
        List<String> tBaseTexs = new ArrayList<String>();
        List<String> trTexs = new ArrayList<String>();
        List<String> tgTexs = new ArrayList<String>();
        List<String> tbTexs = new ArrayList<String>();
        List<String> tBlends = new ArrayList<String>();
        List<Boolean> tProcGens = new ArrayList<Boolean>();
        List<String> tHeights = new ArrayList<String>();
        List<Float> tAmplitudes = new ArrayList<Float>();
        List<Integer> tOctaves = new ArrayList<Integer>();
        List<Float> tRoughnesses = new ArrayList<Float>();
        
        /* audio sources */
        List<String> aNames = new ArrayList<String>();
        List<String> aPaths = new ArrayList<String>();
        List<Vector3f> aCoords = new ArrayList<Vector3f>();
        List<Integer> aMaxDistances = new ArrayList<Integer>();
        
        /* particle systems */
        List<String> pNames = new ArrayList<String>();
        List<String> pTexs = new ArrayList<String>();
        List<Integer> pDims = new ArrayList<Integer>();
        List<Boolean> pAdds = new ArrayList<Boolean>();
        List<Float> pPpss = new ArrayList<Float>();
        List<Float> pSpeeds = new ArrayList<Float>();
        List<Float> pGravities = new ArrayList<Float>();
        List<Float> pLifes = new ArrayList<Float>();
        List<Float> pScales = new ArrayList<Float>();
        

		List<String> entitiesXML = new ArrayList<String>();
		List<String> terrainsXML = new ArrayList<String>();
        try {        	
	        while ((line = reader.readLine()) != null) {        	
	        	if(line.startsWith(ES.XML_ENTITIES_BEGIN)) {  //Read entities      			        		
	        		while(!((line = reader.readLine())
	        				.startsWith(ES.XML_ENTITIES_END))) {
	        			if (line.startsWith(ES.XML_ENTITY_BEGIN) &&
			        			line.endsWith(ES.XML_ENTITY_END)) {
	        				entitiesXML.add(EngineUtils.pullLineFromWords(
			        					line, ES.XML_ENTITY_BEGIN, ES.XML_ENTITY_END
	    		    				));
	        			}
	        		}
	        	} else if (line.startsWith(ES.XML_TERRAINS_BEGIN)) { //Read terrains	        		        			        		
	        		while(!((line = reader.readLine())
	        				.startsWith(ES.XML_TERRAINS_END))) {
	        			if (line.startsWith(ES.XML_TERRAIN_BEGIN) &&
			        			line.endsWith(ES.XML_TERRAIN_END)) {
	        				terrainsXML.add(EngineUtils.pullLineFromWords(
			        				line, ES.XML_TERRAIN_BEGIN, ES.XML_TERRAIN_END
			    				));
	        			}
	        		}
	        	} else if (line.startsWith(ES.XML_AUDIOS_BEGIN)) {
	        		
	        	} else if(line.startsWith(ES.XML_PARTICLES_BEGIN)) {
	        		
	        	}
	        	
	        	if(!entitiesXML.isEmpty()) { //read entities values
		        	for(String entity : entitiesXML) {
		                String[] value = entity.split(ES.XML_SEPARATOR);
		        
		                eNames.add(String.valueOf(value[0]));
		                eModels.add(String.valueOf(value[1]));
		                eTextures.add(String.valueOf(value[2]));
		                eCoords.add(new Vector3f((float) Float.valueOf(value[3]),
		                        (float) Float.valueOf(value[4]),
		                        (float) Float.valueOf(value[5])));
		                eScales.add(Float.valueOf(value[6]));
		                eTypes.add(Boolean.valueOf(value[7]));
		                if(Boolean.valueOf(value[7])) {
		                	eNormTexts.add(String.valueOf(value[8]));
		                	eSpecMaps.add(String.valueOf(value[9]));
		                	eShineDumpers.add(Float.valueOf(value[10]));
		                	eReflectivities.add(Float.valueOf(value[11]));
		                } else {
		                	eNormTexts.add("");
		                	eSpecMaps.add("");
		                	eShineDumpers.add(0.0f);
		                	eReflectivities.add(0.0f);
		                }
		        	}
		        	entitiesXML.clear();
	        	}        	
	        	
	        	if(!terrainsXML.isEmpty()) { //read terrains values
		        	for(String terrain : terrainsXML) {
		        		String[] value = terrain.split(ES.XML_SEPARATOR);
			            
	                    tNames.add(String.valueOf(value[0]));
	                    tCoords.add(new Vector2f((int) Integer.valueOf(value[1]),
	                    		(int) Integer.valueOf(value[2])));
	                    tBaseTexs.add(String.valueOf(value[3]));
	                    trTexs.add(String.valueOf(value[4]));
	                    tgTexs.add(String.valueOf(value[5]));
	                    tbTexs.add(String.valueOf(value[6])); 
	                    tBlends.add(String.valueOf(value[7]));
	                    tProcGens.add(Boolean.valueOf(value[8]));
	                    if(Boolean.valueOf(value[8])){
	                    	tAmplitudes.add(Float.valueOf(value[9]));
	                    	tOctaves.add(Integer.valueOf(value[10]));
	                    	tRoughnesses.add(Float.valueOf(value[11]));
	                    } else {
	                    	tHeights.add(String.valueOf(value[9]));
	                    }
		        	}
	        	}
	        	terrainsXML.clear();
	        }
	        
	        reader.close();	  
	        
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }        
                
        //*Create terrains*//
        System.out.println("Loading terrains...");
        List<TerrainInterface> terrains = new ArrayList<TerrainInterface>();        
        for(int i=0;i<tNames.size();i++) {
        	System.out.println(tNames.get(i));
        	if (tProcGens.get(i)) {
        		TerrainInterface terrain = EngineUtils.createMultiTexTerrain(tNames.get(i), (int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i), tAmplitudes.get(i), tOctaves.get(i), 
	        			tRoughnesses.get(i), loader);
	        	terrains.add(terrain);

        	} else {
        		TerrainInterface terrain = EngineUtils.createMultiTexTerrain(tNames.get(i),  (int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i),tHeights.get(i), loader);
        		terrains.add(terrain);
        	}
        }
        System.out.println("Succed!");
        
        //*Create audios*//
        List<AudioSourceInterface> audios = new ArrayList<AudioSourceInterface>();    
        
        for(int i=0;i<audios.size();i++) {
        	//AudioSource source = new AudioSource(aNames.get(i), aPaths.get(i), aMaxDistances.get(i), aCoords.get(i));
        	//audios.add(source);
        }
        
		GameMap map = new GameMap(fileName, loader);
		
		/* create entities */
		System.out.println("Loading entities...");
		for(int i=0;i<eNames.size();i++) {
			System.out.println(eNames.get(i));
			if(eTypes.get(i)) {
				map.createEntity(eNames.get(i), eModels.get(i), 
						eTextures.get(i), eNormTexts.get(i), eSpecMaps.get(i),
						eCoords.get(i), 0, 0, 0, eScales.get(i), 
						eShineDumpers.get(i), eReflectivities.get(i));
			} else {
				map.createEntity(eNames.get(i), eModels.get(i), 
						eTextures.get(i), eCoords.get(i), 0, 0, 0, eScales.get(i));
			}
        }
		
		System.out.println("Succed!");
		
		/* create particle systems */
		for(int i=0;i<pNames.size();i++) { 
			map.createParticles(pNames.get(i), pTexs.get(i), pDims.get(i), pAdds.get(i), pPpss.get(i), pSpeeds.get(i), pGravities.get(i), pLifes.get(i), pScales.get(i));
		}
		
		//Clear
		
		/* entities */
		eNames.clear();
        eModels.clear();
        eTextures.clear();
        eCoords.clear();
        eScales.clear(); 
        eTypes.clear();
        eNormTexts.clear();
        eShineDumpers.clear();
        eReflectivities.clear();
        
        /* terrains */
        tNames.clear();
        tCoords.clear();
        tBaseTexs.clear();
        trTexs.clear();
        tgTexs.clear();
        tbTexs.clear();
        tBlends.clear();
        tProcGens.clear();
        tHeights.clear();
        tAmplitudes.clear();
        tOctaves.clear();
        tRoughnesses.clear();
        
        /* audio sources */
        aNames.clear();
        aPaths.clear();
        aCoords.clear();
        aMaxDistances.clear();
        
        /* particle systems */
        pNames.clear();
        pTexs.clear();
        pDims.clear();
        pAdds.clear();
        pPpss.clear();
        pSpeeds.clear();
        pGravities.clear();
        pLifes.clear();
        pScales.clear();
		
		map.setTerrains(terrains);
		map.setAudioSources(audios);
		
		System.out.println("Loading complete!");
		
		return map;
	}

}
