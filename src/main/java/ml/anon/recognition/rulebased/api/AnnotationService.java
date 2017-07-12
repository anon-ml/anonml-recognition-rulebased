package ml.anon.recognition.rulebased.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.java.Log;
import ml.anon.annotation.ReplacementAccess;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.docmgmt.Document;
import ml.anon.recognition.rulebased.api.model.AbstractRule;
import ml.anon.recognition.rulebased.api.model.LicencePlateRule;
import ml.anon.recognition.rulebased.api.repository.RuleRepository;
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

    private Multimap<Label, AbstractRule> rules = ArrayListMultimap.create();


    private void refreshRules() {
        log.info("refreshing rules");
        rules.clear();
        List<AbstractRule> regExpRule = repo.findAll();
        rules.put(Label.LICENCE_PLATE, new LicencePlateRule());
        regExpRule.forEach(rule -> rules.put(rule.getLabel(), rule));

    }

    public List<Anonymization> annotate(Document doc) throws Exception {
        refreshRules();
        List<Anonymization> results = new ArrayList<>();
        for (Map.Entry<Label, Collection<AbstractRule>> entry : rules.asMap().entrySet()) {
            List<Anonymization> ruleResults = new ArrayList<>();
            Multimap<Double, AbstractRule> weights = ArrayListMultimap.create();
            SortedSet<Double> keys = new TreeSet<>();

            entry.getValue().forEach(r -> {
                keys.add(r.getWeight());
                weights.put(r.getWeight(), r);
            });

            for (Double key : keys) {
                if (ruleResults.isEmpty()) {
                    for (AbstractRule r : weights.get(key)) {
                        if (r.isActive()) {
                            List<Anonymization> apply = r.apply(doc, new ReplacementAccess());
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
