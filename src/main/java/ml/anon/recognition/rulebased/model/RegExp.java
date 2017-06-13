package ml.anon.recognition.rulebased.model;

import lombok.Builder;
import lombok.Data;
import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.anonymization.Producer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mirco on 11.06.17.
 */
@Data
@Builder
@Document(collection = "RegExp")
public class RegExp extends AbstractRule implements Comparable<RegExp> {
    @Id
    private String id;
    private String regExp;
    private int order;
    private String comment;
    private Label label;
    private boolean core;


    @Override
    public int compareTo(@NotNull RegExp o) {
        return Comparator.comparing(RegExp::getLabel).thenComparing(RegExp::getOrder).compare(this, o);
    }

    @Override
    public List<Anonymization> apply(ml.anon.model.docmgmt.Document doc, ReplacementGenerator repl) {
        Matcher matcher = Pattern.compile(regExp).matcher(doc.fullText());
        List<Anonymization> results = new ArrayList<>();
        while (matcher.find()) {
            results.add(Anonymization.builder().label(label).replacement(repl.generateReplacement(matcher.group(0), label))
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
