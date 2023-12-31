import org.w3c.dom.Node;

public class Avl<E extends Comparable<E>> {
    private Nodo<E> root;
    private boolean height;

    public Avl() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(E x) throws ExceptionNoFound {
        this.root = insert(x, this.root);
        this.height = false; // Cambios en la altura del arbol
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
                            res = balanceToLeft(res); // Rotación hacia la izquierda en el nodo "res"
                            this.height = false;
                            break;
                    }
                }

            } else { // Si es mayor a 0
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
                            res = balanceToRight(res); // Rotación hacia la derecha en el nodo "res"
                            this.height = false;
                            break;
                    }
                }
            }
        }
        return res;
    }

    private Nodo<E> rotateRSR(Nodo<E> node) {
        Nodo<E> son = node.getLeft();
        node.setLeft(son.getRight());
        son.setRight(node);
        node = son;
        return node;
    }

    private Nodo<E> rotateRSL(Nodo<E> node) {
        Nodo<E> son = node.getRight();
        node.setRight(son.getLeft());
        son.setLeft(node);
        node = son;
        return node;
    }

    private Nodo<E> balanceToRight(Nodo<E> node) {
        Nodo<E> son = node.getLeft();
        if (son.getBf() == -1) {
            node.setBf(0);
            son.setBf(0);
            node = rotateRSR(node);
        } else if (son.getBf() == 1) {
            Nodo<E> gSon = son.getRight();
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

    private Nodo<E> balanceToLeft(Nodo<E> node) {
        Nodo<E> son = node.getRight();
        if (son.getBf() == 1) {
            node.setBf(0);
            son.setBf(0);
            node = rotateRSL(node);
        } else if (son.getBf() == -1) {
            Nodo<E> gSon = son.getLeft();
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

    public Nodo<E> getRoot() {
        return this.root;
    }

    public E search(E x) throws ExceptionNoFound {
        Nodo<E> aux = search(x, this.root);
        if (aux == null) {
            throw new ExceptionNoFound("Elemento no se encuentra en el arbol");
        }
        return aux.getData();
    }

    public E getMax() throws ExceptionNoFound {
        if (isEmpty()) {
            throw new ExceptionNoFound("El árbol está vacío");
        }

        Nodo<E> maxNode = getMaxNode(root);
        return maxNode.getData();
    }

    private Nodo<E> getMaxNode(Nodo<E> current) {
        if (current.getRight() == null) {
            return current;
        }
        return getMaxNode(current.getRight());
    }

    public E getMin() throws ExceptionNoFound {
        if (isEmpty()) {
            throw new ExceptionNoFound("El árbol está vacío");
        }

        Nodo<E> minNode = getMinNode(root);
        return minNode.getData();
    }

    private Nodo<E> getMinNode(Nodo<E> current) {
        if (current.getLeft() == null) {
            return current;
        }
        return getMinNode(current.getLeft());
    }

    private Nodo<E> search(E x, Nodo<E> current) throws ExceptionNoFound {
        if (current == null) {
            return null;
        } else {
            int resC = current.getData().compareTo(x);
            if (resC == 0) {
                return current;
            }
            if (resC < 0) {
                return search(x, current.getRight());
            } else {
                return search(x, current.getLeft());
            }
        }
    }
    //  son busca si ambos elementos están presentes y  si a es padre de b, 
    //de lo contrario, devuelve false. 
    public boolean son(E a, E b) throws ExceptionNoFound {
        return son(a, b, this.root);
    }

    private boolean son(E a, E b, Nodo<E> current) throws ExceptionNoFound {
        if (current == null) {
            return false;
        }

        boolean aEncontrado = false;
        boolean bEncontrado = false;

        if (current.getData().equals(a)) {
            aEncontrado = true;
        } else if (current.getData().equals(b)) {
            bEncontrado = true;
        }

        if (aEncontrado && bEncontrado) {
            return true;
        }

        boolean hijoEnIzquierda = son(a, b, current.getLeft());
        boolean hijoEnDerecha = son(a, b, current.getRight());

        if (aEncontrado || bEncontrado || hijoEnIzquierda || hijoEnDerecha) {
            return true;
        }

        return false;
    }
    //metodo parent busca el padre mas cercano entre dos nodos
    public E parent(E a, E b) throws ExceptionNoFound {
    return parent(a, b, this.root);
    }

    private E parent(E a, E b, Nodo<E> current) throws ExceptionNoFound {
        if (current == null) {
            throw new ExceptionNoFound("Elemento no se encuentra en el árbol");
        }

        if ((current.getLeft() != null && current.getLeft().getData().equals(a) && current.getRight() != null && current.getRight().getData().equals(b))
                || (current.getRight() != null && current.getRight().getData().equals(a) && current.getLeft() != null && current.getLeft().getData().equals(b))) {
            return current.getData();
        }

        E parentEnIzquierda = parent(a, b, current.getLeft());
        E parentEnDerecha = parent(a, b, current.getRight());

        if (parentEnIzquierda != null) {
            return parentEnIzquierda;
        } else if (parentEnDerecha != null) {
            return parentEnDerecha;
        }

        return null;
    }
    public void remove(E x) throws ExceptionNoFound {
        this.root = remove(x, this.root);
        this.height = false; // Cambios en la altura del árbol
    }

    private Nodo<E> remove(E x, Nodo<E> current) throws ExceptionNoFound {
        if (current == null) {
            throw new ExceptionNoFound("Elemento no se encuentra en el árbol");
        }

        int resC = current.getData().compareTo(x);

        if (resC == 0) {
            // Caso 1: Nodo actual es el nodo a eliminar
            if (current.getLeft() == null && current.getRight() == null) {
                // El nodo actual no tiene hijos
                return null;
            } else if (current.getLeft() == null) {
                // El nodo actual tiene solo hijo derecho
                return current.getRight();
            } else if (current.getRight() == null) {
                // El nodo actual tiene solo hijo izquierdo
                return current.getLeft();
            } else {
                // El nodo actual tiene ambos hijos
                Nodo<E> successor = getMinNode(current.getRight());
                current.setData(successor.getData());
                current.setRight(remove(successor.getData(), current.getRight()));
            }
        } else if (resC < 0) {
            // Caso 2: Nodo a eliminar está en el subárbol izquierdo
            current.setLeft(remove(x, current.getLeft()));
            if (this.height) {
                current = balanceToRight(current);
            }
        } else {
            // Caso 3: Nodo a eliminar está en el subárbol derecho
            current.setRight(remove(x, current.getRight()));
            if (this.height) {
                current = balanceToLeft(current);
            }
        }

        return current;
    }

}

