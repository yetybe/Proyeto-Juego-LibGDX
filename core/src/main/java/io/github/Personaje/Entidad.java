package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Pantallas.PantallaJuego;

public abstract class Entidad {
	
    protected int vidaMax;
    protected int vidaActual;
    protected float velocidadMax;
    protected int dañoAtaque;
    protected Sprite spr;
    protected Sound sonidoHerido;
    protected boolean muerto;
    
    public Entidad(int vidaMax, float velocidadMax, int dañoAtaque, Sprite spr, Sound sonidoHerido) {
        this.vidaMax = vidaMax;
        this.vidaActual = vidaMax; 
        this.velocidadMax = velocidadMax;
        this.dañoAtaque = dañoAtaque;
        this.spr = spr;
        this.sonidoHerido = sonidoHerido;
        this.muerto = false; 
    }
    
    public abstract void update(PantallaJuego juego);
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    
}
