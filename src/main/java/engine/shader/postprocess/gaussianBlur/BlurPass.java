package shader.postprocess.gaussianBlur;

import primitive.texture.Texture2D;

public class BlurPass {
	
	private Texture2D blurredTexture = null;
	private HorizontalBlur hBlur;
	private VerticalBlur vBlur;
	
	public BlurPass(int width, int height) {
		hBlur = new HorizontalBlur(width, height);
		vBlur = new VerticalBlur(width, height);
	}
	
	public void render(Texture2D sourceTexture) {
		hBlur.render(sourceTexture);
		vBlur.render(hBlur.getOutputTexture());
		blurredTexture = vBlur.getOutputTexture();
	}
	
	public Texture2D getBlurredTexture() {
		return blurredTexture;
	}
	
	public void clean() {
		hBlur.cleanUp();
		vBlur.cleanUp();
	}
}
 