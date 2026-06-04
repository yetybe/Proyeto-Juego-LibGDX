package io.github.Pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.Main.SpaceNavigation;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
	    ScreenUtils.clear(0, 0, 0.2f, 1);

	    camera.update();
	    game.getBatch().setProjectionMatrix(camera.combined);

	    game.getBatch().begin();
	    game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation !", 140, 400);
	    game.getFont().draw(game.getBatch(), "Presiona [1] para Modo Normal", 100, 300);
	    game.getFont().draw(game.getBatch(), "Presiona [2] para Modo Desarrollador", 100, 250);
	    game.getBatch().end();

	    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
	        iniciarJuego(false); // Normal
	    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
	        iniciarJuego(true);  // Developer
	    }
	}

	private void iniciarJuego(boolean isDeveloper) {
	    Screen ss = new PantallaJuego(game, 1, 10, isDeveloper); 
	    ss.resize(1200, 800);
	    game.setScreen(ss);
	    dispose();
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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