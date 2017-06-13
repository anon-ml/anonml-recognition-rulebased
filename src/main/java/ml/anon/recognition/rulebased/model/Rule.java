package ml.anon.recognition.rulebased.model;

import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.docmgmt.Document;

import java.util.List;

/**
 * Created by mirco on 13.06.17.
 */
public interface Rule {

    List<Anonymization> apply(Document doc, ReplacementGenerator repl);

    /**
     * if can this rule be edited via an admin interface
     */
    boolean isEditable();

    boolean isActive();

    void setActive(boolean active);

    int getOrder();
}
