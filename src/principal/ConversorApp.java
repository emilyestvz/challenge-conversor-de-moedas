package principal;
import modelos.ResultadoConversao;
import services.ConversorService;
import ui.MenuInterface;

public class ConversorApp {
    private final ConversorService conversorService;
    private final MenuInterface menuInterface;

    public ConversorApp() {
        this.conversorService = new ConversorService();
        this.menuInterface = new MenuInterface();
    }

    public void iniciar() {
        menuInterface.exibirCabecalho();

        // Testar conexão com a API
        if (!conversorService.testarConexaoApi()) {
            menuInterface.exibirErro("Falha ao conectar com a API. Verifique sua conexão com a internet.");
            return;
        }

        menuInterface.exibirSucesso("Conexão com a API estabelecida!");

        boolean continuar = true;

        while (continuar) {
            try {
                menuInterface.exibirMenu();
                int opcao = menuInterface.obterOpcao();

                switch (opcao) {
                    case 1 -> executarConversao("USD", "BRL");
                    case 2 -> executarConversao("BRL", "USD");
                    case 3 -> executarConversao("EUR", "BRL");
                    case 4 -> executarConversao("BRL", "EUR");
                    case 5 -> executarConversao("GBP", "BRL");
                    case 6 -> executarConversao("BRL", "GBP");
                    case 7 -> executarConversao("JPY", "BRL");
                    case 8 -> executarConversao("BRL", "JPY");
                    case 9 -> executarConversaoPersonalizada();
                    case 10 -> listarMoedasDisponiveis();
                    case 0 -> {
                        continuar = false;
                        menuInterface.exibirMensagem("\n💰 Obrigado por usar o Conversor de Moedas!");
                        menuInterface.exibirMensagem("Até logo! 👋");
                    }
                    default -> menuInterface.exibirErro("Opção inválida! Tente novamente.");
                }

                if (continuar) {
                    menuInterface.aguardarEnter();
                }

            } catch (Exception e) {
                menuInterface.exibirErro("Erro inesperado: " + e.getMessage());
                menuInterface.aguardarEnter();
            }
        }

        menuInterface.fechar();
    }

    private void executarConversao(String codigoOrigem, String codigoDestino) {
        try {
            menuInterface.exibirMensagem("\n🔄 Processando conversão...");

            double valor = menuInterface.obterValor(
                    String.format("Digite o valor em %s: ", codigoOrigem)
            );

            ResultadoConversao resultado = conversorService.converterMoeda(
                    codigoOrigem, codigoDestino, valor
            );

            menuInterface.exibirMensagem(resultado.formatarResultado());

        } catch (NumberFormatException e) {
            menuInterface.exibirErro("Valor inválido! Digite um número válido.");
        } catch (IllegalArgumentException e) {
            menuInterface.exibirErro(e.getMessage());
        } catch (RuntimeException e) {
            menuInterface.exibirErro("Erro ao realizar conversão: " + e.getMessage());
        }
    }

    private void executarConversaoPersonalizada() {
        try {
            menuInterface.exibirMensagem("\n🔄 CONVERSÃO PERSONALIZADA");
            menuInterface.exibirMensagem("==============================================");

            String codigoOrigem = menuInterface.obterTexto(
                    "Digite a moeda de origem (ex: USD): "
            );

            String codigoDestino = menuInterface.obterTexto(
                    "Digite a moeda de destino (ex: BRL): "
            );

            if (codigoOrigem.isEmpty() || codigoDestino.isEmpty()) {
                menuInterface.exibirErro("Códigos de moeda não podem estar vazios!");
                return;
            }

            menuInterface.exibirMensagem("\n🔄 Obtendo taxa de câmbio...");

            double valor = menuInterface.obterValor(
                    String.format("Digite o valor em %s: ", codigoOrigem)
            );

            ResultadoConversao resultado = conversorService.converterMoeda(
                    codigoOrigem, codigoDestino, valor
            );

            menuInterface.exibirMensagem(resultado.formatarResultado());

        } catch (NumberFormatException e) {
            menuInterface.exibirErro("Valor inválido! Digite um número válido.");
        } catch (IllegalArgumentException e) {
            menuInterface.exibirErro(e.getMessage());
        } catch (RuntimeException e) {
            menuInterface.exibirErro("Erro ao realizar conversão: " + e.getMessage());
        }
    }

    private void listarMoedasDisponiveis() {
        menuInterface.exibirMoedasDisponiveis(conversorService.getMoedasDisponiveis());
    }
}
