package ml.anon.recognition.rulebased.api.model;

import com.google.common.collect.Lists;
import ml.anon.annotation.ReplacementGenerator;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.docmgmt.Document;
import ml.anon.model.docmgmt.FileType;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mirco on 13.06.17.
 */
public class IBANRuleTest {

    public Document iban = Document.builder().text(
            Lists.newArrayList("Eine valide IBAN DE85282501100032304180 AD1400080001001234567890 ", "Keine AT48320000001234586400000000000000000 "))
            .originalFileType(FileType.PDF).build();

    public Rule ibanRule = new IBANRule();

    @Test
    public void apply() throws Exception {
        List<Anonymization> apply = ibanRule.apply(iban, new ReplacementGenerator());
        assertThat(apply.size(), is(1));
        assertThat(apply.get(0), is("AD1400080001001234567890"));

    }


}