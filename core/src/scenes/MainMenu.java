package scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameMain;

public class MainMenu implements Screen {

	private GameMain game;
	
	private Texture bg;
	
	public MainMenu(GameMain game) {
		this.game = game;
		
		this.bg = new Texture("Game_BG.jpg");
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		game.getBatch().begin();
		game.getBatch().draw(bg, 0, 0);
		game.getBatch().end();
		
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
		// TODO Auto-generated method stub
		
	}
	
	

}
