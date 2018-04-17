/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador;

import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.mongo.model.entity.AbstractMongoEntity;
import java.util.Date;

/**
 *
 * @author G0041775
 */
public class ExecucaoDetalhada extends AbstractMongoEntity {

    private ValidacaoResult valid;

    private Date dataInicio;

    private Date dataFim;

    private ExecDetailedEnum nome;

    private EfikaCustomer customer;

    private String parametro;

    public ExecucaoDetalhada() {
    }

    public ExecucaoDetalhada(ExecDetailedEnum nome) {
        this.nome = nome;
    }

    public ValidacaoResult getValid() {
        return valid;
    }

    public void setValid(ValidacaoResult valid) {
        this.valid = valid;
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

    public ExecDetailedEnum getNome() {
        return nome;
    }

    public void setNome(ExecDetailedEnum nome) {
        this.nome = nome;
    }

    public EfikaCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(EfikaCustomer customer) {
        this.customer = customer;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

}
