package SpaceObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Planet extends Sprite {
	
	private World world;
	private Body body;
	
	public Planet(World world, String name, float x, float y) {
		super(new Texture(name));
		this.world = world;
		setPosition(x,y);
		createBody();
	}
	
void createBody() {
		
		BodyDef bodyDef = new BodyDef();
		// a static body is not affected by gravity or other forces
		// a kinemetic body is not affeected by gravity but iti is affected by other forces
		// a dynamic body is affected by gravity and other forces
		bodyDef.type = BodyDef.BodyType.StaticBody;
		
		bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();

//		shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);
		
		shape.set(fxCircle(8));
//		shape.setRadius((getWidth() / 2 + 10) / GameInfo.PPM);
		
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		
		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData("Planet");
		fixture.setSensor(false); //true -> collision is detected, but not affecting object.
		
		shape.dispose();
		
	}

//	private Vector2[] fxCircle(int n) {
//		float r = getWidth() / 2;
//		Vector2[] vArr = new Vector2[n];;
//		for(int i=0; i<n; i++) {
//			double t = i * 2* Math.PI/n;
//			float x = (float) (r * Math.cos(t));
//			float y = (float) (r * Math.sin(t));
//			vArr[i] = new Vector2(x,y);
//		}
//		for(int i=0; i<n; i++ ) {
//			System.out.println(vArr[i].toString());
//		}
//		return vArr;
//	}

	private float[] fxCircle(int n) {
		float r = (getWidth() / 2) / GameInfo.PPM;
		float[] fArr = new float[2*n];;
		for(int i=0; i<n; i++) {
			double t = i * 2* Math.PI/(n+1);
			fArr[2*i] = (float) (r * Math.cos(t));
			fArr[2*i+1] = (float) (r * Math.sin(t));
		}
//		for(int i=0; i<2*n; i++ ) {
//			System.out.println(String.valueOf(fArr[i]));
//		}
		return fArr;
	}


}
