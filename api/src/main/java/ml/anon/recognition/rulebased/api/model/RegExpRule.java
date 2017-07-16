package ml.anon.recognition.rulebased.api.model;

import lombok.Builder;
import lombok.Data;
import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.documentmanagement.model.Document;
import ml.anon.anonymization.model.Anonymization;
import ml.anon.anonymization.model.Label;
import ml.anon.anonymization.model.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mirco on 11.06.17.
 */
@Data

public class RegExpRule extends AbstractRule {

    private String regExp;

    @Builder
    private RegExpRule(String id, boolean core, boolean active, boolean editable, double weight, String name, Label label, List<Predicate<String>> additionalConstraints, String regExp) {
        super(id, core, active, editable, weight, name, label, additionalConstraints);
        this.regExp = regExp;
    }

    public RegExpRule() {
        super(null, true, true, true, 0d, null, null, null);
    }


    @Override
    public List<Anonymization> apply(Document doc, ReplacementResource repl) throws Exception {
        Matcher matcher = Pattern.compile(regExp).matcher(doc.getFullText());
        List<Anonymization> results = new ArrayList<>();


        while (matcher.find()) {
            results.add(Anonymization.builder().label(getLabel()).replacement(repl.generateReplacement(matcher.group(0), getLabel()))
                    .producer(Producer.RULE)
                    .original(matcher.group(0)).build());
        }
        return results;
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
