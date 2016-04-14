package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.ExperimentDetails;

public interface DetailsRepository
        extends ElasticsearchRepository<ExperimentDetails, String> {

    public ExperimentDetails findByExperimentName(String experimentName);

}
