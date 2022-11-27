import java.io.File;
import java.io.FileNotFoundException;

public class MainClass {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        int p = Integer.parseInt(args[0]);
        Thread[] threads = new Thread[p];
        MyLinkedList linkedList = new MyLinkedList();
        MyQueue queue = new MyQueue(2000);
        File directoryPath = new File("polynomials");
        File[] filesList = directoryPath.listFiles();
        for(File file: filesList){
            String[] polynomial = Reader.readFromFile(file);
            Worker.addMonomialsToQueue(polynomial, queue);
        }

//        int k=1;
//        while(!queue.isEmpty()){
//            System.out.println(k + ": " + queue.popFromQueue());
//            k++;
//        }

        long startTime = System.nanoTime();
        for(int i=0; i<p-1; i++) {
            Thread t = new Thread(()->
            {
                while (!queue.isEmpty()){
                    String monomial="";
                    synchronized (queue) {
                        if(!queue.isEmpty()) {
                            monomial = queue.popFromQueue();
                        }
                    }
                    Worker.addMonomial(monomial, linkedList);
                }
            });
            threads[i] = t;
            t.start();
        }

        for(int i=0; i<p-1; i++){
            threads[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1E6);
    }
}
