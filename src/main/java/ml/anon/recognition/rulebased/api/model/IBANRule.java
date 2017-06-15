package ml.anon.recognition.rulebased.api.model;

import com.google.common.collect.Lists;
import lombok.Builder;
import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.docmgmt.Document;
import ml.anon.recognition.rulebased.api.constraints.Lengths;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by mirco on 13.06.17.
 */

public class IBANRule extends AbstractRule {


    private Rule iban = RegExpRule.builder().regExp("[A-Z]{2}\\s?\\d{2}\\s?([0-9a-zA-Z]{4}\\s?){1,8}([A-Z0-9]{2})?[\\.,\\s]{0,1}").label(getLabel()).build();

    @Builder
    private IBANRule(String id, boolean core, boolean active, boolean editable, double order, String name, Label label, List<Predicate<String>> additionalConstraints, Rule iban) {
        super(id, core, active, editable, order, name, label, additionalConstraints);
        this.iban = iban;
    }

    public IBANRule() {
        super(null, true, true, false, 0, "IBAN", Label.IBAN, Lists.newArrayList(Lengths.minLength(4), Lengths.maxLength(40)));
    }

    @Override
    public List<Anonymization> apply(Document doc, ReplacementGenerator repl) {
        List<Anonymization> apply = iban.apply(doc, repl);
        return applyConstrains(apply);

    }

}