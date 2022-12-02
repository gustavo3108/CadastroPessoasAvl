public class Pessoa {

    private String CPF;
    private String RG;
    private String nome;
    private String dataDeNascimento;
    private String cidadeOndeNasceu;

    public Pessoa (String CPF, String RG, String nome, String dataDeNascimento, String cidadeOndeNasceu) {
        this.CPF = CPF;
        this.RG = RG;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.cidadeOndeNasceu = cidadeOndeNasceu;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String rG) {
        RG = rG;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCidadeOndeNasceu() {
        return cidadeOndeNasceu;
    }

    public void setCidadeOndeNasceu(String cidadeOndeNasceu) {
        this.cidadeOndeNasceu = cidadeOndeNasceu;
    }

    public String toString() {
        return "Nome: " + nome + "\nCPF: " + CPF + "\nRG: " + RG + "\nData De Nascimento: " + dataDeNascimento + "\nNatural de: " + cidadeOndeNasceu;
    }

}
