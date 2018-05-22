/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.factory;

import br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora.AcaoValidadoraDAO;
import br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora.AcaoValidadoraDAOImpl;
import br.net.gvt.efika.efikaServiceAPI.dao.execucao_detalhada.ExecucaoDetalhadaDAO;
import br.net.gvt.efika.efikaServiceAPI.dao.execucao_detalhada.ExecucaoDetalhadaDAOImpl;

/**
 *
 * @author G0041775
 */
public class FactoryDAO {

    public static AcaoValidadoraDAO acaoDao() {
        return new AcaoValidadoraDAOImpl();
    }

    public static ExecucaoDetalhadaDAO execDao() {
        return new ExecucaoDetalhadaDAOImpl();
    }

}
