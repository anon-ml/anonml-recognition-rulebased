package ml.anon.recognition.rulebased.model;

import lombok.Builder;
import lombok.Data;
import ml.anon.model.anonymization.Label;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;

/**
 * Created by mirco on 11.06.17.
 */
@Data
@Builder
@Document(collection = "RegExp")
public class RegExp implements Comparable<RegExp> {
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
}
