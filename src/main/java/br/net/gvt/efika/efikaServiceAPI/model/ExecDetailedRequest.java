/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model;

import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;

/**
 *
 * @author G0041775
 */
public class ExecDetailedRequest {

    private String instancia;

    private String parametro;

    private ExecDetailedEnum execucao;

    private Object setter;

    public ExecDetailedRequest() {
    }

    public ExecDetailedRequest(String instancia, String parametro, ExecDetailedEnum execucao, Object setter) {
        this.instancia = instancia;
        this.parametro = parametro;
        this.execucao = execucao;
        this.setter = setter;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public ExecDetailedEnum getExecucao() {
        return execucao;
    }

    public void setExecucao(ExecDetailedEnum execucao) {
        this.execucao = execucao;
    }

    public Object getSetter() {
        return setter;
    }

    public void setSetter(Object setter) {
        this.setter = setter;
    }

}
