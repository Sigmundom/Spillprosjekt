package projectiles;

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

public class Projectile extends Sprite {
		
	private World world;
	private Body body;
	private float vX;
	private float vY;
	
	public Projectile(World world, String name, float x, float y, float vX, float vY, float angle) {
		super(new Texture(name));
		this.world = world;
		this.vX = vX;
		this.vY = vY;
		setPosition(x-getWidth()/2, y-getHeight()/2);
		setRotation(angle);
		createBody();
	}
	
void createBody() {
		
		BodyDef bodyDef = new BodyDef();
		// a static body is not affected by gravity or other forces
		// a kinemetic body is not affeected by gravity but iti is affected by other forces
		// a dynamic body is affected by gravity and other forces
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		
		bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
		
		body = world.createBody(bodyDef);
		body.setLinearVelocity(vX, vY);
		
		PolygonShape shape = new PolygonShape();

		shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1;
		
		Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData(this);
		fixture.setSensor(false); //true -> collision is detected, but not affecting object.
			
		shape.dispose();
			
		}

	public void updateProjectile() {
		this.setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
	}
}
