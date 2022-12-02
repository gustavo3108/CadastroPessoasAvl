
import java.util.ArrayList;

class AVLTree {

    private Node root;

    private class Node {

        private int index;
        private long element;
        private int height;

        private Node left;
        private Node right;

        private Node(long element, int index) {
            this.element = element;
            this.index = index;
            this.height = 1;
        }

        @SuppressWarnings("unused")
        public int getIndex() {
            return index;
        }
    }

    /* insert a node into the tree with index */
    public void insert(long element, int index) {
        root = insert(root, element, index);
    }

    private Node insert(Node node, long element, int index) {

        if (node == null) {
            //	System.out.println("O elemento " + element + " foi inserido com sucesso na  rvore.\n");

            return (new Node(element, index));
        }

        if (element < node.element) {
            node.left = insert(node.left, element, index);
        } else if (element > node.element) {
            node.right = insert(node.right, element, index);
        } else {
            System.out.println("O elemento " + element + " j  existe na  rvore.\n");
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (element < node.left.element) {
                return rightRotate(node);
            } else if (element > node.left.element) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        if (balanceFactor < -1) {
            if (element > node.right.element) {
                return leftRotate(node);
            } else if (element < node.right.element) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    /* delete an element from the tree */
    public void delete(long element) {
        boolean existeOElemento = busca(element);
        if (!existeOElemento) {
            System.out
                    .println("O elemento " + element + " n o existe na  rvore, e, portanto, n o pode ser deletado.\n");
        } else {
            root = delete(root, element);
            System.out.println("O elemento " + element + " foi removido da  rvore com sucesso.\n");

        }
    }

    private Node delete(Node root, long element) {

        if (root == null) {
            return root;
        }

        if (element < root.element) {
            root.left = delete(root.left, element);
        } else if (element > root.element) {
            root.right = delete(root.right, element);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node tmp = null;
                if (tmp == root.left) {
                    tmp = root.right;
                } else {
                    tmp = root.left;
                }

                if (tmp == null) {
                    tmp = root;
                    root = null;
                } else {
                    root = tmp;
                }
            } else {
                Node tmp = nodeWithMinValue(root.right);

                root.element = tmp.element;

                root.right = delete(root.right, tmp.element);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        int balanceFactor = getBalanceFactor(root);

        if (balanceFactor > 1) {
            if (getBalanceFactor(root.left) >= 0) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(root.right) <= 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }
        return root;
    }

    /* pretty print the tree */
    public void print() {
        if (root == null) {
            System.out.println("A  arvore esta  vazia.\n");
        } else {
            System.out.println("\nEstrutura da  arvore AVL: \n");
            print(root, "", false);
        }
    }

    private void print(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            System.out.println(node.element);

            print(node.left, indent, false);
            print(node.right, indent, true);
        }
    }

    private Node rightRotate(Node q) {
        Node p = q.left;
        Node aux = p.right;

        p.right = q;
        q.left = aux;

        q.height = max(height(q.left), height(q.right)) + 1;
        p.height = max(height(p.left), height(p.right)) + 1;

        return p;
    }

    private Node leftRotate(Node p) {
        Node q = p.right;
        Node aux = q.left;

        q.left = p;
        p.right = aux;

        p.height = max(height(p.left), height(p.right)) + 1;
        q.height = max(height(q.left), height(q.right)) + 1;

        return q;
    }

    private int getBalanceFactor(Node node) {

        if (node == null) {
            return 0;
        }

        return height(node.left) - height(node.right);
    }

    private int height(Node node) {

        if (node == null) {
            return 0;
        }

        return node.height;
    }

    private Node nodeWithMinValue(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    private int max(int e1, int e2) {
        return (e1 > e2) ? e1 : e2;
    }

    public boolean busca(long element) {
        return busca(root, element);
    }

    private boolean busca(Node node, long element) {

        if (node == null) {
            return false;
        } else if (element == node.element) {

            return true;
        } else if (element < node.element) {
            return busca(node.left, element);
        } else {
            return busca(node.right, element);
        }

    }

    public int buscaIndexDataNasc(long element) {
        return buscaIndexDataNasc(root, element);
    }

    private int buscaIndexDataNasc(Node node, long element) {

        if (node == null) {
            return -1;
        } else if (element == node.element) {
            return node.index;
        } else if (element < node.element) {
            return buscaIndexDataNasc(node.left, element);
        } else {
            return buscaIndexDataNasc(node.right, element);
        }

    }

    public ArrayList<Integer> buscarIndexDeVariasDatasDeNascimento(long startDate, long endDate) {
        ArrayList<Integer> listaDeIndex = new ArrayList<>();

        return buscarIndexDeVariasDatasDeNascimento(root, startDate, endDate, listaDeIndex);
    }

    private ArrayList<Integer> buscarIndexDeVariasDatasDeNascimento(Node node, long startDate, long endDate,
                                                                    ArrayList<Integer> listaDeIndex) {
        if (node.element >= startDate && node.element <= endDate) {
            listaDeIndex.add(node.index);
        }
        if (node.left != null) {
            buscarIndexDeVariasDatasDeNascimento(node.left, startDate, endDate, listaDeIndex);
        }
        if (node.right != null) {
            buscarIndexDeVariasDatasDeNascimento(node.right, startDate, endDate, listaDeIndex);
        }
        return listaDeIndex;
    }
}
