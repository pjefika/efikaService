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

    private AcaoEnum nome;

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

    private Boolean resultado;

    public AcaoValidadora() {
    }

    public AcaoValidadora(AcaoEnum nome) {
        this.nome = nome;
    }

    public AcaoValidadora(AcaoEnum nome, ValidacaoResult valid) {
        this.nome = nome;
        this.valid = valid;
    }

    public AcaoEnum getNome() {
        return nome;
    }

    public void setNome(AcaoEnum nome) {
        this.nome = nome;
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

}
