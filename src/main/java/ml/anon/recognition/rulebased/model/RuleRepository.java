package ml.anon.recognition.rulebased.model;

import ml.anon.model.anonymization.Label;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by mirco on 11.06.17.
 */
public interface RuleRepository extends MongoRepository<AbstractRule, String> {

    List<RegExpRule> findByCore(boolean core);

    List<RegExpRule> findByLabel(Label label);
}
