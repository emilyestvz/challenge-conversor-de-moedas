package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final HttpClient client;
    private final Gson gson;

    public ApiService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     Obtém a taxa de câmbio entre duas moedas
     @param moedaOrigem Código da moeda de origem (ex: USD)
     @param moedaDestino Código da moeda de destino (ex: BRL)
     @return Taxa de câmbio ou -1 em caso de erro
     */
    public double obterTaxaCambio(String moedaOrigem, String moedaDestino) {
        try {
            String url = API_URL + moedaOrigem;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("User-Agent", "ConversorMoedas/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Erro na API: Código " + response.statusCode());
                return -1;
            }

            return extrairTaxaDaResposta(response.body(), moedaDestino);

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro de conexão com a API: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            System.err.println("Erro inesperado na API: " + e.getMessage());
            return -1;
        }
    }

    /**
     Extrai a taxa de câmbio da resposta JSON da API
     */
    private double extrairTaxaDaResposta(String jsonResponse, String moedaDestino) {
        try {
            JsonObject response = gson.fromJson(jsonResponse, JsonObject.class);

            if (!response.has("rates")) {
                System.err.println("Resposta da API não contém taxas de câmbio");
                return -1;
            }

            JsonObject rates = response.getAsJsonObject("rates");

            if (!rates.has(moedaDestino)) {
                System.err.println("Moeda de destino não encontrada: " + moedaDestino);
                return -1;
            }

            return rates.get(moedaDestino).getAsDouble();

        } catch (Exception e) {
            System.err.println("Erro ao processar resposta JSON: " + e.getMessage());
            return -1;
        }
    }

    // Verifica se a API está disponível
    public boolean testarConexao() {
        return obterTaxaCambio("USD", "BRL") != -1;
    }
}

