package io.github.ConstructoresEnemigos;

import io.github.Personaje.Enemigo;

public interface BuilderEnemigo {
	
	public BuilderEnemigo setPosicion(int x , int y);
	
	Enemigo build();
}
