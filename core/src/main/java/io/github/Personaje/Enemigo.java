package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public  abstract class Enemigo extends Entidad {
	
	//Experiencia que le da al jugador cuando muere el enemigo
	protected int dropXp;
	public Enemigo(int vidaMax, float velocidadMax, int dañoAtaque, Sprite spr, Sound sonidoHerido , int dropXp) {
        super(vidaMax, velocidadMax, dañoAtaque, spr, sonidoHerido);
        this.dropXp = dropXp;
    }
    
    // Todos los enemigos deberán saber cómo hacer daño al jugador
    public abstract void atacar(Jugador jugador);
    public int getDropXp() { return dropXp;}
}

