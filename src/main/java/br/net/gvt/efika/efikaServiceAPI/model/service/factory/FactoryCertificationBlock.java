/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.factory;

import br.net.gvt.efika.customer.model.certification.CertificationBlock;
import br.net.gvt.efika.customer.model.certification.enums.CertificationBlockName;



/**
 *
 * @author G0041775
 */
public class FactoryCertificationBlock {

    public static CertificationBlock createBlockByName(CertificationBlockName name) throws Exception {
        return new CertificationBlock(name);
    }

}
