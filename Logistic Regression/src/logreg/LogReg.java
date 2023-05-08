package logreg;

import java.util.Scanner;

class Model {
	float[] weights;
	float[] outputs;
	int D, N;
	
	Model(int N, int D) {
		this.N = N;
		this.D = D;
		this.weights = new float[D+1];
		this.outputs = new float[N];
	}
	
	public void train(float[][] x, float[] labels) {				
		// GD
		float learning_rate = (float) 0.16;
		gradientDescent(x, labels, learning_rate);
		
		for(int j=0; j < this.D+1; j++) {
			System.out.print(this.weights[j] + " ");
		}
	}
	
	public void calcFx(float[][] x) {
		for(int i=0; i < this.N; i++) {
			float fx = this.weights[0];
			for(int j=1; j < this.D+1; j++) {
				fx += x[i][j-1] * this.weights[j];
			}
			this.outputs[i] = fx;
		}
	}
	
	public void calcSigFx() {
		for(int i=0; i < this.N; i++) {
			this.outputs[i] = (float) (1 / (1 + Math.exp(-this.outputs[i])));
		}
	}
	
	public void labelOutputs() {
		for(int i=0; i < this.N; i++) {
			if(this.outputs[i] >= 0.50) {
				this.outputs[i] = 1;
			} else {
				this.outputs[i] = -1;
			}
		}
	}
	
	public void gradientDescent(float[][] x, float[] y, float learning_rate) {
		int itrs = 50;
		while(itrs-- > 0) {
			calcFx(x);
			calcSigFx();
			labelOutputs();
			
			// calculate new intercept
			float gradient = 0;
			for(int i=0; i < this.N; i++) {
				gradient += this.outputs[i] - y[i];;
			}
			this.weights[0] -= ( learning_rate ) * gradient;
			
			// calculate new weights
			for(int i=0; i < this.N; i++) {
				gradient = this.outputs[i] - y[i];
				for(int j=0; j < this.D; j++) {
					float new_weight = gradient * x[i][j];
					this.weights[j+1] -= ( learning_rate ) * new_weight;
				}
			}
		}
	}
}

public class LogReg {

	static int N;
	static int D;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		D = sc.nextInt();
		float[][] x = new float[N][D];
		float[] labels = new float[N];
		for(int i=0; i < N; i++) {
			for(int j=0; j < D; j++) {
				x[i][j] = sc.nextFloat();
			}
			labels[i] = sc.nextFloat();
		}
		
		Model model = new Model(N, D);
		model.train(x, labels);
	}

}
