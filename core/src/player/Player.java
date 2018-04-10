package player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Player extends Sprite {
	
	private World world;
	private Body body;
	
	public Player(World world, String name, float x, float y) {
		super(new Texture(name));
		this.world = world;
		this.setPosition(x - getWidth() / 2, y - getHeight() / 2);
		this.setOrigin(0,0);
		createBody();
	}
	
	void createBody() {
		
		BodyDef bodyDef = new BodyDef();
		// a static body is not affected by gravity or other forces
		// a kinemetic body is not affeected by gravity but iti is affected by other forces
		// a dynamic body is affected by gravity and other forces
		bodyDef.type = BodyDef.BodyType.DynamicBody;
//		bodyDef.linearDamping = 20;
		bodyDef.position.set((getX() - getWidth()/2) / GameInfo.PPM, getY() / GameInfo.PPM);
		bodyDef.linearDamping = 0.8f;
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();

		shape.set(new float[] {
				0, 0,
				(getWidth() / GameInfo.PPM) / 2, getHeight() / GameInfo.PPM,
				getWidth() / GameInfo.PPM, 0,
		});
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.restitution = 0.2f; //bounce
		fixtureDef.friction=0.5f;
		
		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData("Player");
		
		shape.dispose();
	}
	
	public void updatePlayer() {
		this.setPosition((body.getPosition().x) * GameInfo.PPM, 
				body.getPosition().y * GameInfo.PPM);
		this.setRotation((float)Math.toDegrees(body.getAngle()));
	}
	
	public Body getBody() {
		return body;
	}

}
