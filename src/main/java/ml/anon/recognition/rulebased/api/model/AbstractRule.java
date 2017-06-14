package ml.anon.recognition.rulebased.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.anon.model.anonymization.Label;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by mirco on 13.06.17.
 */
@Data
@Document(collection = "Rule")
@AllArgsConstructor
public abstract class AbstractRule implements Rule {
    @Id
    private String id;
    private boolean core;
    private boolean active = true;
    private boolean editable = false;
    private double order;
    private String name;
    private Label label;

    private List<Predicate<?>> additionalConstraints;


}
