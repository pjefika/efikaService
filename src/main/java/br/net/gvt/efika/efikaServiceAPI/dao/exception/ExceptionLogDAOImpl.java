/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.exception;

import br.net.gvt.efika.efikaServiceAPI.model.entity.ExceptionLog;
import br.net.gvt.efika.mongo.dao.AbstractMongoDAO;
import br.net.gvt.efika.mongo.dao.MongoEndpointEnum;

public class ExceptionLogDAOImpl extends AbstractMongoDAO<ExceptionLog> {

    public ExceptionLogDAOImpl() {
        super(MongoEndpointEnum.MONGO.getIp(), "efikaServiceAPI", ExceptionLog.class);
    }

}
