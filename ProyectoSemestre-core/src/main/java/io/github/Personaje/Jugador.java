package io.github.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import io.github.Pantallas.PantallaSubirLVL;
import io.github.Pantallas.PantallaJuego;


public class Jugador extends Entidad {
	private float cadenciaAtaque; 	
	private float temporizadorDisparo; 
    private boolean herido;
    private int tiempoHeridoMax;
    private int tiempoHerido;
    private Sound soundDañoAtaque;
    private Sound soundBala;
    private Texture txBala;
    private int expJugador;
    private int lvlJugador;
    private int lvlCap;
    private boolean godMode = false;
    
    public Jugador(int x, int y, Texture tx, Texture txBala,Sound sonidoHerido , Sound soundBala) {
    	
    	//Vida Maxima(0) , Velocidad Maxima(1) y Daño Ataque(3)
    	super(10,1.5f,2, new Sprite(tx),sonidoHerido);
    	
    	expJugador = 0;
    	lvlJugador = 1;
    	lvlCap = 20;
    	this.cadenciaAtaque = 0.5f; // medio segundo
    	this.temporizadorDisparo = 0f;
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
        temporizadorDisparo += Gdx.graphics.getDeltaTime();

        // Si el cronómetro supera nuestra cadencia, disparamos y reiniciamos el reloj
        if (temporizadorDisparo >= cadenciaAtaque) {
        	dispararBala(juego);
        	temporizadorDisparo = 0f; // Reiniciamos el contador
        }
        // 4. TEMPORIZADOR DE INMUNIDAD
        if (herido) {
            tiempoHerido--;
            if (tiempoHerido <= 0) {
                herido = false; // Se acaba la inmunidad
            }
        }
        
        //
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
        float velocidadBala = 400f; 
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
      
    public void  setPosicionSpr(int x , int y){ 
    	if (godMode) return;
    	float nuevaXpos = spr.getX() + x;
    	float nuevaYpos = spr.getY() + y;
    	spr.setPosition(nuevaXpos ,nuevaYpos );

    }
    
    public boolean ganarXp(int cantidadXp) {
    	expJugador += cantidadXp;
    	boolean subirNivel = false;

        // Mientras tenga suficiente XP para subir (usamos while por si gana mucha XP de golpe)
        while (expJugador >= lvlCap) {
        	expJugador = 0;
        	lvlJugador++;   	
        	lvlCap = (int)(lvlCap *= 1.4f);
        	subirNivel = true;
        }
        return subirNivel;
    }
    
    @Override
    public void recibirDaño(int dañoRecibido) {
        if (godMode || herido || muerto) return;
        
        super.recibirDaño(dañoRecibido); 
        
        this.herido = true;
        this.tiempoHerido = this.tiempoHeridoMax;
    }
    
    public boolean isGodMode() { return godMode; }
    
    public void toggleGodMode() { this.godMode = !this.godMode; }

    public void forzarSubirNivel() {
        int expFaltante = this.lvlCap - this.expJugador;
        this.ganarXp(expFaltante); 
        this.expJugador = 0;
    }
    
    public boolean estaMuerto() {return !herido && muerto;}
    public boolean estaHerido() {return herido;}
    public int getVidaMax() { return vidaMax;}
    public int getVidaActual() {return vidaActual;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
    public int getLvl() { return lvlJugador;}
    public int getExp() { return expJugador;}
    public int getLvlCap() { return lvlCap;}
    public float getVelMax() { return this.velocidadMax;}
    public float getCadenciaAtaque() { return cadenciaAtaque; }
    public void setCadenciaAtaque(float cadencia) { this.cadenciaAtaque = cadencia; }
 
    public void setDañoAtaque(int dmg) { dañoAtaque = dmg;}
	public void setVidaMax(int Puntosvida) {vidaMax = Puntosvida;}
	public void setVidaActual(int totalVida) {vidaActual = totalVida;}
	public void setVelocidadMax(float nuevaVel) {this.velocidadMax = nuevaVel;}
}
