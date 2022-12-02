
import java.io.File;
import java.io.IOException;

public class CadastroTest {

    public static void main(String[] args) {

        Cadastro cadastro = new Cadastro();

        try {
            cadastro.carregar(new File("D:\\Unisinos\\EAD I\\dados-pessoas.txt"));
        } catch (IOException e){
            e.printStackTrace();
        }

        cadastro.menu();
    }
}
