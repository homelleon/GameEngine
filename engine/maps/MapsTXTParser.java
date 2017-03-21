package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioSource;
import renderEngine.Loader;
import scene.ES;
import terrains.Terrain;
import toolbox.ObjectUtils;

public class MapsTXTParser implements MapsParser {

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
        
        try {        	
	        while (true) {	      
				line = reader.readLine(); 
				
				/*Read entities*/	       
	        	if (line.startsWith("<e> ")) {
                    String[] currentLine = line.split(" ");
            
                    eNames.add(String.valueOf(currentLine[1]));
                    eModels.add(String.valueOf(currentLine[2]));
                    eTextures.add(String.valueOf(currentLine[3]));
                    eCoords.add(new Vector3f((float) Float.valueOf(currentLine[4]),
                            (float) Float.valueOf(currentLine[5]),
                            (float) Float.valueOf(currentLine[6])));
                    eScales.add(Float.valueOf(currentLine[7]));
                    eTypes.add(Boolean.valueOf(currentLine[8]));
                    if(Boolean.valueOf(currentLine[8])) {
                    	eNormTexts.add(String.valueOf(currentLine[9]));
                    	eSpecMaps.add(String.valueOf(currentLine[10]));
                    	eShineDumpers.add(Float.valueOf(currentLine[11]));
                    	eReflectivities.add(Float.valueOf(currentLine[12]));
                    } else {
                    	eNormTexts.add("");
                    	eSpecMaps.add("");
                    	eShineDumpers.add(0.0f);
                    	eReflectivities.add(0.0f);
                    }
	        	}
	        	
	        	/*Read normal mapped entities*/
	        	//TODO:Implement reader
	        	
	        	/*Read terrains*/
	        	if (line.startsWith("<t> ")) {
                    String[] currentLine = line.split(" ");
            
                    tNames.add(String.valueOf(currentLine[1]));
                    tCoords.add(new Vector2f((int) Integer.valueOf(currentLine[2]),
                    		(int) Integer.valueOf(currentLine[3])));
                    tBaseTexs.add(String.valueOf(currentLine[4]));
                    trTexs.add(String.valueOf(currentLine[5]));
                    tgTexs.add(String.valueOf(currentLine[6]));
                    tbTexs.add(String.valueOf(currentLine[7])); 
                    tBlends.add(String.valueOf(currentLine[8]));
                    tProcGens.add(Boolean.valueOf(currentLine[9]));
                    if(Boolean.valueOf(currentLine[9])){
                    	tAmplitudes.add(Float.valueOf(currentLine[10]));
                    	tOctaves.add(Integer.valueOf(currentLine[11]));
                    	tRoughnesses.add(Float.valueOf(currentLine[12]));
                    } else {
                    	tHeights.add(String.valueOf(currentLine[10]));
                    } 
	        	}
	        	
	        	/*Read audio loops*/
	        	if (line.startsWith("<a> ")) {
	        		String[] currentLine = line.split(" ");
	        		aNames.add(String.valueOf(currentLine[1]));
	        		aPaths.add(String.valueOf(currentLine[2]));
	        		aCoords.add(new Vector3f((Float) Float.valueOf(currentLine[3]),
	        				 (Float) Float.valueOf(currentLine[4]),
	        				 (Float) Float.valueOf(currentLine[5])));
	        		aMaxDistances.add((int)Integer.valueOf(currentLine[6]));	 
	        	}
	        	
	        	/*Read water planes*/
	        	//TODO:Implement reader
	        	
	        	/*Read particle systems*/
	        	if (line.startsWith("<p> ")) {
	        		String[] currentLine = line.split(" ");
	        		pNames.add(String.valueOf(currentLine[1]));
	        		pTexs.add(String.valueOf(currentLine[2]));	                  
	                pAdds.add((boolean) Boolean.valueOf(currentLine[3]));
	                pPpss.add((float) Float.valueOf(currentLine[4]));
	                pSpeeds.add((float) Float.valueOf(currentLine[5]));
	                pGravities.add((float) Float.valueOf(currentLine[6]));
	                pLifes.add((float) Float.valueOf(currentLine[7]));
	                pScales.add((float) Float.valueOf(currentLine[8]));	 
	        	}
	        	
	        	if (line.startsWith("<end>")) {
	        		break;
	        	}	
	        }	      
	        
	        reader.close();	  
	        
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }        
                
        //*Create terrains*//
        System.out.println("Loading terrains...");
        List<Terrain> terrains = new ArrayList<Terrain>();        
        for(int i=0;i<tNames.size();i++) {
        	System.out.println(tNames.get(i));
        	if (tProcGens.get(i)) {
        		Terrain terrain = ObjectUtils.createMultiTexTerrain(tNames.get(i), (int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i), tAmplitudes.get(i), tOctaves.get(i), 
	        			tRoughnesses.get(i), loader);
	        	terrains.add(terrain);

        	} else {
        		Terrain terrain = ObjectUtils.createMultiTexTerrain(tNames.get(i),  (int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i),tHeights.get(i), loader);
        		terrains.add(terrain);
        	}
        }
        System.out.println("Succed!");
        
        //*Create audios*//
        List<AudioSource> audios = new ArrayList<AudioSource>();    
        
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
