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
        return this.name + "\n" + this.matrix.toString();
    }
}
