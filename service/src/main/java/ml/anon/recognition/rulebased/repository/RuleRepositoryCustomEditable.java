package ml.anon.recognition.rulebased.repository;


import java.util.List;
import ml.anon.recognition.rulebased.api.model.RuleImpl;

/**
 * Created by mirco on 14.06.17.
 */
public interface RuleRepositoryCustomEditable {

  List<RuleImpl> findAllEditable();
}
