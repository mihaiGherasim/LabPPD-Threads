public class MyLinkedList{
    private Node head;
    class Node{
        private Monomial data;
        private Node next;

        Node(Monomial monomial){
            this.data = monomial;
        }
    }

    public void insert(Monomial monomial){
        Node newNode = new Node(monomial);
        if(this.head == null){
            this.head = newNode;
        }
        else{
            Node last = this.head;
            while (last.next != null && last.next.data.grade > monomial.grade){
                last = last.next;
            }
            if(last.next == null){
                if(last.data.grade > monomial.grade) {
                    last.next = newNode;
                }
                else if(last.data.grade < monomial.grade){
                    Monomial mon = new Monomial(last.data.value, last.data.grade);
                    newNode.next = new Node(mon);
                    this.head = newNode;
                }
                else{
                    last.data.value+= monomial.value;
                }
            }
            else {
                if (last.next.data.grade == monomial.grade) {
                    last.next.data.value += monomial.value;
                } else {
                    if(this.head == last){
                        if(last.data.grade < monomial.grade) {
                            Monomial mon = new Monomial(last.data.value, last.data.grade);
                            Node newOldNode = new Node(mon);
                            newOldNode.next = last.next;
                            newNode.next = newOldNode;
                            this.head = newNode;
                        }
                        else if(last.data.grade > monomial.grade){
                            newNode.next = last.next;
                            this.head.next = newNode;
                        }
                        else{
                            last.data.value+= monomial.value;
                        }
                    }
                    else {
                        Node oldNextNode = last.next;
                        last.next = newNode;
                        newNode.next = oldNextNode;
                    }
                }
            }
        }
    }

    public void printList(){
        Node currentNode = this.head;
        System.out.println("MyLinkedList: ");
        while (currentNode != null){
            System.out.print(" +("+currentNode.data +")");
            currentNode = currentNode.next;
        }
    }

}
