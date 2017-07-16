package ml.anon.recognition.rulebased.api.model;

import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.anonymization.model.Anonymization;
import ml.anon.documentmanagement.model.Document;
import ml.anon.documentmanagement.model.FileType;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by mirco on 13.06.17.
 */
@Ignore
public class LicencePlateRuleTest {

    public Document text = Document.builder().text(Collections.singletonList(
            "Das ist ein String mit Nummernschildern: HH AA 123 oder AA BB 2. Das hier ist kein valides Nummernschild: AAA BBBB 12. Das auch nicht: HH AA 012")).
            originalFileType(FileType.PDF).build();
    public Rule lpRule = new LicencePlateRule();
    public ReplacementResource gen = new ReplacementResource();

    @Test
    public void shouldFindValidLicencePlate() throws Exception {
        List<Anonymization> apply = lpRule.apply(text, gen);
        assertThat(apply.size(), is(2));
        assertThat(apply.get(0).getOriginal(), is("HH AA 123"));
        assertThat(apply.get(1).getOriginal(), is("AA BB 2"));
    }
}
