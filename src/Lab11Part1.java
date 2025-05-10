import java.io.IOException;

public class Lab11Part1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Watering.process();
        Watering.processLog();
        System.out.println(Watering.processLog());
    }
}
