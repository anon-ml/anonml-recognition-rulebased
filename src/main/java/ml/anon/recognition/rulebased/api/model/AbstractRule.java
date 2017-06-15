package ml.anon.recognition.rulebased.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by mirco on 13.06.17.
 */
@Data
@Document(collection = "Rule")
@AllArgsConstructor
public abstract class AbstractRule implements Rule, Comparable<AbstractRule> {
    @Id
    private String id;
    private boolean core;
    private boolean active = true;
    private boolean editable = false;
    private double weight;
    private String name;
    private Label label;

    private List<Predicate<String>> constrains;


    protected List<Anonymization> applyConstrains(List<Anonymization> anonymizations) {
        if (constrains != null) {
            return anonymizations.stream().filter(s -> constrains.stream().allMatch(c -> c.test(s.getOriginal()))).collect(Collectors.toList());
        }
        return anonymizations;

    }

    @Override
    public int compareTo(@NotNull AbstractRule o) {
        return Double.compare(weight, o.weight);
    }
}
