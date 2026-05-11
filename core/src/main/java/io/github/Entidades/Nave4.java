package io.github.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;



public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    private float velocidad = 2f;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);

    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
        float x = spr.getX();
        float y = spr.getY();

        // 1. EL MOVIMIENTO (Siempre funciona, estés herido o no)
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= velocidad;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += velocidad;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= velocidad;     
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += velocidad;
        
        // 2. LOS LÍMITES DE LA PANTALLA
        if (x < 0) x = 0;
        if (x + spr.getWidth() > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - spr.getWidth();
        if (y < 0) y = 0;
        if (y + spr.getHeight() > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - spr.getHeight();
        
        // Aplicamos la posición matemática a la imagen
        spr.setPosition(x, y);   

        // 3. EL DIBUJADO (Aquí sí importa si estás herido)
        if (!herido) {
            // Nave normal
            spr.draw(batch);
        } else {
            // Nave parpadeando
            if (tiempoHerido % 10 > 5) {
                spr.draw(batch); 
            }
            // Disminuir el tiempo de inmunidad
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        // 4. DISPARO
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {         
            Bullet bala = new Bullet(spr.getX() + spr.getWidth()/2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }
    }
      
    public boolean checkCollision(Ball2 b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
