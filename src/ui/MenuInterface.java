package ui;
import modelos.Moeda;
import java.util.Map;
import java.util.Scanner;

// Para gerenciar toda interação com o usuário
public class MenuInterface {
    private final Scanner scanner;

    public MenuInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirCabecalho() {
        System.out.println("==============================================");
        System.out.println("🌍 CONVERSOR DE MOEDAS EM TEMPO REAL 🌍");
        System.out.println("==============================================");
    }

    public void exibirMenu() {
        System.out.println("\n==============================================");
        System.out.println("           ESCOLHA UMA OPÇÃO:");
        System.out.println("==============================================");
        System.out.println("1. USD → BRL (Dólar para Real)");
        System.out.println("2. BRL → USD (Real para Dólar)");
        System.out.println("3. EUR → BRL (Euro para Real)");
        System.out.println("4. BRL → EUR (Real para Euro)");
        System.out.println("5. GBP → BRL (Libra para Real)");
        System.out.println("6. BRL → GBP (Real para Libra)");
        System.out.println("7. JPY → BRL (Iene para Real)");
        System.out.println("8. BRL → JPY (Real para Iene)");
        System.out.println("9. 🔄 Conversão Personalizada");
        System.out.println("10. 📋 Listar Moedas Disponíveis");
        System.out.println("0. Sair");
        System.out.println("==============================================");
        System.out.print("Digite sua opção: ");
    }

    public int obterOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double obterValor(String mensagem) {
        System.out.print(mensagem);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    public String obterTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim().toUpperCase();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirErro(String erro) {
        System.out.println("❌ " + erro);
    }

    public void exibirSucesso(String mensagem) {
        System.out.println("✅ " + mensagem);
    }

    public void exibirMoedasDisponiveis(Map<String, Moeda> moedas) {
        System.out.println("\n==============================================");
        System.out.println("         💸 MOEDAS DISPONÍVEIS");
        System.out.println("==============================================");

        moedas.values().forEach(moeda ->
                System.out.println("• " + moeda.toString())
        );

        System.out.println("==============================================");
    }

    public void aguardarEnter() {
        System.out.println("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    public void fechar() {
        scanner.close();
    }
}
