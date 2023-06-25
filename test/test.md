# Factor de equilibrio / Balance factor

El factor de equilibrio (bf) se basa en la diferencia de alturas entre subárbol izquierdo y el subárbol derecho del nodo. Este se utiliza para determinar si un nodo está balanceado o si se requieren rotaciones para mantener balanceado el árbol.

Sobre el algoritmo que implementamos (`insert` y `remove`), primero se realiza un recorrido hasta llegar al nodo afectado (en el bf), después se calcula la diferencia de alturas, esta diferencia se almacena en `bf` del nodo.

Ahora, dependiendo del valor se realiza la acción siguiente:

- Si el factor de balance es -1, 0 o 1, el nodo se considera balanceado y no se requieren ajustes.
- Si el factor de balance es -2 o 2, indica que el nodo está desbalanceado y se requieren rotaciones y ajustes para restaurar el balance.
- En el caso de una inserción, si el factor de balance es 2, se llama al método `balanceToLeft` para realizar rotaciones y ajustes hacia la izquierda.
- En el caso de una eliminación, si el factor de balance es -2, se llama al método `balanceToRight` para realizar rotaciones y ajustes hacia la derecha.
