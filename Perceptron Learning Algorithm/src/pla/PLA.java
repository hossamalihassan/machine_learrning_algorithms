package pla;

import java.util.Scanner;

public class PLA {
    
    public static int D;
    public static int N;
    public static double learning_rate;
    public static int MAX_ITERATIONS;
    
    public static void printArr(float[] arr) {
		for(int i=0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.print("\n");
	}
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("N: ");
        N = sc.nextInt();
        System.out.print("D: ");
        D = sc.nextInt();
        System.out.print("Maxmium number of iteration : ");
        MAX_ITERATIONS = sc.nextInt();
        System.out.print("Learning rate : ");
        learning_rate = sc.nextDouble();

        // max and min inputs
        float max = Float.MIN_VALUE, min = Float.MAX_VALUE;
        
        // input
        System.out.println("Input: (X1...Xd label(-1,1))");
        float[][] x = new float[N][D];
        int[] labels = new int[N];      
        for(int i=0; i < N; i++) {
            for(int j=0; j < D; j++) {
                x[i][j] = sc.nextFloat();
                if(x[i][j] >= max) max = x[i][j];
                if(x[i][j] <= min) min = x[i][j]; 
            }
            labels[i] = sc.nextInt();
        }
        
        // getting the random initial weights
        Perceptron p = new Perceptron(D+1);
        float[] weights = p.weights;
        System.out.println("Initial Weights: ");
		for(int i=0; i < weights.length; i++) {
			System.out.print("w" + i + " = " + weights[i] + ", ");
		}
		System.out.print("\n");
        
        // creating the model
        int current=0;
        double global_error;
        do {
			System.out.println("------");
			System.out.println("Iteration No. " + (current+1));
            global_error = 0;
            for(int i=0; i < N; i++) {
            	System.out.println("Input: ");
				printArr(x[i]);
				System.out.println("Weight: ");
				printArr(weights);
                float net = p.calc(weights, x[i]);
                int label = p.sign(net);
                int error = labels[i] - label;
				System.out.println("Error: " + error);
                if(error != 0) {
                    weights = p.adjustWeight(weights, x[i], error, learning_rate);
                    global_error += error * error;
                    double MSE = Math.sqrt(global_error/N);
					System.out.println("MSE: " + MSE);
                }
    			System.out.println("------");
            } 
			System.out.println("------");
        } while(current++ < MAX_ITERATIONS && global_error != 0);
        
        if(global_error == 0 && current <= MAX_ITERATIONS) {
			System.out.println("Done after " + current + " iterations");
		} else {
			System.out.println("Need more iterations");
		}
        
        System.out.println("Final Weights");
        printArr(weights);
        
        // generating test data
		System.out.println("------");
        System.out.println("Test data: ");
        p.generateTestData(p, weights, min, max, N, D);
       
    }
}


class Perceptron {
    public float[] weights;
    
    public Perceptron(int D) {
        weights = new float[D];
        float min_weight = -1000;
        float max_weight = 1000;
        for(int i=0; i < weights.length; i++) {
            weights[i] = (float) (Math.random() * (max_weight-min_weight+1)) + min_weight;
        }
    }
    
    public float calc(float[] weights, float[] x) {
        float net = weights[weights.length-1];
        for(int i=0; i < x.length; i++) {
            net += weights[i] * x[i];
        }
        return net;
    }
    
    public int sign(float net) {
        return net >= 0 ? 1 : -1;
    }
    
    public float[] adjustWeight(float[] weights, float[] x, int error, double learning_rate) {
        for(int i=0; i < x.length; i++) {
            weights[i] += error * x[i] * learning_rate;
        }
        weights[weights.length-1] += error * learning_rate;
        return weights;
    }
    
    public float generateRandomNumbers(float min, float max) {
        return (float) ((Math.random() * (max-min+1)) + min);
    }
    
    public void generateTestData(Perceptron p, float[] weights, float min, float max, int N, int D) {
        float range = max - min;
        min -= (10*range) / 100; // step back 10 percent of the range
        max += (10*range) / 100; // step forward 10 percent of the range
        System.out.println("N = " + 2*N + " D = " + D);
        for(int i=0; i < 2*N; i++) {
            float[] test_x = new float[D];
            for(int j=0; j < test_x.length; j++) {
                test_x[j] = this.generateRandomNumbers(min, max);
        		System.out.print(test_x[j] + " ");
            }
            float net = p.calc(weights, test_x);
            int label = p.sign(net);
            System.out.print(label + "\n");
        }        
    }
}