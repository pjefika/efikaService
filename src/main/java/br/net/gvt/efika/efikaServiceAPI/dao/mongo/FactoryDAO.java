/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.mongo;

import br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora.AcaoValidadoraDAOImpl;
import br.net.gvt.efika.efikaServiceAPI.dao.exception.ExceptionLogDAOImpl;
import br.net.gvt.efika.efikaServiceAPI.model.entity.ExceptionLog;
import br.net.gvt.efika.mongo.dao.AbstractMongoDAO;
import br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora.AcaoValidadoraDAO;

/**
 *
 * @author G0042204
 */
public class FactoryDAO {

    public static AbstractMongoDAO<ExceptionLog> newExceptionLogDAO() {
        return new ExceptionLogDAOImpl();
    }

    public static AcaoValidadoraDAO newAcaoValidadoraDAO() {
        return new AcaoValidadoraDAOImpl();
    }

}
