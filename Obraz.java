
import  java.util.Random;


public class Obraz  {

    private int size_n;
    private int size_m;
    private char[][] tab;
	private  char[] tab_symb;
    private int[] histogram;
    private  int [] histogram_pararrel;

	private final Object lock1 = new Object(); //zmana histogramu
	private final Object lock2 = new Object(); //wypisywanie



    public Obraz(int n, int m) {
	
	this.size_n = n;
	this.size_m = m;
	tab = new char[n][m];
	tab_symb = new char[94];
	
	final Random random = new Random();
	
	// for general case where symbols could be not just integers
	for(int k=0;k<94;k++) {
	    tab_symb[k] = (char)(k+33); // substitute symbols
	}

	for(int i=0;i<n;i++) {
	    for(int j=0;j<m;j++) {	
			tab[i][j] = tab_symb[random.nextInt(94)];  // ascii 33-127
			System.out.print(tab[i][j]+" ");
	    }
	    System.out.print("\n");
	}
	System.out.print("\n\n"); 
	
	histogram = new int[94];
	histogram_pararrel = new int[94];

   	clear_histograms();
    }


    //blokowo - wariant 2.
	public void calculate_part_histogram2(int start, int end){

    	char a;
    	for(int k=start;k<=end;k++) {
    		a = tab_symb[k];

			int result = 0;
			for (int i = 0; i < size_n; i++) {
				for (int j = 0; j < size_m; j++) {
					if (tab[i][j] == a) result++;
				}
			}
			synchronized (lock1) {
				histogram_pararrel[k] = result;
			}

		}
	}

	public void print_part_histogram2(int i){
		synchronized(lock2) {
			for (int l = 0; l < 94; l++) {

				int border = histogram_pararrel[l];
				if(border!=0)
					System.out.println("Thread " + i + " [" + tab_symb[l] + "] : " + border);
			}
		}
	}


	// watek dla kazdego znaku - wariant 1
    public void calculate_part_histogram(char a){
    	int result=0;
		for(int i=0;i<size_n;i++) {
			for (int j = 0; j < size_m; j++) {
				if(tab[i][j] == a) result++;
			}
		}
		synchronized (lock1){
			histogram_pararrel[(int)a - 33]=result;
		}

	}

	public void print_part_histogram(int i,char a){
    	synchronized(lock2) {
			System.out.print("Thread " + i + " [" + a + "] : ");
			for (int k = 0; k < histogram_pararrel[(int) a - 33]; k++) {
				System.out.print("=");
			}

			System.out.print("\n");

		}
	}


	// sekwencyjne
	public void calculate_histogram(){

		for(int i=0;i<size_n;i++) {
			for(int j=0;j<size_m;j++) {

				// optymalna wersja obliczania histogramu, wykorzystująca fakt, że symbole w tablicy
				// można przekształcić w indeksy tablicy histogramu
				// histogram[(int)tab[i][j]-33]++;

				// wersja bardziej ogólna dla tablicy symboli nie utożsamianych z indeksami
				// tylko dla tej wersji sensowne jest zrównoleglenie w dziedzinie zbioru znaków ASCII
				for(int k=0;k<94;k++) {
					if(tab[i][j] == tab_symb[k]) histogram[k]++;
					//if(tab[i][j] == (char)(k+33)) histogram[k]++;
				}

			}
		}

	}

	public void print_histogram(){

		for(int i=0;i<94;i++)
			if(histogram[i]!=0)
				System.out.print(tab_symb[i]+" "+histogram[i]+"\n");
	}

	//reszta
	public boolean check_histograms(){
		for(int i=0;i<94;i++)
			if(histogram[i] != histogram_pararrel[i]){
				System.out.println(tab_symb[i]+" "+histogram[i]+" "+histogram_pararrel[i]);
				return false;
			}

		return true;
	}

    public void clear_histograms(){

	for(int i=0;i<94;i++){
		histogram[i]=0;
		histogram_pararrel[i]=0;
	}

    }



}
