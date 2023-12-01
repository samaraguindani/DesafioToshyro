import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Moeda> moedasIniciais = new ArrayList<>();
        moedasIniciais.add(new Moeda(0.01));
        moedasIniciais.add(new Moeda(0.05));
        moedasIniciais.add(new Moeda(0.10));
        moedasIniciais.add(new Moeda(0.25));
        moedasIniciais.add(new Moeda(0.50));
        moedasIniciais.add(new Moeda(1.00));

        List<Produto> produtosIniciais = new ArrayList<>();
        produtosIniciais.add(new Produto("COCA-COLA", 1.50));
        produtosIniciais.add(new Produto("AGUA", 1.00));
        produtosIniciais.add(new Produto("PASTELINA", 0.30));

        MaquinaDeVenda maquinaDeVenda = new MaquinaDeVenda(moedasIniciais, produtosIniciais);
        
        try (Scanner scanner = new Scanner(System.in)) {
            maquinaDeVenda.mostrarMenu();

            while (true) {
                System.out.println("\nInsira moedas e solicite um produto ou digite sair para encerrar o programa: ");
                System.out.print("Entrada: ");
                String entradaDoUsuario = scanner.nextLine().trim();

                if (entradaDoUsuario.equalsIgnoreCase("SAIR")) {
                    System.out.println("Encerrando o programa. Até mais!");
                    break;
                }

                maquinaDeVenda.processarEntradaDoUsuario(entradaDoUsuario);
            }
        }
    }
}
