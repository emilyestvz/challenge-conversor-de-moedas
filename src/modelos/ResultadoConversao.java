package modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Armazena resultado completo da conversão
public class ResultadoConversao {
    private Moeda moedaOrigem;
    private Moeda moedaDestino;
    private double valorOriginal;
    private double valorConvertido;
    private double taxaCambio;
    private LocalDateTime dataHora;

    public ResultadoConversao(Moeda moedaOrigem, Moeda moedaDestino,
                              double valorOriginal, double valorConvertido, double taxaCambio) {
        this.moedaOrigem = moedaOrigem;
        this.moedaDestino = moedaDestino;
        this.valorOriginal = valorOriginal;
        this.valorConvertido = valorConvertido;
        this.taxaCambio = taxaCambio;
        this.dataHora = LocalDateTime.now();
    }

    // Getters
    public Moeda getMoedaOrigem() { return moedaOrigem; }
    public Moeda getMoedaDestino() { return moedaDestino; }
    public double getValorOriginal() { return valorOriginal; }
    public double getValorConvertido() { return valorConvertido; }
    public double getTaxaCambio() { return taxaCambio; }
    public LocalDateTime getDataHora() { return dataHora; }

    public String formatarResultado() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return String.format("""
            ==============================================
                       RESULTADO DA CONVERSÃO
            ==============================================
            💰 Valor: %.2f %s
            📊 Taxa: 1 %s = %.4f %s
            💵 Resultado: %.2f %s
            📝 %s %.2f equivalem a %s %.2f
            🕒 Data/Hora: %s
            ==============================================
            """,
                valorOriginal, moedaOrigem.getCodigo(),
                moedaOrigem.getCodigo(), taxaCambio, moedaDestino.getCodigo(),
                valorConvertido, moedaDestino.getCodigo(),
                moedaOrigem.getNome(), valorOriginal,
                moedaDestino.getNome(), valorConvertido,
                dataHora.format(formatter)
        );
    }
}
