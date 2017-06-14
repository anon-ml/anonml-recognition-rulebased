package ml.anon.recognition.rulebased.model;

import lombok.Builder;
import lombok.Data;
import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.anonymization.Producer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mirco on 11.06.17.
 */
@Data

public class RegExpRule extends AbstractRule implements Comparable<RegExpRule> {

    private String regExp;

    @Builder
    private RegExpRule(String id, boolean core, boolean active, boolean editable, int order, String name, Label label, List<Predicate<?>> additionalConstraints, String regExp) {
        super(id, core, active, editable, order, name, label, additionalConstraints);
        this.regExp = regExp;
    }

    public RegExpRule() {
        super(null, true, true, true, 0, null, null, null);
    }


    @Override
    public int compareTo(@NotNull RegExpRule o) {
        return Comparator.comparing(RegExpRule::getLabel).thenComparing(RegExpRule::getOrder).compare(this, o);
    }

    @Override
    public List<Anonymization> apply(ml.anon.model.docmgmt.Document doc, ReplacementGenerator repl) {
        Matcher matcher = Pattern.compile(regExp).matcher(doc.fullText());
        List<Anonymization> results = new ArrayList<>();
        int matches = matcher.groupCount();
        int last = 0;
        while (matcher.find() && last <= matches) {
            results.add(Anonymization.builder().label(getLabel()).replacement(repl.generateReplacement(matcher.group(0), getLabel()))
                    .producer(Producer.RULE)
                    .original(matcher.group(last++)).build());
        }
        return results;
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
