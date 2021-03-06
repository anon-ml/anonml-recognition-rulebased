package ml.anon.recognition.rulebased.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import ml.anon.anonymization.model.*;
import ml.anon.documentmanagement.model.Document;
import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.model.BaseEntity;
import ml.anon.recognition.rulebased.api.constraints.Constraint;
import org.springframework.data.annotation.Id;

/**
 * Created by mirco on 16.08.17.
 */

@Builder
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection = "Rule")
public class Rule extends BaseEntity implements Applicable {

    @NotNull
    private String regExp;

    private boolean core;
    private boolean active = true;
    private boolean editable = false;
    private double weight;
    private String name;

    @NotNull
    private Label label;

    @Singular
    private List<Constraint> constrains;

    public Rule copy() {
        return Rule.builder().active(active).regExp(regExp).core(false).active(active).weight(weight)
                .editable(true).weight(weight).name(name + " (copy)").label(label).build();
    }


    @Override
    public List<Anonymization> apply(Document doc, ReplacementResource repl) throws Exception {
        Matcher matcher = Pattern.compile(regExp).matcher(doc.getFullText());
        List<Anonymization> regExpResults = new ArrayList<>();

        while (matcher.find()) {
            regExpResults.add(Anonymization.builder()
                    .data(
                            repl.create(Replacement.builder().label(label).original(matcher.group(0)).build()))
                    .producer(Producer.RULE).status(Status.PROCESSING).build());
        }
        return applyConstrains(regExpResults);
    }

    protected List<Anonymization> applyConstrains(List<Anonymization> anonymizations) {
        if (constrains != null) {
            return anonymizations.stream()
                    .filter(s -> constrains.stream().allMatch(c -> c.test(s.getData().getOriginal())))
                    .collect(
                            Collectors.toList());
        }
        return anonymizations;

    }

}
