package io.github.ConstructoresEnemigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import io.github.Personaje.Enemigo;
import io.github.Personaje.EnemigoMelee;

public class BuilderMelee implements BuilderEnemigo {
	    private int x, y;
	    private Texture textura;
	    private Sound sonido;

	    
	    public BuilderMelee(Texture tx, Sound snd) {
	        this.textura = tx;
	        this.sonido = snd;
	    }

	    @Override
	    public BuilderEnemigo setPosicion(int x, int y) {
	        this.x = x;
	        this.y = y;
	        return this; 
	    }

	    @Override
	    public Enemigo build() {
	        // Ensambla el EnemigoMelee con sus stats fijos por defecto
	        int vidaBase = 7;
	        float velBase = 1f;
	        int dañoBase = 2;
	        return new EnemigoMelee(vidaBase, velBase, dañoBase, textura, sonido, x, y);	    }
}

