package ml.anon.recognition.rulebased.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.java.Log;
import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.anonymization.model.Anonymization;
import ml.anon.anonymization.model.Label;
import ml.anon.documentmanagement.model.Document;

import ml.anon.recognition.rulebased.api.model.RuleImpl;
import ml.anon.recognition.rulebased.repository.RuleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by mirco on 11.06.17.
 */

@Service
@Log
public class AnnotationService {

  @Resource
  private RuleRepository repo;

  private Multimap<Label, RuleImpl> rules = ArrayListMultimap.create();


  private void refreshRules() {
    log.info("refreshing rules");
    rules.clear();
    List<RuleImpl> regExpRule = repo.findAll();

    regExpRule.forEach(rule -> rules.put(rule.getLabel(), rule));

  }

  public List<Anonymization> annotate(Document doc) throws Exception {
    refreshRules();
    List<Anonymization> results = new ArrayList<>();
    for (Map.Entry<Label, Collection<RuleImpl>> entry : rules.asMap().entrySet()) {
      List<Anonymization> ruleResults = new ArrayList<>();
      Multimap<Double, RuleImpl> weights = ArrayListMultimap.create();
      SortedSet<Double> keys = new TreeSet<>();

      entry.getValue().forEach(r -> {
        keys.add(r.getWeight());
        weights.put(r.getWeight(), r);
      });

      for (Double key : keys) {
        if (ruleResults.isEmpty()) {
          for (RuleImpl r : weights.get(key)) {
            if (r.isActive()) {
              List<Anonymization> apply = r.apply(doc, new ReplacementResource());
              log.info("Rule " + r.getName() + " (" + r.getLabel() + "): " + apply.toString());
              ruleResults.addAll(apply);
            }
          }
        }
      }
      results.addAll(ruleResults);
    }

    return results;
  }


}
