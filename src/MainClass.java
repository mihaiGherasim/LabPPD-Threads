import java.io.File;
import java.io.FileNotFoundException;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {
        int p = 4;
        Thread[] threads = new Thread[p];
        MyLinkedList linkedList = new MyLinkedList();
        MyQueue queue = new MyQueue(2000);
        Worker worker = new Worker(queue);
        File directoryPath = new File("polynomials1");
        File[] filesList = directoryPath.listFiles();
        int numberOfFiles = filesList.length;
        final int[] currentFile = {1};
        Thread producer = new Thread(()->{
            for(File file: filesList){
                String[] polynomial;
                try {
                    polynomial = Reader.readFromFile(file);
                    synchronized (queue) {
                        worker.addMonomialsToQueue(polynomial, queue);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                synchronized (currentFile) {
                    currentFile[0]++;
                }
            }
        });
        producer.start();

        long startTime = System.nanoTime();
        for(int i=0; i<p-1; i++) {
            Thread t = new Thread(()->
            {
                while(queue.isEmpty() && currentFile[0] != numberOfFiles+1) {
                    synchronized (queue){
                        if(queue.isEmpty() && currentFile[0] != numberOfFiles+1) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            break;
                        }
                    }
                    while (!queue.isEmpty()) {
                        String monomial = "";
                        synchronized (currentFile) {
                                if (!queue.isEmpty() && currentFile[0] <= filesList.length+1) {
                                    monomial = queue.popFromQueue();
                                }
                        }
                        Worker.addMonomial(monomial, linkedList);
                    }
                }
            });
            threads[i] = t;
            t.start();
        }

        producer.join();
        for(int i=0; i<p-1; i++){
            threads[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1E6);
        linkedList.printList();
    }
}
