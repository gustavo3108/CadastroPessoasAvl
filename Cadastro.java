

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cadastro {

    private ArrayList<Pessoa> listaDePessoas = new ArrayList<>();
    private AVLTreeString avlCpf = new AVLTreeString();
    private AVLTree avlDataNasc = new AVLTree();
    private AVLTreeString avlNome = new AVLTreeString();

    public ArrayList<Pessoa> getListaDePessoas() {
        return listaDePessoas;
    }

    public void setListaDePessoas(ArrayList<Pessoa> listaDePessoas) {
        this.listaDePessoas = listaDePessoas;
    }

    public AVLTreeString getAvlCpf() {
        return avlCpf;
    }

    public void setAvlCpf(AVLTreeString avlCpf) {
        this.avlCpf = avlCpf;
    }

    public AVLTree getAvlDataNasc() {
        return avlDataNasc;
    }

    public void setAvlDataNasc(AVLTree avlDataNasc) {
        this.avlDataNasc = avlDataNasc;
    }

    public AVLTreeString getAvlNome() {
        return avlNome;
    }

    public void setAvlNome(AVLTreeString avlNome) {
        this.avlNome = avlNome;
    }

    public void carregar(File file) throws IOException {

        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                String[] cols = line.split(";");
                Pessoa pessoa = new Pessoa(cols[0], cols[1], cols[2], cols[3], cols[4]);
                listaDePessoas.add(pessoa);
                int index = listaDePessoas.indexOf(pessoa);
                avlCpf.insert(cols[0], index);
                avlDataNasc.insert(converteDataNasc(cols[3]), index);
                avlNome.insert(cols[2], index);
            }
        }
    }

    private void imprimirTodosCadastrosNaTela() {
        for (int i = 0; i < listaDePessoas.size(); i++) {
            System.out.println(listaDePessoas.get(i) + "\n");
        }
    }

    private void imprimirUmUnicoCadastroDePessoaNaTela(int index) {
        if (index >= 0)
            System.out.println(listaDePessoas.get(index) + "\n");
    }

    private long converteDataNasc(String dataNasc) {
        return Long.parseLong(dataNasc.substring(6, 10) + dataNasc.substring(3, 5) + dataNasc.substring(0, 2));
    }

    private void buscarNomesPorSubstring(String stringASerBuscadaNaArvore) {
        ArrayList<Integer> listaDeIndices = getAvlNome().buscarIndexDeVariosNomes(stringASerBuscadaNaArvore);
        imprimirCadastrosDePessoasAPartirDoIndice(listaDeIndices);
    }

    private void imprimirCadastrosDePessoasAPartirDoIndice(ArrayList<Integer> listaDeIndices) {
        if (listaDeIndices.isEmpty()) {
            System.out.println("Dado buscado não consta na árvore.\n");
        } else {

            for (Integer integer : listaDeIndices) {
                System.out.println(getListaDePessoas().get(integer) + "\n");
            }
        }
    }

    public void menu() {
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        System.out.println("      _");
        System.out.println("|\\/| |_  |\\ | |  | ");
        System.out.println("|  | |_  | \\| |__|");
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        while (opcao != 9) {

            System.out.println("Digite '1' para visualizar a árvore de CPF.");
            System.out.println("Digite '2' para visualizar a árvore de Nomes.");
            System.out.println("Digite '3' para visualizar a árvore de Data de Nascimento.");
            System.out.println("Digite '4' para uma busca por CPF.");
            System.out.println("Digite '5' para uma busca por Nome.");
            System.out.println("Digite '6' para uma busca por faixa de Datas de Nascimentos.");
            System.out.println("Digite '7' para ver todos os registros de pessoas.");
            System.out.println("Digite '8' para uma busca por Data de Nascimento.");
            System.out.println("Digite '9' para sair.\n");

            System.out.println("Qual opção você deseja?\n");
            int opcaoEscolhida = scanner.nextInt();

            if (opcaoEscolhida == 9) {
                System.out.println(
                        "---------------------------------------------------------------------------------------------");
                System.out.println("Obrigado por usar o nosso sistema de árvores.");
                System.out.println(
                        "---------------------------------------------------------------------------------------------");
                scanner.close();
                break;
            } else if (opcaoEscolhida == 1) {
                avlCpf.print();
            } else if (opcaoEscolhida == 2) {
                avlNome.print();
            } else if (opcaoEscolhida == 3) {
                avlDataNasc.print();
            } else if (opcaoEscolhida == 4) {
                System.out.println(
                        "O CPF a ser digitado e buscado deve ter exatamente 11 digitos numéricos, sem ponto e sem hífen. Ex: 66666666666");
                System.out.println("Por favor digite o CPF que você deseja buscar: ");
                String cpfASerBuscado = scanner.next();
                imprimirUmUnicoCadastroDePessoaNaTela(avlCpf.buscaUmUnicoIndexAPartirDeUmUnicoCpf(cpfASerBuscado));
            } else if (opcaoEscolhida == 5) {
                System.out.println("Exemplo de busca: se desejar retornar Gustavo e Guilherme, pesquise por Gu.");
                System.out.println(
                        "Por favor digite a substring que você deseja que todos os nomes que comecem com ela sejam imprimidos na tela: ");
                String iniciaisASeremBuscadas = scanner.next();
                buscarNomesPorSubstring(iniciaisASeremBuscadas);
            } else if (opcaoEscolhida == 6) {
                System.out.println(
                        "As datas de nascimento a serem digitadas devem ter dia, mês e ano. É obrigatório digitar a barra separadora. Favor usar esse formato: DD/MM/AAAA. Ex: 01/01/1950");
                System.out.println(
                        "Você deve digitar duas datas de nascimento e será imprimido na tela todos os cadastros de pessoas que estiverem dentro dessa faixa de datas.");
                System.out.println("Por favor digite a data de nascimento inicial que você deseja buscar: ");
                String startDate = scanner.next();
                System.out.println("Por favor digite a data de nascimento final que você deseja buscar: ");
                String endDate = scanner.next();
                imprimirCadastrosDePessoasAPartirDoIndice(avlDataNasc
                        .buscarIndexDeVariasDatasDeNascimento(converteDataNasc(startDate), converteDataNasc(endDate)));
            } else if (opcaoEscolhida == 7) {
                imprimirTodosCadastrosNaTela();
            } else if (opcaoEscolhida == 8) {
                System.out.println(
                        "A data de nascimento a ser digitada e buscada deve ter dia, mês e ano. É obrigatório digitar a barra separadora. Favor usar esse formato: DD/MM/AAAA. Ex: 01/01/1950");
                System.out.println("Por favor digite a data de nascimento que você deseja buscar: ");
                String dataASerBuscada = scanner.next();
                imprimirUmUnicoCadastroDePessoaNaTela(
                        avlDataNasc.buscaIndexDataNasc(converteDataNasc(dataASerBuscada)));
            } else {
                System.out.println("Você deve digitar uma das seguintes opções: 1, 2, 3, 4, 5, 6, 7, 8 ou 9.\n");
            }

            opcao = opcaoEscolhida;
        }
        scanner.close();
    }
}
