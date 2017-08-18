package ml.anon.recognition.rulebased.repository;


import ml.anon.recognition.rulebased.api.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by mirco on 14.06.17.
 */
public class RuleRepositoryImpl implements RuleRepositoryCustomEditable {

  @Autowired
  public MongoOperations ops;

  @Override
  public List<Rule> findAllEditable() {
    Query q = new Query().restrict(Rule.class);
    return ops.find(q, Rule.class);

  }
}
