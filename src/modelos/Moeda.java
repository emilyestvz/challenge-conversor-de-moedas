package modelos;

//  Controlador da aplicação (lógica principal)
public class Moeda {
    private String codigo;
    private String nome;
    private String simbolo;

    public Moeda(String codigo, String nome, String simbolo) {
        this.codigo = codigo;
        this.nome = nome;
        this.simbolo = simbolo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", codigo, simbolo, nome);
    }
}
