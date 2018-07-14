/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.execucao_detalhada;

import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import java.util.List;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.mongo.dao.AbstractMongoDAO;
import br.net.gvt.efika.mongo.dao.MongoEndpointEnum;
import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.FindOptions;

/**
 *
 * @author G0041775
 */
public class ExecucaoDetalhadaDAOImpl extends AbstractMongoDAO<ExecucaoDetalhada> implements ExecucaoDetalhadaDAO {

    public ExecucaoDetalhadaDAOImpl() {
        super(MongoEndpointEnum.MONGO.getIp(), "efikaServiceAPI", ExecucaoDetalhada.class);
    }

    @Override
    public List<ExecucaoDetalhada> findByCustomer(EfikaCustomer cust) throws Exception {
        return getDatastore().createQuery(ExecucaoDetalhada.class)
                .field("customer.instancia")
                .equal(cust.getInstancia())
                .order("-dataFim")
                .asList(new FindOptions()
                        .limit(10));
    }

    @Override
    public ExecucaoDetalhada read(String id) throws Exception {
        return super.read(new ObjectId(id));
    }

    @Override
    public EfikaCustomer findRecentCustomer(String instancia, Date dataLimite) throws Exception {
        return getDatastore().createQuery(ExecucaoDetalhada.class)
                .field("customer.instancia")
                .equal(instancia)
                .field("dataFim")
                .greaterThan(dataLimite)
                .get()
                .getCustomer();
    }

    @Override
    public ExecucaoDetalhada findRecentExec(String instancia, ExecDetailedEnum exec, Date dataLimite) throws Exception {
        ExecucaoDetalhada l = getDatastore().createQuery(ExecucaoDetalhada.class)
                .field("nome")
                .equal(exec)
                .field("customer.instancia")
                .equal(instancia)
                .field("dataFim")
                .greaterThan(dataLimite)
                .get();
        try {
            return l;
        } catch (Exception e) {
            return null;
        }
    }

}
