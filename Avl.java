public class Avl<E extends Comparable<E>> {
    private Node<E> root;
    private boolean height;

    public Avl() {
        this.root = null;
    }

    public void insert(E x) throws ExceptionNoFound {
        this.root = insert(x, this.root); 
        this.height = false; //Cambios en la altura del arbol
    }

    private Node<E> insert(E x, Node<E> current) throws ExceptionNoFound {
        Node<E> res = current;
        if (current == null) { // hemos alcanzado una posición adecuada para insertar 
            res = new Node<E>(x);
            this.height = true;
        } else {
            
        }
        return res;
    }
}