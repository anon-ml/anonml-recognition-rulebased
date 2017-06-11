package ml.anon.recognition.rulebased;

import lombok.extern.java.Log;
import ml.anon.model.anonymization.Anonymization;
import ml.anon.model.anonymization.Label;
import ml.anon.model.anonymization.Producer;
import ml.anon.model.docmgmt.Document;
import ml.anon.recognition.rulebased.model.RegExp;
import ml.anon.recognition.rulebased.model.RegExpRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by mirco on 11.06.17.
 */

@Service
@Log
public class AnnotationService {

    @Resource
    private RegExpRepository repo;

    public List<Anonymization> annotate(Document doc) {
        List<Anonymization> result = new ArrayList<>();
        for (Label label : Label.getAll()) {
            Integer order = 1;
            while (order != null) {
                List<RegExp> byOrder = getByOrder(label, order);
                for (RegExp regExp : byOrder) {
                    Pattern pattern = Pattern.compile(regExp.getRegExp());
                    Matcher matcher = pattern.matcher(doc.fullText());
                    log.info("Try " + regExp.getRegExp());
                    while (matcher.find()) {
                        result.add(Anonymization.builder().label(label).replacement("#################").producer(Producer.REGEX).original(matcher.group(0)).build());
                    }

                }
                if (byOrder.isEmpty()) {
                    order = null;
                } else {
                    order++;
                }
            }
        }
        return result;
    }

    private List<RegExp> getByOrder(Label label, int order) {

        return repo.findByLabel(label).stream().filter(a -> a.getOrder() == order).collect(Collectors.toList());
    }
}
