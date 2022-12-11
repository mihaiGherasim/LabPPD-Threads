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
        synchronized (node.next) {
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
    }

    public void insert(Monomial monomial) {
        Node newNode = new Node(monomial);
        Node last = this.head;
        synchronized (last) {
            synchronized (last.next) {
                while (last.next.data.grade > monomial.grade) {
                    last = last.next;
                }

                if (last.next.data.grade == monomial.grade) {
                    last.next.data.value += monomial.value;
                    if (last.next.data.value == 0) {
                        removeNode(last.next);
                    }
                } else {
                    Node oldNextNode = last.next;
                    last.next = newNode;
                    newNode.next = oldNextNode;
                }
            }
        }
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
