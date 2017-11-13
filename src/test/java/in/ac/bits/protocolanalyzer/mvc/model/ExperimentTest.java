package in.ac.bits.protocolanalyzer.mvc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import config.in.ac.bits.protocolanalyzer.mvc.model.ExperimentTestConfig;

import in.ac.bits.protocolanalyzer.mvc.model.Experiment;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ExperimentTestConfig.class, loader = AnnotationConfigWebContextLoader.class)

public class ExperimentTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;

	@Autowired
	@Qualifier("Experiment")
	private Experiment experiment;
	
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
	
	@Test
	public void autowiringTest() {
		assertNotNull(experiment);
	}
	
	@Test
	public void testExperiment() {
		String protocolGraphStr = "graph start {\n\tethernet;\n}\ngraph ethernet {\n\tswitch(ethertype) {\n\t\tcase 0800:			 ipv4;\n\t}\n}\ngraph ipv4 {\n\tswitch(protocol) {\n\t\tcase 06: tcp;\n\t}\n}\ngraph tcp {\n}\ngraph end {\n}"; 
		String pcapPath = System.getProperty("user.dir") + "/data/packet/DNS_Traffic000.pcap";
		experiment.init(pcapPath  , protocolGraphStr);
		String experimentResults = experiment.analyze();
	    JSONObject jsonObj = new JSONObject(experimentResults);
	    assertEquals(100, jsonObj.get("packetCount"));
	    assertEquals("success", jsonObj.get("status"));
	}
}