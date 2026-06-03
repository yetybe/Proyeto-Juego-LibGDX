package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.Pantallas.PantallaJuego;

public class EnemigoMelee extends Enemigo{
		
	public EnemigoMelee(int vidaMax , float velocidad , int daño, int dropXp, Texture tx, Sound sonidoH, int x, int y) {         
        //  de vida máxima, 2f de velocidad, 10 de daño de ataque
        super(vidaMax, velocidad, daño, new Sprite(tx), sonidoH , dropXp);
        
        this.spr.setPosition(x, y);
        this.spr.setBounds(x, y, 40, 40); // Tamaño del enemigo
    }
	
	@Override
    public void update(PantallaJuego juego) {
        if (muerto) return;

        // 1. Necesitamos saber dónde está el jugador
        Jugador jugador = juego.getJugador();
        
        if (jugador != null && !jugador.estaMuerto()) {
            
            // 2. Calcular la distancia (Deltas) entre el enemigo y el jugador
            float dx = jugador.getX() - this.spr.getX();
            float dy = jugador.getY() - this.spr.getY();
            
            // 3. Obtener el ángulo con Arco Tangente
            float angulo = (float) Math.atan2(dy, dx);
            
            // 4. Moverse fluidamente en esa dirección usando Seno y Coseno
            float nuevaX = this.spr.getX() + (float) Math.cos(angulo) * velocidadMax;
            float nuevaY = this.spr.getY() + (float) Math.sin(angulo) * velocidadMax;
            
            this.spr.setPosition(nuevaX, nuevaY);
        }
    }
	
	@Override
	public void atacar(Jugador pjJugador) {
		pjJugador.recibirDaño(this.getDañoAtaque());
		pjJugador.setPosicionSpr(50, 50);
		
	}
	
}
