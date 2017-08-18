package ml.anon.recognition.rulebased.api.model;

import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.anonymization.model.Anonymization;
import ml.anon.documentmanagement.model.Document;

import java.util.List;

/**
 * Created by mirco on 13.06.17.
 */
public interface Applicable {

    List<Anonymization> apply(Document doc, ReplacementResource repl) throws Exception;

}
