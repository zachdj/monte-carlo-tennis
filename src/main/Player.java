package main;

import Jama.*;

public class Player {
    private String name; // The player's name (ued as visual identifier)
    private Matrix matrix; // The player's transition matrix
    private ServeStats serveStats; // The player's service stats

    public Player(String name){
        this.name = name;
        this.matrix = new Matrix(Settings.STATE_VECTOR_LENGTH, Settings.STATE_VECTOR_LENGTH);
        this.serveStats = new ServeStats(0.65, 0.10, 0.15, 0.025);
    }

    public Player(String name, double[][] matrix, ServeStats serveStats){
        this.name = name;
        this.matrix = new Matrix(matrix);
        this.serveStats = serveStats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public String toString(){
        String ret = this.name + "\n";

        double[][] matrix = this.matrix.getArray();
        for(int row=0; row < matrix.length; row++){
            for(int col=0; col < matrix[0].length; col++){
                ret += matrix[row][col] + " ";
            }
            ret += "\n";
        }
        return ret;
    }

    // Test class
//    public static void main(String[] args){
////        double[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        Player fed = new Player("Roger Federer");
//        System.out.println(fed.toString());
//    }
}
