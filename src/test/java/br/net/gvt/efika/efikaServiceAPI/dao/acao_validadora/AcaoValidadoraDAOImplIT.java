/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.acao_validadora;

import br.net.gvt.efika.efikaServiceAPI.model.enums.AcaoEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.AcaoValidadora;
import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.util.json.JacksonMapper;
import java.util.Calendar;
import java.util.Date;
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
public class AcaoValidadoraDAOImplIT {
    
    public AcaoValidadoraDAOImplIT() {
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
     * Test of findByCustomer method, of class AcaoValidadoraDAOImpl.
     */
    @Test
    public void testFindByCustomer() throws Exception {
        System.out.println("findByCustomer");
        EfikaCustomer cust = null;
        AcaoValidadoraDAOImpl instance = new AcaoValidadoraDAOImpl();
        List<AcaoValidadora> expResult = null;
        List<AcaoValidadora> result = instance.findByCustomer(cust);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class AcaoValidadoraDAOImpl.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        String id = "";
        AcaoValidadoraDAOImpl instance = new AcaoValidadoraDAOImpl();
        AcaoValidadora expResult = null;
        AcaoValidadora result = instance.read(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRecentCustomer method, of class AcaoValidadoraDAOImpl.
     */
    @Test
    public void testFindRecentCustomer() throws Exception {
        System.out.println("findRecentCustomer");
        String instancia = "";
        Date dataLimite = null;
        AcaoValidadoraDAOImpl instance = new AcaoValidadoraDAOImpl();
        EfikaCustomer expResult = null;
        EfikaCustomer result = instance.findRecentCustomer(instancia, dataLimite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRecentExec method, of class AcaoValidadoraDAOImpl.
     */
    @Test
    public void testFindRecentExec() throws Exception {
        System.out.println("findRecentExec");
        String instancia = "4131495583";
        AcaoEnum acao = AcaoEnum.ASSOCIACAO_ONT;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, -1);
        Date dataLimite = now.getTime();
        AcaoValidadoraDAOImpl instance = new AcaoValidadoraDAOImpl();
     
        AcaoValidadora result = instance.findRecentExec(instancia, acao, dataLimite);
        System.out.println(new JacksonMapper(AcaoValidadora.class).serialize(result));
    }
    
}
