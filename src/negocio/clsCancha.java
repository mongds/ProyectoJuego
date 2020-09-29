/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Tech Home
 */
public class clsCancha extends Thread {

    // Cancha, color, tablero puntos, pelota, tablita
    Graphics pintor;
    Color color;
    // Datos para la tablita
    public int tx, ty, th, tw;
    int w, h;
    // pelota
    clsPelota pelota;
    clsLadrillos ladrillos;
    //Datos del juego
    int puntaje;
    JLabel label;
    boolean gameover;
    static boolean jugando;

    public clsCancha(Graphics pintor, Color color, int w, int h,
            JLabel labelV, JLabel labelP) {
        this.gameover = false;
        this.h = h;
        this.w = w;
        this.pintor = pintor;
        this.color = color;
        this.th = 10;
        this.tw = 50;
        this.tx = (w / 2) - tw / 2;//400
        this.ty = h - 50;//350
        this.ladrillos = new clsLadrillos(pintor, color, w, h);
        this.pelota = new clsPelota(pintor, ladrillos, this, color, labelV);
        this.puntaje = 0;
        this.label = labelP;
        this.label.setText("Puntaje: " + puntaje);
        jugando = false;
    }

    public void init() {
        this.gameover = false;
        this.th = 10;
        this.tw = 50;
        this.tx = (w / 2) - tw / 2;//400
        this.ty = h - 50;//350
        this.puntaje = 0;
        this.label.setText("Puntaje: " + puntaje);
        this.ladrillos.init();
        this.pelota.init();
        jugando = true;
    }

    public void iniciarPelota() {
        this.pelota.start();
    }

    public void moverPelota() {
        this.pelota.startPelota();
    }

    public void reiniciarPelotaPos() {
        this.pelota.reiniciarPelotaPos();
    }

    public void dibujarLadrillos() {
        this.ladrillos.start();
    }

    @Override
    public void run() {
        // Lógica para el juego
        jugando = true;
        while (true && !gameover) {
            //Si choca con algun ladrillo
            int nx = pelota.px + (pelota.pw / 2);
            for (int i = ladrillos.fil; i > 0; i--) {
                boolean si = false;
                for (int j = 0; j < ladrillos.col; j++) {
                    int lx = ladrillos.getX(i - 1, j);
                    int ly = ladrillos.getY(i - 1, j);
                    if (lx != -1 && ly != -1) {
                        if (nx >= lx && nx <= lx + ladrillos.lw
                                && pelota.py >= ly && pelota.py <= ly + ladrillos.lh) {
                            ladrillos.destruidos++;
                            ladrillos.setX(i - 1, j, -1);
                            ladrillos.setY(i - 1, j, -1);
                            pintor.setColor(color);
                            pintor.fillRect(lx, ly, ladrillos.lw, ladrillos.lh);
                            puntaje += (ladrillos.fil + 1 - i) * 10;
                            label.setText("Puntaje: " + puntaje);
                            ladrillos.destruidos = ladrillos.destruidos + 1;
                            /*if(ladrillos.destruidos == 3){
                                detener();
                                pintor.fillRect(0, 80, w, h);
                                pintor.setColor(Color.BLACK);
                                pintor.setFont(new Font("Arial", Font.BOLD, 32));
                                pintor.drawString("GANASTE", 300, 200);
                                pintor.setColor(color);
                            }*/
                            if (pelota.py <= ly + (ladrillos.lh / 2)) {
                                pelota.sdx *= -1;
                            } else {
                                pelota.sdy *= -1;
                            }
                            si = true;
                            break;
                        }
                    }
                }
                if (si) {
                    break;
                }
            }
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
    }

    public void cambiar() {
        clsCancha.jugando = !clsCancha.jugando;
    }

    public void detener() {
        this.gameover = true;
        this.ladrillos.gameover = true;
        this.pelota.gameover = true;
        /*pintor.fillRect(0, 0, w, h);
        pintor.setColor(Color.BLACK);
        pintor.setFont(new Font("Arial", Font.BOLD, 18));
        pintor.drawString("GAME OVER", 0, 0);
        pintor.setColor(color);*/
    }

    public void dibujarTablita() {
        this.pintor.setColor(Color.BLACK);
        this.pintor.fillRect(tx, ty, tw, th);
        this.pintor.setColor(this.color);
    }

    public void limpiarTablita() {
        this.pintor.setColor(this.color);
        this.pintor.fillRect(tx, ty, tw, th);
    }

    public void moverDerechaTablita() {
        if (this.tx + this.tw < w) {
            limpiarTablita();
            this.tx = this.tx + 10;
            dibujarTablita();
        }

    }

    public void moverIzquierdaTablita() {
        if (this.tx > 0) {
            limpiarTablita();
            this.tx = this.tx - 10;
            dibujarTablita();
        }

    }
}
