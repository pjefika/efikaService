/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;

/**
 *
 * @author G0041775
 */
public class ResponsesRequest {

    private AcaoEnum acao;

    public ResponsesRequest() {
    }

    public ResponsesRequest(AcaoEnum acao) {
        this.acao = acao;
    }

    public AcaoEnum getAcao() {
        return acao;
    }

    public void setAcao(AcaoEnum acao) {
        this.acao = acao;
    }

}
