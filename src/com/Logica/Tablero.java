package com.Logica;

import Piezas.Pieza;
import Piezas.PiezaFactory;
import Piezas.Tile;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablero {
    Connection con;


    public Tile[][] campo;
    public Pieza pieza;
    int puntuacion;
    int level;
    int delay;
    Random generadorAleatorio;

    public Tablero() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
        generadorAleatorio = new Random();
        level = 0;
        puntuacion = 0;
        campo = new Tile[10][20];
        try {
            generarNuevaPieza();

        } catch (Exception e) {

        }
    }

    private void generarNuevaPieza() {
        try {
            pieza = PiezaFactory.getPieza(generadorAleatorio.nextInt(6));
        } catch (Exception e) {

        }
        pieza.moverDerecha();
    }

    private void colocarPieza() {
        for (var coordenada : pieza.coordenada) {
            int x = coordenada[0];
            int y = coordenada[1];
            if (y < 0) {
                partidaFinalizada();
            }
            campo[x][y] = pieza.tile;
        }
        generarNuevaPieza();
    }

    private boolean colisionar(int x, int y) {
        try {
            return x > campo.length - 1 || y > campo[0].length - 1 || campo[x][y] != null;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    private void comprobarLinea() {
        boolean completa;
        for (int j = 0; j < campo[0].length; j++) {//numero de fila (segunda coordenada)
            completa = true;
            for (int i = 0; i < campo.length; i++) {
                if (campo[i][j] == null) {
                    completa = false;
                    break;
                }
            }
            if (completa) {
                moverPosiciones(j);
            }
        }
    }

    private void moverPosiciones(int fila) {
        Tile[][] nuevoCampo = transposeArray(campo);
        for (int i =fila-1; i >0; i--) {
            nuevoCampo[i+1]=nuevoCampo[i];

        }
        campo = transposeArray(nuevoCampo);
        puntuacion++;
    }

    public void update() {
        if (!colisionarPieza(0, 1)) {
            pieza.moverAbajo();
        } else {
            colocarPieza();
            comprobarLinea();
        }
    }

    private boolean colisionarPieza(int desplazamientox, int desplazamientoy) {
        boolean colisiona = false;
        for (var coordenada : pieza.coordenada) {
            if (colisionar(coordenada[0] + desplazamientox, coordenada[1] + desplazamientoy)) {
                colisiona = true;
            }
        }
        return colisiona;
    }

    public void moverPiezaDerecha() {
        if (!colisionarPieza(1, 0)) {
            pieza.moverDerecha();
        }
    }

    public void moverPiezaIzquierda() {
        if (!colisionarPieza(-1, 0)) {
            pieza.moverIzquierda();
        }
    }

    public void rotarPieza() {
        pieza.rotar();
        if (colisionarPieza(0, 0)) {
            pieza.rotar();
            pieza.rotar();
            pieza.rotar();
        }
    }

    public void moverPiezaAbajo() {
        if (!colisionarPieza(0, 1)) {
            pieza.moverAbajo();
        }
    }

    public static Tile[][] transposeArray(Tile[][] array) {
        int m = array.length;
        int n = array[0].length;

        Tile[][] transposedArray = new Tile[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                transposedArray[x][y] = array[y][x];
            }
        }

        return transposedArray;
    }

    public  String printCampo() {
        int m = campo.length;
        int n = campo[0].length;

        String tmp = "";
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                tmp = tmp + campo[y][x] + " ";
            }
            tmp = tmp + "\n";
        }

        return tmp;
    }

    private void partidaFinalizada() {




        JOptionPane.showMessageDialog(null, "GAME OVER \n \n Tu puntuación es:" + puntuacion);
        //pedir nombre
        String nombre = JOptionPane.showInputDialog(null, "Cual es tu nombre?");
        //guardar nombre a un fichero

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/puntaje","root","");

            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO records VALUES('"+nombre+"','"
                   +puntuacion+"')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        System.exit(0);



    }
}