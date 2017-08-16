package ml.anon.recognition.rulebased.repository;

import ml.anon.anonymization.model.Label;
import ml.anon.recognition.rulebased.api.model.RuleImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mirco on 11.06.17.
 */
@Repository
public interface RuleRepository extends MongoRepository<RuleImpl, String>,
    RuleRepositoryCustomEditable {

  List<RuleImpl> findByCore(boolean core);

  List<RuleImpl> findByLabel(Label label);
}
