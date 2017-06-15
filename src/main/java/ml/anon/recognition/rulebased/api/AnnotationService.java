package ml.anon.recognition.rulebased.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.java.Log;
import ml.anon.annotation.ReplacementGenerator;
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

    public List<Anonymization> annotate(Document doc) {
        refreshRules();
        List<Anonymization> results = new ArrayList<>();
        for (Map.Entry<Label, Collection<AbstractRule>> entry : rules.asMap().entrySet()) {
            Multimap<Double, AbstractRule> weights = ArrayListMultimap.create();
            SortedSet<Double> keys = new TreeSet<>();

            entry.getValue().forEach(r -> {
                keys.add(r.getWeight());
                weights.put(r.getWeight(), r);
            });

            for (Double key : keys) {
                if (results.isEmpty()) {
                    weights.get(key).forEach(r -> results.addAll(r.apply(doc, new ReplacementGenerator())));
                }
            }
        }

        return results;
    }


}
