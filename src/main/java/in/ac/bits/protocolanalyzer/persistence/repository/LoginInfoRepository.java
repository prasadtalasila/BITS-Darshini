package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import in.ac.bits.protocolanalyzer.persistence.entity.LoginInfoEntity;

public interface LoginInfoRepository
        extends ElasticsearchRepository<LoginInfoEntity, String> {

    public LoginInfoEntity findByEmail(String email);

}
