
import java.util.Scanner;


class Histogram_test {
    
    public static void main(String[] args) {


    	// * SEKWENCYJNIE *
	Scanner scanner = new Scanner(System.in);
	
	System.out.println("Set image size: n (#rows), m(#kolumns)");
	int n = scanner.nextInt();
	int m = scanner.nextInt();
	Obraz obraz_1 = new Obraz(n, m);

	obraz_1.calculate_histogram();
	obraz_1.print_histogram();





			//WARIANT 2 BLOKOWO

		System.out.println("Set number of threads");
		int num_threads = scanner.nextInt();
		int tasks;
		tasks = (int)Math.ceil( 94.0/(double) num_threads );
		System.out.println("Number of tasks signed to one thread:" + tasks);

		Runnable[] runnable = new Runnable[num_threads];
		Thread[] RealThreads = new Thread[num_threads];



		//signed blocks of tasks to threads
		int start=0;
		int end=tasks;
		int jump=tasks;
		for (int i = 0; i < num_threads; i++) {

			runnable[i] = new Runnable(i,start,end-1,obraz_1);
			RealThreads[i] =  new Thread(runnable[i]);
			RealThreads[i].start();
			start=end;
			end=end+jump;

			if(end>94)
				end=94;
		}

		for (int i = 0; i < num_threads; i++) {
			try {
				RealThreads[i].join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	 if(obraz_1.check_histograms())
	 	System.out.println("Program works correctly");
	 else
		 System.out.print("ERROR");


    }

}

