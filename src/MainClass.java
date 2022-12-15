import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {
        int p = 4;
        Thread[] threadsConsumers = new Thread[p];
        Thread[] threadsProducers = new Thread[p];
        MyLinkedList linkedList = new MyLinkedList();
        BlockingHelper blockingHelper = new BlockingHelper();
        MyQueue queue = new MyQueue(2000);
        Worker worker = new Worker(queue, blockingHelper);
        File directoryPath = new File("polynomials1");
        File[] filesList = directoryPath.listFiles();
        int numberOfFiles = filesList.length;
        final int[] currentFile = {1};
        final ArrayList<ArrayList<File>> filesForThreads = new ArrayList<>();
        int whole = numberOfFiles / (p-1);
        int rest = numberOfFiles % (p-1);
        int start = 0;
        int end = whole;
        for(int i = 0; i < p-1; i++){
            if(rest > 0){
                end++;
                rest--;
            }
            ArrayList<File> files = new ArrayList<>(Arrays.asList(filesList).subList(start, end));
            filesForThreads.add(files);
            start = end;
            end = start + whole;
        }
        for(int  i = 0; i < p-1; i++){
            final int finalI = i;
            Thread producer = new Thread(()->{
                for(File file: filesForThreads.get(finalI)){
                    String[] polynomial;
                    try {
                        polynomial = Reader.readFromFile(file);
                        synchronized (blockingHelper) {
                            synchronized (queue) {
                                for (String monomial : polynomial) {
                                    if (queue.isFull()) {
                                        blockingHelper.wait();
                                    }
                                    worker.addMonomialToQueue(monomial, queue);
                                }
                            }
                        }
                    } catch (FileNotFoundException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (currentFile) {
                        currentFile[0]++;
                    }
                }
            });
            threadsProducers[i] = producer;
            producer.start();
        }

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
                                //monomial = queue.popFromQueue();
                                monomial = worker.popFromQueue();
                            }
                        }
                        Worker.addMonomial(monomial, linkedList);
                    }
                }
            });
            threadsConsumers[i] = t;
            t.start();
        }

        for(int i=0; i<p-1; i++){
            threadsConsumers[i].join();
            threadsProducers[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1E6);
        linkedList.printList();
    }
}
