/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.efikaServiceAPI.dao.execucao_detalhada;

import br.net.gvt.efika.efikaServiceAPI.model.enums.ExecDetailedEnum;
import br.net.gvt.efika.efikaServiceAPI.model.validador.ExecucaoDetalhada;
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
public class ExecucaoDetalhadaDAOImplIT {
    
    public ExecucaoDetalhadaDAOImplIT() {
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
     * Test of findByCustomer method, of class ExecucaoDetalhadaDAOImpl.
     */
    @Test
    public void testFindByCustomer() throws Exception {
        System.out.println("findByCustomer");
        EfikaCustomer cust = null;
        ExecucaoDetalhadaDAOImpl instance = new ExecucaoDetalhadaDAOImpl();
        List<ExecucaoDetalhada> expResult = null;
        List<ExecucaoDetalhada> result = instance.findByCustomer(cust);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class ExecucaoDetalhadaDAOImpl.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        String id = "";
        ExecucaoDetalhadaDAOImpl instance = new ExecucaoDetalhadaDAOImpl();
        ExecucaoDetalhada expResult = null;
        ExecucaoDetalhada result = instance.read(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRecentCustomer method, of class ExecucaoDetalhadaDAOImpl.
     */
    @Test
    public void testFindRecentCustomer() throws Exception {
        System.out.println("findRecentCustomer");
        String instancia = "";
        Date dataLimite = null;
        ExecucaoDetalhadaDAOImpl instance = new ExecucaoDetalhadaDAOImpl();
        EfikaCustomer expResult = null;
        EfikaCustomer result = instance.findRecentCustomer(instancia, dataLimite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRecentExec method, of class ExecucaoDetalhadaDAOImpl.
     */
    @Test
    public void testFindRecentExec() throws Exception {
        System.out.println("findRecentExec");
        String instancia = "4130862424";
        ExecDetailedEnum exec = ExecDetailedEnum.PING;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -120);
        Date dataLimite = now.getTime();
        ExecucaoDetalhadaDAOImpl instance = new ExecucaoDetalhadaDAOImpl();

        ExecucaoDetalhada result = instance.findRecentExec(instancia, exec, dataLimite);
        System.out.println(new JacksonMapper(ExecucaoDetalhada.class).serialize(result));
    }
    
}
