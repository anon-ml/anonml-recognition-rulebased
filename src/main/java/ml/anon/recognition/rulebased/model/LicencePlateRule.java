package ml.anon.recognition.rulebased.model;

import com.google.common.collect.Lists;
import lombok.Builder;
import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.docmgmt.Document;
import ml.anon.recognition.rulebased.constraints.Lengths;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by mirco on 13.06.17.
 */
public class LicencePlateRule extends AbstractRule {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 7;
    private RegExpRule wrapped = RegExpRule.builder().label(getLabel()).regExp(
            "\\b[A-Z]{1,3} [A-Z]{1,4} [1-9]\\d{0,3}\\.?\\b").build();

    @Builder
    private LicencePlateRule(String id, boolean core, boolean active, boolean editable, int order, String name, Label label, List<Predicate<?>> additionalConstraints, RegExpRule wrapped) {
        super(id, core, active, editable, order, name, label, additionalConstraints);
        this.wrapped = wrapped;
    }

    public LicencePlateRule() {
        super(null, true, true, false, 0, "Kennzeichen", Label.LICENCE_PLATE,
                Lists.newArrayList(Lengths.minLength(3), Lengths.maxLength(7)));
    }

    @Override
    public List<Anonymization> apply(Document doc, ReplacementGenerator repl) {

        List<Anonymization> regExp = wrapped.apply(doc, repl);
        regExp.forEach(w -> {
            if (w.getOriginal().endsWith(".")) {
                System.err.println(w.getOriginal());
                w.setOriginal(w.getOriginal().replace(".", ""));
            }
        });
        return regExp.stream().filter(a -> {
            String s = a.getOriginal().replaceAll(" ", "");
            return s.length() >= MIN_LENGTH && s.length() <= MAX_LENGTH;
        }).collect(Collectors.toList());

    }


}
