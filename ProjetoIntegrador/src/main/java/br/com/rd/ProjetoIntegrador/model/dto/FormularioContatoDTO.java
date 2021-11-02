package br.com.rd.ProjetoIntegrador.model.dto;

public class FormularioContatoDTO {

    private Long id;
    private String nome;
    private Integer telefone;
    private String email;
    private String tipo_de_contato;
    private String assunto;
    private String mensagem;

    public Long getId() {   
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo_de_contato() {
        return tipo_de_contato;
    }

    public void setTipo_de_contato(String tipo_de_contato) {
        this.tipo_de_contato = tipo_de_contato;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
