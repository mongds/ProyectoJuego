/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.random;

/**
 *
 * @author Tech Home
 */
public class clsLadrillos extends Thread {

    Graphics pintor;
    Color color;
    int w, h;
    //
    boolean gameover;
    int lh, lw;
    final int fil = 5;
    final int col = 14;
    //
    int f1x[], f2x[], f3x[], f4x[], f5x[];
    int f1y[], f2y[], f3y[], f4y[], f5y[];
    //
    int destruidos;
    int cantidad = fil*col;
    //
    Color c[];

    public int getX(int fil, int col) {
        switch (fil) {
            case 0:
                return f1x[col];
            case 1:
                return f2x[col];
            case 2:
                return f3x[col];
            case 3:
                return f4x[col];
            case 4:
                return f5x[col];
        }
        return -1;
    }

    public void setX(int fil, int col, int x) {
        switch (fil) {
            case 0:
                f1x[col] = x;
                break;
            case 1:
                f2x[col] = x;
                break;
            case 2:
                f3x[col] = x;
                break;
            case 3:
                f4x[col] = x;
                break;
            case 4:
                f5x[col] = x;
                break;
        }
    }

    public int getY(int fil, int col) {
        switch (fil) {
            case 0:
                return f1y[col];
            case 1:
                return f2y[col];
            case 2:
                return f3y[col];
            case 3:
                return f4y[col];
            case 4:
                return f5y[col];
        }
        return -1;
    }

    public void setY(int fil, int col, int x) {
        switch (fil) {
            case 0:
                f1y[col] = x;
                break;
            case 1:
                f2y[col] = x;
                break;
            case 2:
                f3y[col] = x;
                break;
            case 3:
                f4y[col] = x;
                break;
            case 4:
                f5y[col] = x;
                break;
        }
    }

    public clsLadrillos(Graphics pintor, Color color, int w, int h) {
        this.w = w;
        this.h = h;
        this.pintor = pintor;
        this.color = color;
        this.destruidos = 0;
        lh = 20;
        lw = 60;
        f1x = new int[col];
        f1y = new int[col];
        f2x = new int[col];
        f2y = new int[col];
        f3x = new int[col];
        f3y = new int[col];
        f4x = new int[col];
        f4y = new int[col];
        f5x = new int[col];
        f5y = new int[col];
        c = new Color[fil];
        c[0] = Color.BLUE;
        c[1] = Color.RED;
        c[2] = Color.YELLOW;
        c[3] = Color.GREEN;
        c[4] = Color.BLACK;
        cantidad = 3 * 14;
        destruidos = 0;
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                this.setX(i, j, j * lw);
                this.setY(i, j, 80 + i * lh);
            }
        }
    }

    public void init() {
        this.gameover = false;
        lh = 20;
        lw = 60;
        f1x = new int[col];
        f1y = new int[col];
        f2x = new int[col];
        f2y = new int[col];
        f3x = new int[col];
        f3y = new int[col];
        f4x = new int[col];
        f4y = new int[col];
        f5x = new int[col];
        f5y = new int[col];
        c = new Color[fil];
        c = new Color[fil];
        c[0] = Color.BLUE;
        c[1] = Color.RED;
        c[2] = Color.YELLOW;
        c[3] = Color.GREEN;
        c[4] = Color.BLACK;
        cantidad = 3 * 14;
        destruidos = 0;
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                this.setX(i, j, j * lw);
                this.setY(i, j, 80 + i * lh);
            }
        }
    }

    @Override
    public void run() {
        while (true && !gameover) {
            dibujarLadrillos();
            try {
                sleep(50);
            } catch (Exception e) {
            }
        }
    }

    public void dibujarLadrillos() {
        for (int i = fil; i > 0; i--) {
            for (int j = 0; j < col; j++) {
                int x = getX(i - 1, j);
                int y = getY(i - 1, j);
                if (x != -1 && y != -1) {
                    this.pintor.setColor(c[i - 1]);
                    this.pintor.fillRect(x, y, lw - 1, lh - 1);
                    this.pintor.setColor(color);
                }
            }
        }
    }

}
