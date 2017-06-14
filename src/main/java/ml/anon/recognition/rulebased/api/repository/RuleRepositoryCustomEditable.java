package ml.anon.recognition.rulebased.api.repository;

import ml.anon.recognition.rulebased.api.model.RegExpRule;

import java.util.List;

/**
 * Created by mirco on 14.06.17.
 */
public interface RuleRepositoryCustomEditable {

    List<RegExpRule> findAllEditable();
}
