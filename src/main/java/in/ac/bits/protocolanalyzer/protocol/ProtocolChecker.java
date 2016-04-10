package in.ac.bits.protocolanalyzer.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is one of the auto-generated classes (amongst custom analyzers, entities
 * and headers). By default, the defaultStatus variable is set to true so that
 * {@link Protocol} class will add default custom analyzers provided (ethernet,
 * ipv4 and tcp) to its list. If auto generation is used then this class will be
 * rewritten, setting defaultStatus variable to false, autowiring newly
 * generated custom analyzer and adding it to the protocol list (from the if
 * block in checkNAdd method by invoking Protocol.addCustomAnalyzer method)
 * 
 * @author crygnus
 *
 */
@Component
public class ProtocolChecker {
    private static boolean defaultStatus = true;

    @Autowired
    private Protocol protocol;

    public void checkNAdd() {
        protocol.defaultCustoms();
        if (!defaultStatus) {
        }
    }
}
