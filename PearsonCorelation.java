
import java.util.ArrayList;
import java.util.List;

public class PearsonCorelation {
	List<Double> x = new ArrayList<Double>();
	List<Double> y = new ArrayList<Double>();
	double corelation = -1.0;
	double n = 0.0;

	PearsonCorelation(List<Double> x2, List<Double> y2) {
		if (x2.size() != y2.size()) {
			System.out.println("Invalid Lists : Should be of Equal Size");
		} else {
			this.x = x2;
			this.y = y2;
			n = x2.size();
			calculateCorelation();
		}
	}

	public double getCorelation() {
		return this.corelation;
	}

	public void calculateCorelation() {
		double sum_XY = this.sumXY();
		double sum_X = this.summation(this.x);
		double mean_X = sum_X / n;
		double sum_Y = this.summation(this.y);
		double mean_Y = sum_Y / n;
		double squareSumX = this.squareSummation(this.x);
		double squareSumY = this.squareSummation(this.y);
		double numerator = sum_XY - ((sum_X * sum_Y) / n);
		double denominator = (squareSumX - (Math.pow(sum_X, 2) / n)) * (squareSumY - (Math.pow(sum_Y, 2) / n));
		// double denominator = Math.pow(sum_X-mean_X, 2)
		if (denominator != 0.0) {
			this.corelation = numerator / Math.pow(denominator, 0.5);
		}
	}

	public double sumXY() {
		double sum_XY = 0.0;
		for (int i=0; i<x.size(); i++ ) {			
			sum_XY += x.get(i) * y.get(i);
		}
		return sum_XY;
	}

	public double summation(List<Double> x) {
		double summation = 0.0;
		for (double a : x) {
			summation += a;
		}
		return summation;
	}

	public double squareSummation(List<Double> x) {
		double squareSummation = 0.0;
		for (double a : x) {
			squareSummation += Math.pow(a, 2);
		}
		return squareSummation;
	}
}
