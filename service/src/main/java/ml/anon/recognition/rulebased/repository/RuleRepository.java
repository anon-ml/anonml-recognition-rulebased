package ml.anon.recognition.rulebased.repository;

import ml.anon.anonymization.model.Label;
import ml.anon.recognition.rulebased.api.model.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mirco on 11.06.17.
 */
@Repository
public interface RuleRepository extends MongoRepository<Rule, String>,
    RuleRepositoryCustomEditable {

  List<Rule> findByCore(boolean core);

  List<Rule> findByLabel(Label label);
}
