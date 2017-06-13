package ml.anon.recognition.rulebased.model;

import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.docmgmt.Document;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mirco on 13.06.17.
 */
public class LicencePlateRule extends AbstractRule {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 7;
    private RegExp wrapped = RegExp.builder().label(Label.LICENCE_PLATE).regExp(
            ("\\b([A-Z]{1,3} [A-Z]{1,4} \\d{1,4})\\b")).build();

    @Override
    public List<Anonymization> apply(Document doc, ReplacementGenerator repl) {
        return wrapped.apply(doc, repl).stream().filter(a -> a.getOriginal().length() <= MAX_LENGTH && a.getOriginal()
                .length() >= MIN_LENGTH).collect(Collectors.toList());

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
