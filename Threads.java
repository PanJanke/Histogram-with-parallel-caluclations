public class Threads extends Thread{
    int i;
    char a;
    Obraz obraz;

    public Threads( int i,char a,Obraz obraz){
        this.i=i;
        this.a=a;
        this.obraz=obraz;
    }

    public void run() {
        this.obraz.calculate_part_histogram(a);
        this.obraz.print_part_histogram(i,a);
    }
}
