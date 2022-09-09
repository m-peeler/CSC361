package Matrix;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main (String args[]) {
		String s = "5.0";
		Integer in = Integer.parseInt(s);
		System.out.println(in);
		
		Timer timer = new Timer();
		List<Double> lst = new ArrayList<Double>();
		for (int i = 200; i <= 1800; i+= 25) {
			lst.add(Math.random());
			long time;
			time = 0;
			Matrix test1 = new Matrix(i,i);
			Matrix test2 = new Matrix(lst, i, i);

			time = 0;
			for (int j = 0; j < 10; j++) {
				timer.start();
				test1.plus(test2);
				time += timer.stop();
			}
			System.out.println(time);
		}
		
		System.out.println("HHH");
		for (int i = 200; i <= 1800; i+= 25) {
			lst.add(Math.random());
			long time;
			time = 0;
			Matrix test1 = new Matrix(i,i);
			Matrix test2 = new Matrix(lst, i, i);

			time = 0;
			for (int j = 0; j < 2; j++) {
				timer.start();
				test1.times(test2);
				time += timer.stop();
			}
			System.out.println(time);

			//time = 0;
			//for (int j = 0; j < 100; j++) {
				//timer.start();
				//test1.times(test2);
				//time += timer.stop();
			//}
			//System.out.println(i + " x " + i + " Times: " + time/10 + ", Total: " + time);
			
			//System.out.println();
		}
	}
}
