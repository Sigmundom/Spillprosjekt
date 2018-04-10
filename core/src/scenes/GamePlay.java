package scenes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameMain;

import SpaceObjects.Planet;
import helpers.GameInfo;
import player.Player;
import projectiles.Projectile;

public class GamePlay implements Screen, ContactListener {

	private GameMain game;
	
	private Texture bg;
	private Player player;
	private Planet planet;
	private Planet planet1;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>(0);
	private World world;
	private OrthographicCamera box2DCamera;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch sb;
	
	
	public GamePlay(GameMain game) {
		this.game = game;
		
		box2DCamera = new OrthographicCamera();
		box2DCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
		box2DCamera.position.set(GameInfo.WIDTH * GameInfo.PPM / 2f, GameInfo.HEIGHT * GameInfo.PPM / 2f, 0);
		
		debugRenderer = new Box2DDebugRenderer();
		
		world = new World(new Vector2(0,0), true);
		
		world.setContactListener(this);
		
		bg = new Texture("Game_BG.jpg");
		player = new Player(world, "Player.png", 
				GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2);
		
		planet = new Planet(world, "Planet1.png", GameInfo.WIDTH / 2, GameInfo.HEIGHT / 4);
		planet1 = new Planet(world, "Planet1.png", GameInfo.WIDTH / 4, GameInfo.HEIGHT / 2);
		
		sb = new SpriteBatch();
	}
	
	void update(float dt) {
		//Register key-down
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.getBody().setAngularVelocity(5);
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.getBody().setAngularVelocity(-5);
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.getBody().applyLinearImpulse(new Vector2(-0.005f*sin(0),
					0.005f*cos(0)), player.getBody().getWorldCenter(), true);
			System.out.println(String.valueOf(player.getBody().getAngle()));
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.getBody().applyLinearImpulse(new Vector2(0.005f*sin(0),
					-0.01f*cos(0)), player.getBody().getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			player.getBody().applyLinearImpulse(new Vector2(-0.005f*sin(Math.PI/2),
					0.005f*cos(Math.PI/2)), player.getBody().getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			player.getBody().applyLinearImpulse(new Vector2(0.005f*sin(Math.PI/2),
					-0.005f*cos(Math.PI/2)), player.getBody().getWorldCenter(), true);
		}
		
		//Register key-up
		if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.getBody().setAngularVelocity(0);
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			projectiles.add(new Projectile(world, "Projectile.png", 
					player.getX() + (player.getWidth()/2)*cos(0) - (player.getHeight()+20)*sin(0), 
					player.getY() + (player.getHeight()+20)*cos(0) + (player.getWidth()/2)*sin(0), 
					-4 * sin(0), 4 * cos(0), 
					(float)Math.toDegrees(player.getBody().getAngle())));
		}
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		update(delta);
		
		player.updatePlayer();
		for(Projectile p:projectiles) {
			p.updateProjectile();			
		}
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		game.getBatch().draw(bg, 0, 0);
//		game.getBatch().draw(player, player.getX(), player.getY(),0, 0, player.getWidth(), player.getHeight(),
//				1,1,player.getRotation());
//		game.getBatch().draw(player, player.getX(),player.getY());
//		game.getBatch().draw(player, player.getX(), player.getY(), 0, 0, player.getWidth(), player.getHeight(),
//				1,1,player.getRotation(), false);
		game.getBatch().draw(planet, planet.getX() - planet.getWidth()/2, planet.getY() - planet.getHeight()/2);
		game.getBatch().draw(planet1, planet1.getX() - planet1.getWidth()/2, planet1.getY() - planet1.getHeight()/2);
		game.getBatch().end();
		
		sb.begin();
		player.draw(sb);
		
		for(Projectile p:projectiles) {
			p.draw(sb);			
		}
		sb.end();
		
		
		debugRenderer.render(world, box2DCamera.combined);
		
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		bg.dispose();
		player.getTexture().dispose();
		planet.getTexture().dispose();
		
	}

	@Override
	public void beginContact(Contact contact) {
		
		Fixture firstBody, secondBody;
		
		if(contact.getFixtureA().getUserData().getClass().getName() == "Projectile") {
			
			// setting fixture A e.g. the player to the first body
			firstBody = contact.getFixtureA();
			secondBody = contact.getFixtureB();
			
		} else {

			firstBody = contact.getFixtureB();
			secondBody = contact.getFixtureA();
			
		}
		
		System.out.println("first body is " + firstBody.getUserData());
		
		projectiles.remove(firstBody.getUserData());
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private float cos(double add) {
		return (float)Math.cos((double)player.getBody().getAngle()+add);
	}
	
	private float sin(double add) {
		return (float)Math.sin((double)player.getBody().getAngle()+add);
	}
	
	

}
