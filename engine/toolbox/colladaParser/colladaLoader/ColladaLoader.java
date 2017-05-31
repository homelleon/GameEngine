package toolbox.colladaParser.colladaLoader;

import toolbox.MyFile;
import toolbox.colladaParser.dataStructures.AnimatedModelData;
import toolbox.colladaParser.dataStructures.AnimationData;
import toolbox.colladaParser.dataStructures.MeshData;
import toolbox.colladaParser.dataStructures.SkeletonData;
import toolbox.colladaParser.dataStructures.SkinningData;
import toolbox.colladaParser.xmlParser.XmlNode;
import toolbox.colladaParser.xmlParser.XmlParser;

public class ColladaLoader {

	public static AnimatedModelData loadColladaModel(MyFile colladaFile, int maxWeights) {
		XmlNode node = XmlParser.loadXmlFile(colladaFile);

		SkinLoader skinLoader = new SkinLoader(node.getChild("library_controllers"), maxWeights);
		SkinningData skinningData = skinLoader.extractSkinData();

		SkeletonLoader jointsLoader = new SkeletonLoader(node.getChild("library_visual_scenes"), skinningData.jointOrder);
		SkeletonData jointsData = jointsLoader.extractBoneData();

		GeometryLoader g = new GeometryLoader(node.getChild("library_geometries"), skinningData.verticesSkinData);
		MeshData meshData = g.extractModelData();

		return new AnimatedModelData(meshData, jointsData);
	}

	public static AnimationData loadColladaAnimation(MyFile colladaFile) {
		XmlNode node = XmlParser.loadXmlFile(colladaFile);
		XmlNode animNode = node.getChild("library_animations");
		XmlNode jointsNode = node.getChild("library_visual_scenes");
		AnimationLoader loader = new AnimationLoader(animNode, jointsNode);
		AnimationData animData = loader.extractAnimation();
		return animData;
	}

}
