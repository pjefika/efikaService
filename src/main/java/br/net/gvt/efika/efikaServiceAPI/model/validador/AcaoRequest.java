/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.validador;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;

/**
 *
 * @author G0041775
 */
public class AcaoRequest {

    private AcaoEnum acao;
    private String instancia;

    public AcaoRequest() {
    }

    public AcaoRequest(AcaoEnum acao, String instancia) {
        this.acao = acao;
        this.instancia = instancia;
    }

    public AcaoEnum getAcao() {
        return acao;
    }

    public void setAcao(AcaoEnum acao) {
        this.acao = acao;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

}
