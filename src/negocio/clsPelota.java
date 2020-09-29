package negocio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author Tech Home
 */
public class clsPelota extends Thread {

    int px, py, pw, ph;
    int sdx, sdy;
    Graphics pintor;
    Color color;
    JLabel labelVidas;
    //Datos juego
    int vidas;
    //
    final clsLadrillos ladrillos;
    final clsCancha cancha;
    boolean gameover;

    public clsPelota(Graphics pintor, clsLadrillos lad, clsCancha cancha, Color color,
            JLabel label) {
        this.ladrillos = lad;
        this.cancha = cancha;
        this.pw = 20;
        this.ph = 20;
        this.px = (int) (Math.random() * 800) - 30;
        this.py = 80;
        this.sdx = 0;
        this.sdy = 0;
        this.pintor = pintor;
        this.color = color;
        this.vidas = 3;
        this.labelVidas = label;
        this.labelVidas.setText("Vidas: " + vidas);
        this.reiniciarPelotaPos();
    }

    public void init() {
        this.gameover = false;
        this.pw = 20;
        this.ph = 20;
        this.px = (int) (Math.random() * 800) - 30;
        this.py = 80;
        this.sdx = 0;
        this.sdy = 0;
        this.vidas = 3;
        this.labelVidas.setText("Vidas: " + vidas);
        this.reiniciarPelotaPos();

    }

    public void setPosPelota(int px, int py) {
        this.px = px;
        this.py = py;
    }

    public void reiniciarPelotaPos() {
        int px1 = cancha.tx + (cancha.tw / 2);
        px1 = px1 - (pw / 2);
        int py1 = cancha.ty - pw;
        this.sdx = 0;
        this.sdy = 0;
        setPosPelota(px1, py1);
    }

    public void startPelota() {
        this.sdx = 0;
        this.sdy = -1;
    }

    @Override
    public void run() {
        while (true && !gameover) {
            // Aqui hay que programar la direccion de la pelota..
            int p = clsCancha.jugando ? 1 : 0;
            borrarP();
            this.px = this.px + 5 * sdx * p;
            this.py = this.py + 5 * sdy * p;
            pintarP();
            int nx = px + pw / 2;
            if (nx >= cancha.tx && nx <= cancha.tx + cancha.tw) {
                if (py + ph >= cancha.ty && py + ph <= cancha.ty + cancha.th) {
                    //Si choca con la paleta
                    sdy = -1;
                    if (sdx == 0) {
                        int mtx = cancha.tx + (cancha.tw / 2);
                        if (nx > mtx) {
                            sdx = 1;
                        } else if (nx < mtx) {
                            sdx = -1;
                        }
                    }
                    System.out.println("Chocó Paleta");
                }
            }
            if (px < 0 || px + pw > cancha.w) {//Si choca con algun lateral
                sdx *= -1;
            }
            if (py < 80) {//Si choca con el techo
                sdy = 1;
                System.out.println("Chocó Arriba");
            }
            if (this.py > 400) {//Si choca con el piso
                this.px = (int) (Math.random() * 800) - 30;
                this.py = 80;
                this.vidas = this.vidas - 1;
                this.sdx = 0;
                this.sdy = 0;
                this.labelVidas.setText("Vidas: " + vidas);
                this.reiniciarPelotaPos();
                if (vidas == 0) {
                    gameover = true;
                    ladrillos.gameover = true;
                    cancha.gameover = true;
                    pintor.fillRect(0, 80, cancha.w, cancha.h);
                    pintor.setColor(Color.BLACK);
                    pintor.setFont(new Font("Arial", Font.BOLD, 32));
                    pintor.drawString("GAME OVER", 300, 200);
                    pintor.setColor(color);
                }
            }
            try {
                sleep(50);
            } catch (Exception e) {
            }
        }
    }

    private void pintarP() {
        this.pintor.setColor(Color.RED);
        this.pintor.fillOval(px, py, pw, ph);
        this.pintor.setColor(this.color);
    }

    private void borrarP() {
        this.pintor.setColor(this.color);
        this.pintor.fillOval(px, py, pw, ph);
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getW() {
        return pw;
    }

    public void setW(int w) {
        this.pw = w;
    }

    public int getH() {
        return ph;
    }

    public void setH(int h) {
        this.ph = h;
    }

}
