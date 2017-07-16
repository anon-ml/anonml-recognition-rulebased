package ml.anon.recognition.rulebased.api.model;

import ml.anon.annotation.ReplacementAccess;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.documentmanagement.model.Document;

import java.util.List;

/**
 * Created by mirco on 13.06.17.
 */
public interface Rule {

    List<Anonymization> apply(Document doc, ReplacementAccess repl) throws Exception;

}
