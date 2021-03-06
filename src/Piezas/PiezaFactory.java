package Piezas;


import java.awt.*;

public class PiezaFactory {
    /**
     * Empregamos a funcion enum para añadirlle valores e cor as constantes, neste caso as nosas pezas
     */
    enum tipoPiezas {
       L (plantilla_de_coordenadas[0], new Tile(new Color(20, 43, 147))),
       L_Inverso (plantilla_de_coordenadas[1], new Tile(new Color(194, 6, 9))),
       S (plantilla_de_coordenadas[2], new Tile(new Color(8, 147, 11))),
       Cuadrado (plantilla_de_coordenadas[3], new Tile(new Color(229, 0, 230))),
       Linea (plantilla_de_coordenadas[4], new Tile(new Color(0, 232, 236))),
       Z (plantilla_de_coordenadas[5], new Tile(new Color(226, 214, 0))),
       T (plantilla_de_coordenadas[6], new Tile(new Color(107, 51, 255))),
       ;
        int [][] coordenadas;
        Tile tile;
       tipoPiezas(int [][] coordenadas, Tile tile ) {
           this.coordenadas=coordenadas;
           this.tile=tile;
       }
   }

   /**
    * Plantilla de coordenadas para determinar as formas das pezas
    */

   public static final int[][][] plantilla_de_coordenadas = new int[][][] {

        // L
        { {-1,-1 }, { 0,-1 }, { 0, 0 }, { 0, 1 } },

        // L inverso
        { { 1,-1 }, { 0,-1 }, { 0, 0 }, { 0, 1 } },

        // S
        { { 0,-1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },

        // Cuadrado
        { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } },

        // Linea
        { { 0,-1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } },

        // Z
        { {-1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },

        // T
        { { 0,-1 }, { 0, 0 }, {-1, 0 }, {-1, 1 } }
    };

    public static Pieza getPieza(int criteria) throws Exception {

        /**
         * Switch para chamar as pezas
         */

         switch (criteria) {
             case 0:
                return new Pieza((tipoPiezas.L.coordenadas), tipoPiezas.L.tile);
            case 1:
               return new Pieza((tipoPiezas.L_Inverso.coordenadas), tipoPiezas.L_Inverso.tile);
            case 2:
               return new Pieza((tipoPiezas.S.coordenadas), tipoPiezas.S.tile);
            case 3:
                return new Pieza((tipoPiezas.Cuadrado.coordenadas), tipoPiezas.Cuadrado.tile);
            case 4:
               return new Pieza((tipoPiezas.Linea.coordenadas), tipoPiezas.Linea.tile);
             case 5:
                 return new Pieza((tipoPiezas.Z.coordenadas), tipoPiezas.Z.tile);
            case 6:
               return new Pieza((tipoPiezas.T.coordenadas), tipoPiezas.T.tile);
             default:
                 throw new Exception("valor no valido, el valor de la pieza debe estar entre 0 y 6");
        }
    }
}
