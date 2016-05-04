package main;

import Jama.*;

public class Player {
    private String name;
    private Matrix matrix;

    public Player(String name){
        this.name = name;
        this.matrix = new Matrix(Settings.STATE_VECTOR_LENGTH, Settings.STATE_VECTOR_LENGTH);
    }

    public Player(String name, double[][] matrix){
        this.name = name;
        this.matrix = new Matrix(matrix);
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

    public static void main(String[] args){
        double[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Player fed = new Player("Roger Federer", matrix);
        System.out.println(fed.toString());
    }
}
