package ml.anon.recognition.rulebased.repository;

import ml.anon.model.anonymization.Label;
import ml.anon.recognition.rulebased.api.model.AbstractRule;
import ml.anon.recognition.rulebased.api.model.RegExpRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mirco on 11.06.17.
 */
@Repository
public interface RuleRepository extends MongoRepository<AbstractRule, String>, RuleRepositoryCustomEditable {

    List<RegExpRule> findByCore(boolean core);

    List<RegExpRule> findByLabel(Label label);
}
