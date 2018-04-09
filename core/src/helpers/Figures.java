package helpers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Figures {
	
	PolygonSprite poly;
	PolygonSpriteBatch polyBatch;
	Texture textureSolid;
	
	public Figures(/*String type, int width, int height*/) {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0x00FF00AA);
        pix.fill();
	    float a = 100;
	    float b = 100;
	    PolygonRegion polyReg = new PolygonRegion(new TextureRegion(new Texture(pix)),
	      new float[] {
	        a*0, b*0,
	        a*0, b*2,
	        a*3, b*2,
	        a*3, b*0,
	        a*2, b*0,
	        a*2, b*1,
	        a*1, b*1,
	        a*1, b*0,
	    }, null);
	    poly = new PolygonSprite(polyReg);
	    poly.setOrigin(a, b);
	    polyBatch = new PolygonSpriteBatch();
	}
	
	public PolygonSprite getFigure() {
		return poly;
	}

}
