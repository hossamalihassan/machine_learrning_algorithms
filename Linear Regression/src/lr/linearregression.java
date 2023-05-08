package lr;

import java.util.Scanner;

class LinearReg {
	
	float[] weights;
	int N, D;
	float[][] x;
	float[] y;
	float[] x_mean;
	float y_mean;
	float intercept_weight;
	
	LinearReg() {

	}
	
	public void train() {	
		// take inputs
		takeInputs();
		
		// calculate x and y mean
		calcMean();
		
		// calculate weights
		calcWeights();
		
		// calculate intercept weight
		calcInterceptWeight();
		
		// print output
		printWeights();
		
	}
	
	public void takeInputs() {
		Scanner sc = new Scanner(System.in);
		this.N = sc.nextInt();
		this.D = sc.nextInt();
		
		this.x = new float[N][D];
		this.y = new float[N];
		
		for(int i=0; i < N; i++) {
			for(int j=0; j < D; j++) {
				this.x[i][j] = sc.nextFloat();
			}
			this.y[i] = sc.nextFloat();
		}
	}
	
	public void calcMean() {
		this.x_mean = new float[D];
		
		// calculate x mean
		for(int i=0; i < this.D; i++) {
			for(int j=0; j < this.N; j++) {
				this.x_mean[i] += this.x[j][i];
			}
			this.x_mean[i] /= N;
		}

		
		// calculate y mean
		for(int i=0; i < this.N; i++) {
			this.y_mean += y[i];
		}
		this.y_mean /= this.N;
		
	}
	
	public void calcWeights() {
		this.weights = new float[D];

		for(int i=0; i < this.D; i++) {
			float[] sum_of_sqaures = getSS(i);
			float sum_prod = sum_of_sqaures[0];
			float x_sum_sq = sum_of_sqaures[1];
			float y_sum_sq = sum_of_sqaures[2];
			
			// calculate R
			float R = (float) ((sum_prod) / Math.sqrt(x_sum_sq * y_sum_sq));
			float Sy = (float) Math.sqrt(y_sum_sq / (N-1));
			float Sx = (float) Math.sqrt(x_sum_sq / (N-1));
			this.weights[i] = R * (Sy / Sx);
		}
	}
	
	public float[] getSS(int current_D) {
		float x_sum_sq = 0;
		float y_sum_sq = 0;
		float sum_prod = 0;
		for(int j=0; j < this.N; j++) {
			float x_mean_diff = this.x[j][current_D] - this.x_mean[current_D];
			float x_mean_diff_sq = (float) Math.pow(x_mean_diff, 2);
			x_sum_sq += x_mean_diff_sq;

			float y_mean_diff = y[j] - y_mean;
			float y_mean_diff_sq = (float) Math.pow(y_mean_diff, 2);
			y_sum_sq += y_mean_diff_sq;
			
			float x_y_diff = x_mean_diff * y_mean_diff;
			sum_prod += x_y_diff;
		}
		
		return new float[] {sum_prod, x_sum_sq, y_sum_sq};
	}
	
	public void calcInterceptWeight() {
		this.intercept_weight = this.y_mean;
		for(int i=0; i < this.D; i++) {
			this.intercept_weight -= weights[i] * x_mean[i];
		}
	}
	
	public void printWeights() {
		System.out.print(this.intercept_weight + " ");
		for(int i=0; i < this.D; i++) {
			System.out.print(this.weights[i] + " ");
		}
	}
	
}

public class linearregression {

	public static void main(String[] args) {
		LinearReg reg = new LinearReg();
		reg.train();
	}

}
