package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.github.Pantallas.PantallaJuego;


public class Jugador extends Entidad {
	
    private float velocidadActual;
    private boolean herido;
    private int tiempoHeridoMax;
    private int tiempoHerido;
    private Sound soundDañoAtaque;
    private Sound soundBala;
    private Texture txBala;
    
    public Jugador(int vidaMax, float velocidadMax, int dañoAtaque, Sprite spr, Sound sonidoHerido,
    		 int x, int y, Texture tx, Texture txBala, Sound soundBala) {
    	
    	super(vidaMax,velocidadMax,dañoAtaque, new Sprite(tx),sonidoHerido);
    	
    	this.herido = false;
    	this.tiempoHeridoMax = 50;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	
    	this.spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	this.spr.setBounds(x, y, 45, 45);

    }
    
    @Override
    public void update(PantallaJuego juego) {
        if (muerto) return; 

        float x = spr.getX();
        float y = spr.getY();

        // 1. MOVIMIENTO (Usando la variable protegida velocidadMax de Entidad)
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= velocidadMax;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += velocidadMax;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= velocidadMax;     
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += velocidadMax;
        
        // 2. LÍMITES DE PANTALLA
        if (x < 0) x = 0;
        if (x + spr.getWidth() > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - spr.getWidth();
        if (y < 0) y = 0;
        if (y + spr.getHeight() > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - spr.getHeight();
        
        // Aplicar la nueva posición
        spr.setPosition(x, y);

        // 3. DISPARO CON MOUSE
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {         
            // Usamos las coordenadas del sprite heredado
            Bullet bala = new Bullet(spr.getX() + spr.getWidth()/2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }

        // 4. TEMPORIZADOR DE INMUNIDAD
        if (herido) {
            tiempoHerido--;
            if (tiempoHerido <= 0) {
                herido = false; // Se acaba la inmunidad
            }
        }
    }
    
    private void dispararBala(PantallaJuego juego) {
        // A. Capturar la posición del mouse en la pantalla
        Vector3 posicionMouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        
        // B. Traducir esa posición al mundo del juego (CRÍTICO)
        juego.getCamera().unproject(posicionMouse);
        
        // C. Calcular el centro del jugador (de donde sale la bala)
        float origenX = spr.getX() + spr.getWidth() / 2;
        float origenY = spr.getY() + spr.getHeight() / 2;
        
        // D. Calcular la distancia (Deltas)
        float dx = posicionMouse.x - origenX;
        float dy = posicionMouse.y - origenY;
        
        // E. Obtener el ángulo con Arco Tangente
        float angulo = (float) Math.atan2(dy, dx);
        
        // F. Aplicar Seno y Coseno para obtener las velocidades finales
        float velocidadBala = 10f; 
        float velX = (float) Math.cos(angulo) * velocidadBala;
        float velY = (float) Math.sin(angulo) * velocidadBala;
        
        // G. Crear y disparar la bala
        Bullet bala = new Bullet(origenX - 5, origenY - 5, velX, velY, txBala);
        juego.agregarBala(bala);
        soundBala.play();
    }
    
    public void draw(SpriteBatch batch) {
        if (muerto) return; // No dibujamos nada si está muerto

        if (!herido) {
            // Dibujado normal
            spr.draw(batch);
        } else {
            // Efecto de parpadeo usando el residuo de la división
            if (tiempoHerido % 10 > 5) {
                spr.draw(batch); 
            }
        }
    }
      

    
    public boolean estaDestruido() {return !herido && muerto;}
    public boolean estaHerido() {return herido;}
    public int getVidas() {return vidaActual;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidasMax(int vidas2) {vidaMax = vidas2;}
}
