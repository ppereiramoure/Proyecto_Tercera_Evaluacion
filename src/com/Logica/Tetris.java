package com.Logica;


import Interfaz.Ventana;
import Piezas.LectorPuntucion;


public class Tetris {

    public static void main(String[] args) {
        Ventana ventana= new Ventana();
        ventana.setVisible(true);
        LectorPuntucion.leer();

    }
}

