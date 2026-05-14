package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;


public class Bullet {

	private float xSpeed;
	private float ySpeed;
	private boolean destroyed = false;
	private Sprite spr;
	    
	    public Bullet(float x, float y, float xSpeed, float ySpeed, Texture tx) {
	    	spr = new Sprite(tx);
	    	spr.setPosition(x, y);
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	    }
	 // Le pasamos el delta temporal
	    public void update(float delta) {
	        
	        // 1. Movimiento constante sin importar los FPS
	        float nuevaX = spr.getX() + (xSpeed * delta);
	        float nuevaY = spr.getY() + (ySpeed * delta);
	        spr.setPosition(nuevaX, nuevaY);
	        
	        // 2. Limpieza de memoria (¡Tu lógica intacta y compactada!)
	        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
	            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
	            
	            destroyed = true;
	        }
	    }
	    
	    public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    }
	    

	    public void setDestroyed(boolean destruida) {this.destroyed = destruida;}
	    public boolean isDestroyed() {return destroyed;}
	    public Rectangle getArea() {return this.spr.getBoundingRectangle();}
	    
	
}
