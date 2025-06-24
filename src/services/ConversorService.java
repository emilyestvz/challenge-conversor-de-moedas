package services;
import modelos.Moeda;
import modelos.ResultadoConversao;
import java.util.HashMap;
import java.util.Map;

//  Lógica de negócio das conversões
public class ConversorService {
    private final ApiService apiService;
    private final Map<String, Moeda> moedasDisponiveis;

    public ConversorService() {
        this.apiService = new ApiService();
        this.moedasDisponiveis = inicializarMoedas();
    }

    //Realiza a conversão entre duas moedas
    public ResultadoConversao converterMoeda(String codigoOrigem, String codigoDestino, double valor) {
        Moeda moedaOrigem = moedasDisponiveis.get(codigoOrigem);
        Moeda moedaDestino = moedasDisponiveis.get(codigoDestino);

        if (moedaOrigem == null || moedaDestino == null) {
            throw new IllegalArgumentException("Código de moeda inválido");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }

        double taxa = apiService.obterTaxaCambio(codigoOrigem, codigoDestino);

        if (taxa == -1) {
            throw new RuntimeException("Erro ao obter taxa de câmbio");
        }

        double valorConvertido = valor * taxa;

        return new ResultadoConversao(moedaOrigem, moedaDestino, valor, valorConvertido, taxa);
    }

    //Inicializa o mapa de moedas disponíveis
    private Map<String, Moeda> inicializarMoedas() {
        Map<String, Moeda> moedas = new HashMap<>();

        // Moedas principais
        moedas.put("USD", new Moeda("USD", "Dólares Americanos", "$"));
        moedas.put("BRL", new Moeda("BRL", "Reais Brasileiros", "R$"));
        moedas.put("EUR", new Moeda("EUR", "Euros", "€"));
        moedas.put("GBP", new Moeda("GBP", "Libras Esterlinas", "£"));
        moedas.put("JPY", new Moeda("JPY", "Ienes Japoneses", "¥"));
        moedas.put("CAD", new Moeda("CAD", "Dólares Canadenses", "C$"));
        moedas.put("AUD", new Moeda("AUD", "Dólares Australianos", "A$"));
        moedas.put("CHF", new Moeda("CHF", "Francos Suíços", "Fr"));
        moedas.put("CNY", new Moeda("CNY", "Yuan Chinês", "¥"));
        moedas.put("MXN", new Moeda("MXN", "Pesos Mexicanos", "$"));

        return moedas;
    }

    public Map<String, Moeda> getMoedasDisponiveis() {
        return new HashMap<>(moedasDisponiveis);
    }

    public boolean testarConexaoApi() {
        return apiService.testarConexao();
    }
}
