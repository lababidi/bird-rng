import java.security.MessageDigest;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Created by mahmoud on 2/25/15.
 */
public class RandomGenerator {

    MessageDigest md;
    Streaming streaming;
    BlockingQueue<Byte> randomBytes = new LinkedBlockingQueue<>(10000);
    HashMap<Integer, Integer> statistics;
    Thread streamingThread;

    public RandomGenerator() {

        streaming = new Streaming(randomBytes);
        streamingThread = new Thread(new Streaming(randomBytes));
        statistics = new HashMap<>();

    }

    public void build(){

        int total = 0;

        streamingThread.start();
        int count = 0;
        while(streamingThread.isAlive()) {
            try {

                int n = 256;
                total++;
                byte b = randomBytes.take();
                int r = b & 0xFF;
                count = statistics.containsKey(r)?statistics.get(r)+1 : 1;
                statistics.put(r,count);
                double mean = 0, stdDev = 0;
                for(int v:statistics.values())
                    mean += v;
                mean /= n;
                for(int v:statistics.values())
                    stdDev += (v - mean)*(v - mean);
                stdDev = Math.sqrt(stdDev/n);
                System.out.print(stdDev/mean);
                System.out.println(" "+stdDev/73.9+" "+stdDev+" "+mean + " "+ 256*mean/total);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        RandomGenerator generator = new RandomGenerator();
        generator.build();

    }
}
