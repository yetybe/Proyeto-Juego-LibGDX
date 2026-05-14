package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

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
    public void draw(SpriteBatch batch) {spr.draw(batch);}
    
    public void recibirDaño(int dañoRecibido) {
        // Si ya está muerto, ignoramos el daño
        if (muerto) return;

        // Restamos la vida
        vidaActual -= dañoRecibido;
        
        // Reproducimos el sonido que le asignamos al nacer
        if (sonidoHerido != null) {
            sonidoHerido.play();
        }

        // Verificamos si este golpe fue el golpe de gracia
        if (vidaActual <= 0) {
            vidaActual = 0; // Para que no queden números negativos
            muerto = true;
        }
    }
    
    public Rectangle getArea() {return this.spr.getBoundingRectangle();}
    public int getDañoAtaque() {return this.dañoAtaque;}
    public boolean isMuerto() { return this.muerto; }
}
