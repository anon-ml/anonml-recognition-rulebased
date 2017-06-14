package ml.anon.recognition.rulebased.model;

import com.google.common.collect.Lists;
import lombok.Builder;
import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.docmgmt.Document;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by mirco on 13.06.17.
 */

public class IBANRule extends AbstractRule {


    private Rule iban = RegExpRule.builder().regExp("[A-Z]{2}\\s?\\d{2}([A-Z0-9 ]){11,32}\\b").label(getLabel()).build();

    @Builder
    private IBANRule(String id, boolean core, boolean active, boolean editable, int order, String name, Label label, List<Predicate<?>> additionalConstraints, Rule iban) {
        super(id, core, active, editable, order, name, label, additionalConstraints);
        this.iban = iban;
    }

    public IBANRule() {
        super(null, true, true, false, 0, "IBAN", Label.IBAN, Lists.newArrayList());
    }

    @Override
    public List<Anonymization> apply(Document doc, ReplacementGenerator repl) {
        List<Anonymization> apply = iban.apply(doc, repl);
        for (Anonymization anonymization : apply) {
            boolean valid = true;
            try {
                String s = IbanUtil.calculateCheckDigit(anonymization.getOriginal());
                IbanUtil.validate(anonymization.getOriginal());
            } catch (IbanFormatException e) {
                valid = false;
            }
            System.err.print(valid);
        }
        return apply;

    }

}
