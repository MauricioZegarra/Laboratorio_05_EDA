public class Avl<E extends Comparable<E>> {
    private Nodo<E> root;
    private boolean height;

    public Avl() {
        this.root = null;
    }

    public void insert(E x) throws ExceptionNoFound {
        this.root = insert(x, this.root); 
        this.height = false; //Cambios en la altura del arbol
    }

    private Nodo<E> insert(E x, Nodo<E> current) throws ExceptionNoFound {
        Nodo<E> res = current;
        if (current == null) { // hemos alcanzado una posición adecuada para insertar 
            res = new Nodo<E>(x);
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

    private NodoAvl<E> rotateRSR(NodoAvl<E> node) {
        NodoAvl<E> son = node.getLeft();
        node.setLeft(son.getRight());
        son.setRight(node);
        node = son;
        return node;
    }

    private NodoAvl<E> rotateRSL(NodoAvl<E> node) {
        NodoAvl<E> son = node.getRight();
        node.setRight(son.getLeft());
        son.setLeft(node);
        node = son;
        return node;
    }

    private NodoAvl<E> balanceToRight(NodoAvl<E> node) {
        NodoAvl<E> son = node.getLeft();
        if (son.getBf() == -1) {
            node.setBf(0);
            son.setBf(0);
            node = rotateRSR(node);
        } else if (son.getBf() == 1) {
            NodoAvl<E> gSon = son.getRight();
            switch (gSon.getBf()) {
                case 1:
                    node.setBf(0);
                    son.setBf(1);
                    break;
                case 0:
                    node.setBf(0);
                    son.setBf(0);
                    break;
                case -1:
                    node.setBf(-1);
                    son.setBf(0);
                    break;
            }
            gSon.setBf(0);

            node.setLeft(rotateRSL(son));
            node = rotateRSR(node);
        }
        return node;
    }
    
    private NodoAvl<E> balanceToLeft(NodoAvl<E> node) {
        NodoAvl<E> son = node.getRight();
        if (son.getBf() == 1) {
            node.setBf(0);
            son.setBf(0);
            node = rotateRSL(node);
        } else if (son.getBf() == -1) {
            NodoAvl<E> gSon = son.getLeft();
            switch (gSon.getBf()) {
                case -1:
                    node.setBf(0);
                    son.setBf(-1);
                    break;
                case 0:
                    node.setBf(0);
                    son.setBf(0);
                    break;
                case 1:
                    node.setBf(1);
                    son.setBf(0);
                    break;
            }
            gSon.setBf(0);

            node.setRight(rotateRSR(son));
            node = rotateRSL(node);
        }
        return node;
    }

}
