public class MyLinkedList {

    private Node head;

    public MyLinkedList() {
        this.head = new Node(new Monomial(0, 99999));
        head.next = new Node(new Monomial(0, -1));
    }

    class Node {
        private Monomial data;
        private Node next;

        Node(Monomial monomial) {
            this.data = monomial;
        }
    }

    public void removeNode(Node node) {
        Node currentNode = this.head;
        if (node.data.grade == head.data.grade) {
            head.data = head.next.data;
            head.next = head.next.next;
        } else {
            while (currentNode != null) {
                if (currentNode.next != null && currentNode.next.data.grade == node.data.grade) {
                    Node nextNode = currentNode.next;
                    currentNode.next = nextNode.next;
                    currentNode.data = nextNode.data;
                }
                currentNode = currentNode.next;
            }
        }
    }

    public void insert(Monomial monomial) {
//        Node newNode = new Node(monomial);
//        if(this.head == null){
//            this.head = newNode;
//        }
//        else{
//            Node last = this.head;
//            while (last.next != null && last.next.data.grade > monomial.grade){
//                last = last.next;
//            }
//            if(last.next == null){
//                if(last.data.grade > monomial.grade) {
//                    last.next = newNode;
//                }
//                else if(last.data.grade < monomial.grade){
//                    Monomial mon = new Monomial(last.data.value, last.data.grade);
//                    newNode.next = new Node(mon);
//                    this.head = newNode;
//                }
//                else{
//                    last.data.value+= monomial.value;
//                    if(last.data.value == 0){
//                        removeNode(head);
//                    }
//                }
//            }
//            else {
//                if (last.next.data.grade == monomial.grade) {
//                    last.next.data.value += monomial.value;
//                    if(last.next.data.value == 0){
//                        removeNode(last.next);
//                    }
//                } else {
//                    if(this.head == last){
//                        if(last.data.grade < monomial.grade) {
//                            Monomial mon = new Monomial(last.data.value, last.data.grade);
//                            Node newOldNode = new Node(mon);
//                            newOldNode.next = last.next;
//                            newNode.next = newOldNode;
//                            this.head = newNode;
//                        }
//                        else if(last.data.grade > monomial.grade){
//                            newNode.next = last.next;
//                            this.head.next = newNode;
//                        }
//                        else{
//                            last.data.value+= monomial.value;
//                            if(last.data.value == 0){
//                                removeNode(last);
//                            }
//                        }
//                    }
//                    else {
//                        Node oldNextNode = last.next;
//                        last.next = newNode;
//                        newNode.next = oldNextNode;
//                    }
//                }
//            }
//        }

        Node newNode = new Node(monomial);
        Node last = this.head;
        synchronized (last) {
            synchronized (last.next) {
                while (last.next.data.grade > monomial.grade) {
                    last = last.next;
                }
            }
        }

        if (last.next.data.grade == monomial.grade) {
            synchronized (last){
                synchronized (last.next) {
                    last.next.data.value += monomial.value;
                    if (last.next.data.value == 0) {
                        removeNode(last.next);
                    }
                }
            }
        }
        else {
            synchronized (last) {
                synchronized (last.next) {
                    Node oldNextNode = last.next;
                    last.next = newNode;
                    newNode.next = oldNextNode;
                }
            }
        }

//        if (last.next == null) {
//            if (last.data.grade > monomial.grade) {
//                synchronized (last){
//                    last.next = newNode;
//                }
//
//            } else if (last.data.grade < monomial.grade) {
//                synchronized (last){
//                    Monomial mon = new Monomial(last.data.value, last.data.grade);
//                    newNode.next = new Node(mon);
//                    this.head = newNode;
//                }
//            } else {
//                synchronized (last){
//                    synchronized (last.next) {
//                        last.data.value += monomial.value;
//                        if (last.data.value == 0) {
//                            removeNode(head);
//                        }
//                    }
//                }
//            }
//        } else {
//
//        }
    }

    public void printList(){
        Node currentNode = this.head;
        System.out.println("MyLinkedList: ");
        while (currentNode != null){
            if(currentNode.data.grade != 99999 && currentNode.data.grade != -1) {
                System.out.print(" +(" + currentNode.data + ")");
            }
            currentNode = currentNode.next;
        }
    }
}
