package ml.anon.recognition.rulebased.api.model;

import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.docmgmt.Document;
import ml.anon.model.docmgmt.FileType;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by mirco on 13.06.17.
 */
public class LicencePlateRuleTest {

    public Document text = Document.builder().text(Collections.singletonList(
            "Das ist ein String mit Nummernschildern: HH AA 123 oder AA BB 2. Das hier ist kein valides Nummernschild: AAA BBBB 12. Das auch nicht: HH AA 012")).
            originalFileType(FileType.PDF).build();
    public Rule lpRule = new LicencePlateRule();
    public ReplacementGenerator gen = new ReplacementGenerator();

    @Test
    public void shouldFindValidLicencePlate() {
        List<Anonymization> apply = lpRule.apply(text, gen);
        assertThat(apply.size(), is(2));
        assertThat(apply.get(0).getOriginal(), is("HH AA 123"));
        assertThat(apply.get(1).getOriginal(), is("AA BB 2"));
    }
}
