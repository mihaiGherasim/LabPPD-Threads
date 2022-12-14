import java.util.LinkedList;

public class Worker {
    public static void addPolynomial(String[] polynomial, MyLinkedList linkedList){
        for(String monomial:polynomial){
            int value = 0;
            int grade;
            if (!monomial.contains("^") && monomial.contains("x")) {
                String[] valueAsString = monomial.split("x");
                if(valueAsString[0].length() > 1){
                    value = Integer.parseInt(valueAsString[0]);
                }
                else if(valueAsString[0].length() == 1){
                    if(valueAsString[0].equals("-")){
                        value = -1;
                    }
                    else value = 1;
                }
                grade = 1;
            }
            else if(!monomial.contains("x^")){
                value = Integer.parseInt(monomial);
                grade = 0;
            }
            else {
                String[] valueGrade = monomial.split("x\\^");
                if(valueGrade[0].length() >= 1 && !valueGrade[0].equals("-") && !valueGrade[0].equals("+")){
                    value = Integer.parseInt(valueGrade[0]);
                }
                else if(valueGrade[0].length() == 1){
                    if(valueGrade[0].equals("-")){
                        value = -1;
                    }
                    else value = 1;
                }
                grade = Integer.parseInt(valueGrade[1]);
            }
            Monomial newMonomial = new Monomial(value, grade);
            linkedList.insert(newMonomial);
        }
    }

    public static void addMonomialsToQueue(String[] polynomial, MyQueue queue){
        for(String monomial : polynomial){
            queue.pushToQueue(monomial);
        }
    }

    public static void addMonomial(String monomial, MyLinkedList linkedList){
        int value = 0;
        int grade;
        if (!monomial.contains("^") && monomial.contains("x")) {
            String[] valueAsString = monomial.split("x");
            if(valueAsString[0].length() > 1){
                value = Integer.parseInt(valueAsString[0]);
            }
            else if(valueAsString[0].length() == 1){
                if(valueAsString[0].equals("-")){
                    value = -1;
                }
                else value = 1;
            }
            grade = 1;
        }
        else if(!monomial.contains("x^")){
            value = Integer.parseInt(monomial);
            grade = 0;
        }
        else {
            String[] valueGrade = monomial.split("x\\^");
            if(valueGrade[0].length() >= 1 && !valueGrade[0].equals("-") && !valueGrade[0].equals("+")){
                value = Integer.parseInt(valueGrade[0]);
            }
            else if(valueGrade[0].length() == 1){
                if(valueGrade[0].equals("-")){
                    value = -1;
                }
                else value = 1;
            }
            grade = Integer.parseInt(valueGrade[1]);
        }
        Monomial newMonomial = new Monomial(value, grade);
//        synchronized (linkedList){
            linkedList.insert(newMonomial);
//        }
    }
}
