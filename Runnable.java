public class Runnable implements java.lang.Runnable {
    int i;
    int start;
    int end;
    Obraz obraz;

    public Runnable(int i, int start, int end, Obraz obraz){
        this.i=i;
        this.start=start;
        this.end=end;
        this.obraz=obraz;
    }

    public void run() {
        this.obraz.calculate_part_histogram2(start,end);
        this.obraz.print_part_histogram2(i);
        System.out.println("Thread:" + i + " Range " + start + "-" + end);

    }
}
