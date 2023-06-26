public class Nodo<E> {

    private E data;
    private Nodo<E> left;
    private Nodo<E> right;
    private int bf;

    public Nodo(E data, Nodo<E> left, Nodo<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.bf = 0;
    }

    public int getBf() {
        return bf;
    }

    public void setBf(int bf) {
        this.bf = bf;
    }

    public Nodo(E data) {
        this(data, null, null);
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Nodo<E> getLeft() {
        return this.left;
    }

    public void setLeft(Nodo<E> left) {
        this.left = left;
    }

    public Nodo<E> getRight() {
        return this.right;
    }

    public void setRight(Nodo<E> right) {
        this.right = right;
    }

    public String toString() {
        return this.data.toString() + "(" + this.bf + ")";
    }
}
