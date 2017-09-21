package tool.meshLoader.objloader;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import object.model.Mesh;
import tool.math.Quaternion;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class Util {

	public static String [] removeEmptyStrings(String[] data)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < data.length; i++)
			if(!data[i].equals(""))
				result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] toIntArray(Integer[] data)
	{
		int[] result = new int[data.length];
		
		for(int i=0; i < data.length; i++)
			result[i] = data[i].intValue();
		
		return result;
	}
	
	public static Vertex[] toVertexArray(FloatBuffer data)
	{
		Vertex[] vertices = new Vertex[data.limit() / Vertex.FLOATS];
		
		for(int i=0; i<vertices.length; i++)
		{
			vertices[i] = new Vertex();
			vertices[i].setPos(new Vector3f(data.get(),data.get(),data.get()));
			vertices[i].setTextureCoord(new Vector2f(data.get(),data.get()));
			vertices[i].setNormal(new Vector3f(data.get(),data.get(),data.get()));
		}
		
		return vertices;
	}
	
	public static Vertex[] toVertexArray(ArrayList<Vertex> data)
	{
		Vertex[] vertices = new Vertex[data.size()];
		
		for(int i=0; i<vertices.length; i++)
		{
			vertices[i] = new Vertex();
			vertices[i].setPos(data.get(i).getPos());
			vertices[i].setTextureCoord(data.get(i).getTextureCoord());
			vertices[i].setNormal(data.get(i).getNormal());
		}
		
		return vertices;
	}
	
	public static void generateNormalsCW(Vertex[] vertices, int[] indices)
	{
	    for ( int i = 0; i < indices.length; i += 3 )
	    {
	    	Vector3f v0 = vertices[indices[i    ]].getPos();
	    	Vector3f v1 = vertices[indices[i + 1]].getPos();
	    	Vector3f v2 = vertices[indices[i + 2]].getPos();
	        
	    	Vector3f normal = v1.sub(v0).cross(v2.sub(v0)).normalize();
	        
	        vertices[indices[i	  ]].setNormal(vertices[indices[i    ]].getNormal().add(normal));
	        vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().add(normal));
	        vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.length; ++i )
	    {	
	    	vertices[i].setNormal(vertices[i].getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCCW(Vertex[] vertices, int[] indices)
	{
	    for ( int i = 0; i < indices.length; i += 3 )
	    {
	    	Vector3f v0 = vertices[indices[i    ]].getPos();
	    	Vector3f v1 = vertices[indices[i + 1]].getPos();
	    	Vector3f v2 = vertices[indices[i + 2]].getPos();
	        
	    	Vector3f normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
	        
	        vertices[indices[i	  ]].setNormal(vertices[indices[i    ]].getNormal().add(normal));
	        vertices[indices[i + 1]].setNormal(vertices[indices[i + 1]].getNormal().add(normal));
	        vertices[indices[i + 2]].setNormal(vertices[indices[i + 2]].getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.length; ++i )
	    {	
	    	vertices[i].setNormal(vertices[i].getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCW(ArrayList<Vertex> vertices, ArrayList<Integer> indices)
	{
	    for ( int i = 0; i < indices.size(); i += 3 )
	    {
	    	Vector3f v0 = vertices.get(indices.get(i)).getPos();
	    	Vector3f v1 = vertices.get(indices.get(i+1)).getPos();
	    	Vector3f v2 = vertices.get(indices.get(i+2)).getPos();
	        
	    	Vector3f normal = v1.sub(v0).cross(v2.sub(v0)).normalize();
	        
	        vertices.get(indices.get(i)).setNormal(vertices.get(indices.get(i)).getNormal().add(normal));
	        vertices.get(indices.get(i+1)).setNormal(vertices.get(indices.get(i+1)).getNormal().add(normal));
	        vertices.get(indices.get(i+2)).setNormal(vertices.get(indices.get(i+2)).getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.size(); ++i )
	    {	
	    	vertices.get(i).setNormal(vertices.get(i).getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCCW(ArrayList<Vertex> vertices, ArrayList<Integer> indices)
	{
	    for ( int i = 0; i < indices.size(); i += 3 )
	    {
	    	Vector3f v0 = vertices.get(indices.get(i)).getPos();
	    	Vector3f v1 = vertices.get(indices.get(i+1)).getPos();
	    	Vector3f v2 = vertices.get(indices.get(i+2)).getPos();
	        
	    	Vector3f normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
	        
	        vertices.get(indices.get(i)).setNormal(vertices.get(indices.get(i)).getNormal().add(normal));
	        vertices.get(indices.get(i+1)).setNormal(vertices.get(indices.get(i+1)).getNormal().add(normal));
	        vertices.get(indices.get(i+2)).setNormal(vertices.get(indices.get(i+2)).getNormal().add(normal));
	    }

	    for ( int i = 0; i < vertices.size(); ++i )
	    {	
	    	vertices.get(i).setNormal(vertices.get(i).getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCW(SmoothingGroup smoothingGroup)
	{
	    for (Face face : smoothingGroup.getFaces())
	    {
	    	Vector3f v0 = smoothingGroup.getVertices().get(face.getIndices()[0]).getPos();
	    	Vector3f v1 = smoothingGroup.getVertices().get(face.getIndices()[1]).getPos();
	    	Vector3f v2 = smoothingGroup.getVertices().get(face.getIndices()[2]).getPos();
	        
	    	Vector3f normal = v1.sub(v0).cross(v2.sub(v0)).normalize();
	        
	    	smoothingGroup.getVertices().get(face.getIndices()[0]).setNormal(
	    			smoothingGroup.getVertices().get(face.getIndices()[0]).getNormal().add(normal));
	    	smoothingGroup.getVertices().get(face.getIndices()[1]).setNormal(
	    			smoothingGroup.getVertices().get(face.getIndices()[1]).getNormal().add(normal));
	    	smoothingGroup.getVertices().get(face.getIndices()[2]).setNormal(
	    			smoothingGroup.getVertices().get(face.getIndices()[2]).getNormal().add(normal));
	    }

	    for (Vertex vertex : smoothingGroup.getVertices())
	    {	
	    	vertex.setNormal(vertex.getNormal().normalize());
	    }       
	}
	
	public static void generateNormalsCCW(SmoothingGroup smoothingGroup)
	{
		  for (Face face : smoothingGroup.getFaces())
		    {
		    	Vector3f v0 = smoothingGroup.getVertices().get(face.getIndices()[0]).getPos();
		    	Vector3f v1 = smoothingGroup.getVertices().get(face.getIndices()[1]).getPos();
		    	Vector3f v2 = smoothingGroup.getVertices().get(face.getIndices()[2]).getPos();
		        
		    	Vector3f normal = v2.sub(v0).cross(v1.sub(v0)).normalize();
		        
		    	smoothingGroup.getVertices().get(face.getIndices()[0]).setNormal(
		    			smoothingGroup.getVertices().get(face.getIndices()[0]).getNormal().add(normal));
		    	smoothingGroup.getVertices().get(face.getIndices()[1]).setNormal(
		    			smoothingGroup.getVertices().get(face.getIndices()[1]).getNormal().add(normal));
		    	smoothingGroup.getVertices().get(face.getIndices()[2]).setNormal(
		    			smoothingGroup.getVertices().get(face.getIndices()[2]).getNormal().add(normal));
		    }

		    for (Vertex vertex : smoothingGroup.getVertices())
		    {	
		    	vertex.setNormal(vertex.getNormal().normalize());
		    }     
	}
	
	
	public static void generateTangentsBitangents(Mesh mesh)
	{
		for ( int i = 0; i < mesh.getIndices().length; i += 3 )
		{
		    	Vector3f v0 = mesh.getVertices()[mesh.getIndices()[i]].getPos();
		    	Vector3f v1 = mesh.getVertices()[mesh.getIndices()[i+1]].getPos();
		    	Vector3f v2 = mesh.getVertices()[mesh.getIndices()[i+2]].getPos();
		        
		    	Vector2f uv0 = mesh.getVertices()[mesh.getIndices()[i]].getTextureCoord();
		    	Vector2f uv1 = mesh.getVertices()[mesh.getIndices()[i+1]].getTextureCoord();
		    	Vector2f uv2 = mesh.getVertices()[mesh.getIndices()[i+2]].getTextureCoord();
		    	
		    	Vector3f e1 = v1.sub(v0);
		    	Vector3f e2 = v2.sub(v0);
		    	
		    	Vector2f deltaUV1 = uv1.sub(uv0);
		    	Vector2f deltaUV2 = uv2.sub(uv0);
		    	
		    	float r = (1.0f / (deltaUV1.getX() * deltaUV2.getY() - deltaUV1.getY() * deltaUV2.getX()));
		    	
		    	Vector3f tangent = new Vector3f();
		    	tangent.setX(r * deltaUV2.getY() * e1.getX() - deltaUV1.getY() * e2.getX());
		    	tangent.setY(r * deltaUV2.getY() * e1.getY() - deltaUV1.getY() * e2.getY());
		    	tangent.setZ(r * deltaUV2.getY() * e1.getZ() - deltaUV1.getY() * e2.getZ());
		    	Vector3f bitangent = new Vector3f();
		    	Vector3f normal = mesh.getVertices()[mesh.getIndices()[i]].getNormal().add(
		    				   mesh.getVertices()[mesh.getIndices()[i+1]].getNormal()).add(
		    				   mesh.getVertices()[mesh.getIndices()[i+2]].getNormal());
		    	normal = normal.normalize();
		    	
		    	bitangent = tangent.cross(normal);
		    	
		    	tangent = tangent.normalize();
		    	bitangent = bitangent.normalize();
		    	
		    	if (mesh.getVertices()[mesh.getIndices()[i]].getTangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i]].setTangent(new Vector3f(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i]].getBitangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i]].setBitangent(new Vector3f(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+1]].getTangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+1]].setTangent(new Vector3f(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+1]].getBitangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+1]].setBitangent(new Vector3f(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+2]].getTangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+2]].setTangent(new Vector3f(0,0,0));
		    	if (mesh.getVertices()[mesh.getIndices()[i+2]].getBitangent() == null) 
		    		mesh.getVertices()[mesh.getIndices()[i+2]].setBitangent(new Vector3f(0,0,0));
		    	
		    	mesh.getVertices()[mesh.getIndices()[i]].setTangent(mesh.getVertices()[mesh.getIndices()[i]].getTangent().add(tangent));
		    	mesh.getVertices()[mesh.getIndices()[i]].setBitangent(mesh.getVertices()[mesh.getIndices()[i]].getBitangent().add(bitangent));
		    	mesh.getVertices()[mesh.getIndices()[i+1]].setTangent(mesh.getVertices()[mesh.getIndices()[i+1]].getTangent().add(tangent));
		    	mesh.getVertices()[mesh.getIndices()[i+1]].setBitangent(mesh.getVertices()[mesh.getIndices()[i+1]].getBitangent().add(bitangent));
		    	mesh.getVertices()[mesh.getIndices()[i+2]].setTangent(mesh.getVertices()[mesh.getIndices()[i+2]].getTangent().add(tangent));
		    	mesh.getVertices()[mesh.getIndices()[i+2]].setBitangent(mesh.getVertices()[mesh.getIndices()[i+2]].getBitangent().add(bitangent));	
		 }
		
		 for (Vertex vertex : mesh.getVertices())
		    {	
		    	vertex.setTangent(vertex.getTangent().normalize());
		    	vertex.setBitangent(vertex.getBitangent().normalize());
		    }
	}
	
	public static Quaternion normalizePlane(Quaternion plane)
	{
		float mag;
		mag = (float) Math.sqrt(plane.x * plane.x + plane.y * plane.y + plane.z * plane.z);
		plane.setX(plane.x/mag);
		plane.setY(plane.y/mag);
		plane.setZ(plane.z/mag);
		plane.setW(plane.w/mag);
	
		return plane;
	}
	
	public static Vector2f[] texCoordsFromFontMap(char x)
	{
		float x_ = (x%16)/16.0f;
		float y_ = (x/16)/16.0f;
		Vector2f[] texCoords = new Vector2f[4];
		texCoords[0] = new Vector2f(x_, y_ + 1.0f/16.0f);
		texCoords[1] = new Vector2f(x_, y_);
		texCoords[2] = new Vector2f(x_ + 1.0f/16.0f, y_ + 1.0f/16.0f);
		texCoords[3] = new Vector2f(x_ + 1.0f/16.0f, y_);
		
		return texCoords;
	}
}
