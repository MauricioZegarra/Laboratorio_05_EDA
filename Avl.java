public class Avl<E extends Comparable<E>> {
    private Node<E> root;
    private boolean height;

    public Avl() {
        this.root = null;
    }

    public void insert(E x) throws ExceptionNoFound {
        this.root = insert(x, this.root); //Recien se implementara el insert real
        this.height = false; //Cambios en la altura del arbol
    }
}