package unit.in.ac.bits.protocolanalyzer.mvc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import in.ac.bits.protocolanalyzer.mvc.model.Experiment;
import unit.config.in.ac.bits.protocolanalyzer.mvc.model.ExperimentTestConfig;;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExperimentTestConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ExperimentTest {
	@Autowired
	@Qualifier("Experiment")
	private Experiment experiment;
	
	@Test
	public void autowiringTest() {
		assertNotNull(experiment);
	}
	
	@Test
	public void testCheckFileAccessTrue() throws Exception {
		try{
 		   experiment.checkFileAccess(System.getProperty("user.dir") + "/data/packet/DNS_Traffic000.pcap");
 	   }
 	   catch(Exception e){
 	      fail("Should not have thrown any exception");
 	   }
	} 
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
    @Test
	public void testCheckFileAccessOnEmptyFile() throws Exception {
    	expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Error in reading file(s) : Empty file(s)");
    	experiment.checkFileAccess(System.getProperty("user.dir") + "/data/packet/test_files/empty_test_file.pcap");  
    } 

    @Test
	public void testCheckFileAccessOnNonExistentFile() throws Exception {
        expectedEx.expectMessage("Error in reading file(s) : No such file(s) found");
    	experiment.checkFileAccess(System.getProperty("user.dir") + "/data/packet/test_files/non_existent_file.pcap");   
    }

    @Test
	public void testCheckFileAccessOnLockedFile() throws Exception {
        expectedEx.expectMessage("Error in reading file(s) : Access denied to file(s)");
    	experiment.checkFileAccess(System.getProperty("user.dir") + "/data/packet/test_files/locked_test_file.pcap");    	
    }
    
    @Test
    public void testInitWithPcapFileCheck() throws Exception {
    	
    	try{
    		String pcapPath = System.getProperty("user.dir") + "/data/packet/DNS_Traffic000.pcap";
        	
        	String protocolGraphStr = "graph start {\n" + 
        			"\n" + 
        			"	ethernet;\n" + 
        			"\n" + 
        			"}\n" + 
        			"\n" + 
        			"graph ethernet {\n" + 
        			"	switch(ethertype) {\n" + 
        			"		case 0800:			 ipv4;\n" + 
        			"	}\n" + 
        			"}\n" + 
        			"graph ipv4 {\n" + 
        			"	switch(protocol) {\n" + 
        			"		case 06: tcp;\n" + 
        			"	}\n" + 
        			"}\n" + 
        			"\n" + 
        			"\n" + 
        			"graph tcp {\n" + 
        			"\n" + 
        			"}\n" + 
        			"\n" + 
        			"graph end {\n" + 
        			"}";
    		experiment.initWithPcapFileCheck(pcapPath, protocolGraphStr);
    		assertEquals(pcapPath,experiment.getPcapPath());
    		assertEquals(protocolGraphStr,experiment.getProtocolGraphStr());
    		}
  	   catch(Exception e){
  	      fail("Should not have thrown any exception");
  	   }
    }
}
