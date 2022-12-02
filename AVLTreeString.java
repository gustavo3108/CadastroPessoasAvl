
import java.util.ArrayList;

class AVLTreeString {

    private Node root;

    private class Node {

        private int index;
        private String element;
        private int height;

        private Node left;
        private Node right;

        private Node(String element, int index) {
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
    public void insert(String element, int index) {
        root = insert(root, element, index);
    }

    private Node insert(Node node, String element, int index) {

        if (node == null) {
            //System.out.println("O elemento " + element + " foi inserido com sucesso na arvore.\n");
            return (new Node(element, index));
        }

        if (element.compareToIgnoreCase(node.element) < 0) {
            node.left = insert(node.left, element, index);
        } else if (element.compareToIgnoreCase(node.element) > 0) {
            node.right = insert(node.right, element, index);
        } else {
            System.out.println("O elemento " + element + " ja existe na arvore.\n");
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (element.compareToIgnoreCase(node.left.element) < 0) {
                return rightRotate(node);
            } else if (element.compareToIgnoreCase(node.left.element) > 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        if (balanceFactor < -1) {
            if (element.compareToIgnoreCase(node.right.element) > 0) {
                return leftRotate(node);
            } else if (element.compareToIgnoreCase(node.right.element) < 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    /* delete an element from the tree */
    public void delete(String element) {
        boolean existeOElemento = busca(element);
        if (!existeOElemento) {
            System.out
                    .println("O elemento " + element + " nao existe na arvore, e, portanto, nao pode ser deletado.\n");
        } else {
            root = delete(root, element);
            System.out.println("O elemento " + element + " foi removido da arvore com sucesso.\n");

        }
    }

    private Node delete(Node root, String element) {

        if (root == null) {
            return root;
        }

        if (element.compareToIgnoreCase(root.element) < 0) {
            root.left = delete(root.left, element);
        } else if (element.compareToIgnoreCase(root.element) > 0) {
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
            System.out.println("A arvore esta vazia.\n");
        } else {
            System.out.println("\nEstrutura da arvore AVL: \n");
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

    public boolean busca(String element) {
        return busca(root, element);
    }

    private boolean busca(Node node, String element) {

        if (node == null) {
            return false;
        } else if (element.equalsIgnoreCase(node.element)) {

            return true;
        } else if (element.compareToIgnoreCase(node.element) < 0) {
            return busca(node.left, element);
        } else {
            return busca(node.right, element);
        }
    }

    public int buscaIndexDeUmUnicoNome(String element) {
        return buscaIndexDeUmUnicoNome(root, element);
    }

    private int buscaIndexDeUmUnicoNome(Node node, String element) {

        if (node == null) {
            return -1;
        } else if (node.element.startsWith(element)) {

            return node.index;
        } else if (element.compareToIgnoreCase(node.element) < 0) {
            return buscaIndexDeUmUnicoNome(node.left, element);
        } else {
            return buscaIndexDeUmUnicoNome(node.right, element);
        }

    }

    public int buscaUmUnicoIndexAPartirDeUmUnicoCpf(String element) {
        return buscaUmUnicoIndexAPartirDeUmUnicoCpf(root, element);
    }

    private int buscaUmUnicoIndexAPartirDeUmUnicoCpf(Node node, String element) {

        if (node == null) {
            return -1;
        } else if (element.equalsIgnoreCase(node.element)) {

            return node.index;
        } else if (element.compareToIgnoreCase(node.element) < 0) {
            return buscaUmUnicoIndexAPartirDeUmUnicoCpf(node.left, element);
        } else {
            return buscaUmUnicoIndexAPartirDeUmUnicoCpf(node.right, element);
        }

    }

    public ArrayList<Integer> buscarIndexDeVariosNomes(String element) {
        ArrayList<Integer> listaDeIndex = new ArrayList<>();
        return buscarIndexDeVariosNomes(root, element, listaDeIndex);
    }

    private ArrayList<Integer> buscarIndexDeVariosNomes(Node node, String element, ArrayList<Integer> listaDeIndex) {
        String auxNodeElementToUpperCase=node.element.toUpperCase();
        if (auxNodeElementToUpperCase.startsWith(element.toUpperCase())) {
            listaDeIndex.add(node.index);
        }
        if (node.left != null) {
            buscarIndexDeVariosNomes(node.left, element, listaDeIndex);
        }

        if (node.right != null) {
            buscarIndexDeVariosNomes(node.right, element, listaDeIndex);
        }
        return listaDeIndex;
    }

}

