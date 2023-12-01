import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaquinaDeVenda {
    private List<Moeda> moedas;
    private List<Produto> produtos;
    private double saldoDoUsuario;

    public MaquinaDeVenda(List<Moeda> moedas, List<Produto> produtos) {
        this.moedas = moedas;
        this.produtos = produtos;
        this.saldoDoUsuario = 0.0;
    }

    public void mostrarMenu() {
        System.out.println("\nProdutos Disponíveis:");
        for (Produto produto : produtos) {
            System.out.println(produto.getNome() + " - Valor: " + String.format("%.2f", produto.getPreco()));
        }
    }

    public void inserirMoedas(String entrada) {
        String[] valoresDeEntrada = entrada.split(" ");
        double valorTotalMoedas = 0.0;

        for (String valorDaEntrada : valoresDeEntrada) {
            try {
                double valor = Double.parseDouble(valorDaEntrada.replace(',', '.'));
                valorTotalMoedas += valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: " + valorDaEntrada);
            }
        }

        saldoDoUsuario += valorTotalMoedas;
    }

    public String processarCompra(Produto produto, double valorAcumulado) {
        if (produto.getPreco() > valorAcumulado) {
            return "SALDO_INSUFICIENTE";
        }
    
        double valorTroco = valorAcumulado - produto.getPreco();

        if (valorTroco > 0) {
            saldoDoUsuario = valorTroco;
            return "Produto comprado: " + produto.getNome() + "\nTroco: " +
                    String.format("%.2f", saldoDoUsuario);
        } else {
            saldoDoUsuario = 0.0;
            return "Produto comprado: " + produto.getNome() + "\nTroco: " +
                    String.format("%.2f", saldoDoUsuario);
        }
    }
    

    private String calcularTroco(double valorTroco) {
        List<Moeda> moedasDoTroco = new ArrayList<>();

        for (Moeda moeda : moedas) {
            while (valorTroco >= moeda.getValor()) {
                moedasDoTroco.add(new Moeda(moeda.getValor()));
                valorTroco -= moeda.getValor();
            }
        }

        if (valorTroco > 0) {
            saldoDoUsuario += valorTroco;
            return "SEM_TROCO";
        }

        return formatarListaDeTroco(moedasDoTroco);
    }

    private String solicitarTroco(double valorTotalMoedas) {
        double troco = saldoDoUsuario - valorTotalMoedas;
        String resultadoTroco = calcularTroco(troco);
        if (resultadoTroco.equals("SEM_TROCO")) {
            return "SEM_TROCO";
        } else {
            saldoDoUsuario = 0;
            return "Troco: " + resultadoTroco;
        }
    }
    
    private Produto encontrarProduto(String nomeDoProduto) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nomeDoProduto)) {
                return produto;
            }
        }
        return null;
    }
    
    public String solicitarProduto(String nomeDoProduto, double valorAcumulado) {
        Produto produto = encontrarProduto(nomeDoProduto.trim().toUpperCase());
    
        if (produto == null) {
            saldoDoUsuario = 0.0;
            return "SEM_PRODUTO";
        }
    
        return processarCompra(produto, valorAcumulado);
    }
    
    public void processarEntradaDoUsuario(String entradaDoUsuario) {
        String[] partes = entradaDoUsuario.split(" ");
    
        if (partes.length > 1) {
            String[] entradasMoedas = Arrays.copyOfRange(partes, 0, partes.length - 1);
            inserirMoedas(String.join(" ", entradasMoedas));
    
            String nomeProduto = partes[partes.length - 1];
            System.out.println(solicitarProduto(nomeProduto, saldoDoUsuario));
            mostrarMenu();
        } else {
            // Processar como comando (e.g., TROCO)
            String comando = partes[0].toUpperCase();
            if (comando.equals("TROCO")) {
                System.out.println(solicitarTroco(saldoDoUsuario));
            } else {
                System.out.println("Comando inválido: " + comando);
            }
        }
    }
    
    private String formatarListaDeTroco(List<Moeda> moedasDoTroco) {
        StringBuilder stringBuilderDeTroco = new StringBuilder();
    
        for (Moeda moeda : moedasDoTroco) {
            stringBuilderDeTroco.append(String.format("%.2f", moeda.getValor())).append(" ");
        }
    
        return stringBuilderDeTroco.toString().trim();
    }
}


