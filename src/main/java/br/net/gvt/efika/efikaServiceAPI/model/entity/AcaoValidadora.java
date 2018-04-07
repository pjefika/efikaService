/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.entity;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoResultEnum;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;

/**
 *
 * @author G0041775
 */
public class AcaoValidadora {

    private AcaoEnum nome;

    private ValidacaoResult valid;

    private String mensagem;

    private AcaoResultEnum resultado;

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

    public AcaoResultEnum getResultado() {
        return resultado;
    }

    public void setResultado(AcaoResultEnum resultado) {
        this.resultado = resultado;
    }

}
