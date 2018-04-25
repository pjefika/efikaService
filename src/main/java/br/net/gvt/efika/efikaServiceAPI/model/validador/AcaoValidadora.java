/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.mongo.model.entity.AbstractMongoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

/**
 *
 * @author G0041775
 */
public class AcaoValidadora extends AbstractMongoEntity {

    private AcaoEnum acao;

    @JsonIgnore
    private ValidacaoResult valid;

    @JsonIgnore
    private EfikaCustomer customer;

    @JsonIgnore
    private Date dataInicio;

    @JsonIgnore
    private Date dataFim;

    private String mensagem;

    private AcaoResultEnum tipo;

    private Boolean resultado, consulta;

    private String urlCorrecao;

    public AcaoValidadora() {
    }

    public AcaoValidadora(AcaoEnum nome) {
        this.acao = nome;
    }

    public AcaoValidadora(AcaoEnum nome, ValidacaoResult valid) {
        this.acao = nome;
        this.valid = valid;
    }

    public AcaoEnum getAcao() {
        return acao;
    }

    public void setAcao(AcaoEnum acao) {
        this.acao = acao;
    }

    public Boolean getConsulta() {
        return consulta;
    }

    public void setConsulta(Boolean consulta) {
        this.consulta = consulta;
    }

    public ValidacaoResult getValid() {
        return valid;
    }

    public void setValid(ValidacaoResult valid) {
        this.valid = valid;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public AcaoResultEnum getTipo() {
        return tipo;
    }

    public void setTipo(AcaoResultEnum tipo) {
        this.tipo = tipo;
    }

    public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public EfikaCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(EfikaCustomer customer) {
        this.customer = customer;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getUrlCorrecao() {
        return urlCorrecao;
    }

    public void setUrlCorrecao(String urlCorrecao) {
        this.urlCorrecao = urlCorrecao;
    }


}
