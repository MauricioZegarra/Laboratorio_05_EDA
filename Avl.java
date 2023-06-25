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
            int resC = current.getData().compareTo(x);
            if (resC == 0) {
                throw new ExceptionNoFound("Elemento ya se encuentra en el arbol");
            }
            if (resC < 0) {
                res.setRight(insert(x, current.getRight()));
                if (this.height) {
                    switch (res.getBf()) {
                        case -1:
                            res.setBf(0);
                            this.height = false;
                            break;
                        case 0:
                            res.setBf(1);
                            break;
                        case 1: 
                            res = balanceToLeft(res); //Rotación hacia la izquierda en el nodo "res"
                            this.height = false;
                            break;
                    }
                }

            } else { //Si es mayor a 0
                res.setLeft(insert(x, current.getLeft()));
                if (this.height) {
                    switch (res.getBf()) {
                        case 1:
                            res.setBf(0);
                            this.height = false;
                            break;
                        case 0:
                            res.setBf(-1);
                            break;
                        case -1:
                            res = balanceToRight(res); //Rotación hacia la derecha en el nodo "res"
                            this.height = false;
                            break;
                    }
                }
            }
        }
        return res;
    }
}