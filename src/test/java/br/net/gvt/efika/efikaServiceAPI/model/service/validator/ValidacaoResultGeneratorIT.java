/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.model.service.validator;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.efika_customer.model.customer.mock.CustomerMock;
import br.net.gvt.efika.fulltest.model.fulltest.ValidacaoResult;
import br.net.gvt.efika.util.json.JacksonMapper;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G0041775
 */
public class ValidacaoResultGeneratorIT {
    
    public ValidacaoResultGeneratorIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCust method, of class ValidacaoResultGenerator.
     */
    @Test
    public void testGetCust() throws Exception {
        System.out.println("getCust");
        String instancia = "";
        EfikaCustomer expResult = null;
        EfikaCustomer result = ValidacaoResultGenerator.getCust(instancia);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generate method, of class ValidacaoResultGenerator.
     */
    @Test
    public void testGenerate_AcaoValidadora() throws Exception {
        System.out.println("generate");
        AcaoValidadora a = null;
        ValidacaoResult expResult = null;
        ValidacaoResult result = ValidacaoResultGenerator.generate(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generate method, of class ValidacaoResultGenerator.
     */
    @Test
    public void testGenerate_ExecucaoDetalhada() throws Exception {
        System.out.println("generate");
        ExecucaoDetalhada exec = new ExecucaoDetalhada(ExecDetailedEnum.GET_FIRMWARE);
        exec.setCustomer(ValidacaoResultGenerator.getCust("4130157784"));
        exec.setParametro("26737329");
        Object result = ValidacaoResultGenerator.generate(exec);
        System.out.println(new JacksonMapper<>(Object.class).serialize(result));
    }

    /**
     * Test of fakeGeneration method, of class ValidacaoResultGenerator.
     */
    @Test
    public void testFakeGeneration() {
        System.out.println("fakeGeneration");
        AcaoEnum a = null;
        List<ValidacaoResult> expResult = null;
        List<ValidacaoResult> result = ValidacaoResultGenerator.fakeGeneration(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mockValidation method, of class ValidacaoResultGenerator.
     */
    @Test
    public void testMockValidation() {
        System.out.println("mockValidation");
        AcaoValidadora a = null;
        ValidacaoResult expResult = null;
        ValidacaoResult result = ValidacaoResultGenerator.mockValidation(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
