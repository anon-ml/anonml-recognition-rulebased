package ml.anon.recognition.rulebased.api.model;

import com.google.common.collect.Lists;
import lombok.Builder;
import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.anonymization.model.Anonymization;
import ml.anon.anonymization.model.Label;
import ml.anon.documentmanagement.model.Document;
import ml.anon.recognition.rulebased.api.constraints.Lengths;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by mirco on 13.06.17.
 */


public class LicencePlateRule extends AbstractRule {

    private RegExpRule wrapped = RegExpRule.builder().label(getLabel()).regExp(
            "\\b[A-Z]{1,3} [A-Z]{1,4} [1-9]\\d{0,3}\\.?\\b").build();

    @Builder
    private LicencePlateRule(String id, boolean core, boolean active, boolean editable, double order, String name, Label label, List<Predicate<String>> additionalConstraints, RegExpRule wrapped) {
        super(id, core, active, editable, order, name, label, additionalConstraints);
        this.wrapped = wrapped;
    }

    public LicencePlateRule() {
        super(null, true, true, false, 0, "Kennzeichen", Label.LICENCE_PLATE,
                Lists.newArrayList(Lengths.minLength(3), Lengths.maxLength(7)));
    }

    @Override
    public List<Anonymization> apply(Document doc, ReplacementResource repl) throws Exception {
        List<Anonymization> regExp = wrapped.apply(doc, repl);
        return applyConstrains(regExp);
    }


}
