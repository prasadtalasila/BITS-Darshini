package in.ac.bits.protocolanalyzer.mvc.model;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateModel {

    private int experimentId;
    private JSONObject graph;
}
