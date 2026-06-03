package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.Main.SpaceNavigation;
import io.github.Personaje.Jugador;


public class PantallaSubirLVL implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Jugador pjJugador;
	private PantallaJuego pantallaJuego;

	public PantallaSubirLVL(SpaceNavigation game , PantallaJuego pantallaJuego , Jugador pjJugador) {
		this.game = game;
		this.pantallaJuego = pantallaJuego;
		this.pjJugador = pjJugador;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
    public void render(float delta) {
        
        // 1. EFECTO DE PAUSA (Opcional pero muy visual)
        // En lugar de borrar el fondo de azul, dibujamos la partida pausada atrás.
        // Le pasamos delta 0 para que nada se mueva.
        pantallaJuego.render(0f); 

        // 2. CONFIGURAR LA CÁMARA PARA LOS TEXTOS
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        // 3. DIBUJAR LA INTERFAZ DE MEJORAS
        game.getBatch().begin();
        
        // Ajusta las coordenadas (X, Y) según cómo se vea en tu monitor
        game.getFont().getData().setScale(2f); // Hacemos la letra más grande
        game.getFont().draw(game.getBatch(), "¡NIVEL AUMENTADO!", 400, 600);
        
        game.getFont().getData().setScale(1.5f);
        game.getFont().draw(game.getBatch(), "Elige una mejora para tu nave:", 350, 500);

        game.getFont().draw(game.getBatch(), "[1] Aumentar Daño (+2)", 400, 400);
        game.getFont().draw(game.getBatch(), "[2] Aumentar Vida Máxima (+2)", 400, 350);
        game.getFont().draw(game.getBatch(), "[3] Aumentar Velocidad (+0.25)", 400, 300);
        game.getFont().draw(game.getBatch(), "[4] Aumentar Vel. de Ataque (-0.05s)", 400, 250);
        
        game.getBatch().end();

        // 4. LÓGICA DE SELECCIÓN (Teclas 1, 2 y 3)
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            
            pjJugador.setDañoAtaque(pjJugador.getDañoAtaque() + 5);
            volverAlJuego();
            
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            
            pjJugador.setVidaMax(pjJugador.getVidaMax() + 2);
            // Curamos al jugador al subir de vida máxima como premio
            pjJugador.setVidaActual(pjJugador.getVidaMax()); 
            volverAlJuego();
            
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            
            pjJugador.setVelocidadMax(pjJugador.getVelMax() + 0.5f);
            volverAlJuego();
        
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
	    
			// Le restamos 0.05 segundos al tiempo de disparo
			float nuevaCadencia = pjJugador.getCadenciaAtaque() - 0.05f;
	    
			// Ponemos un límite para que no baje de 0.1 segundos (para que no dispare infinito y rompa el juego)
			if (nuevaCadencia < 0.1f) {
				nuevaCadencia = 0.1f;
			}
	    
			pjJugador.setCadenciaAtaque(nuevaCadencia);
			volverAlJuego();
		}
    }

    // Método auxiliar para no repetir código
    private void volverAlJuego() {
        // Devolvemos la pantalla original (la que estaba en pausa), NO creamos una nueva
        game.setScreen(pantallaJuego); 
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