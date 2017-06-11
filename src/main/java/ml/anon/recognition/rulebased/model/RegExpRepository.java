package ml.anon.recognition.rulebased.model;

import ml.anon.model.anonymization.Label;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by mirco on 11.06.17.
 */
public interface RegExpRepository extends MongoRepository<RegExp, String> {

    List<RegExp> findByCore(boolean core);

    List<RegExp> findByLabel(Label label);
}
