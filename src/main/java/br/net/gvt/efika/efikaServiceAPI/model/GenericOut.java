/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model;

/**
 *
 * @author G0041775
 */
public class GenericOut {

    private StringParam param;

    private Object result;

    public GenericOut() {
    }

    public StringParam getParam() {
        return param;
    }

    public void setParam(StringParam param) {
        this.param = param;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
