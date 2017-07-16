package ml.anon.recognition.rulebased.controller;

import ml.anon.anonymization.model.Anonymization;
import ml.anon.documentmanagement.model.Document;
import ml.anon.documentmanagement.resource.DocumentResource;
import ml.anon.recognition.rulebased.service.AnnotationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by mirco on 11.06.17.
 */
@RestController
public class RuleApiController {

    @Resource
    private AnnotationService annotationService;
    private DocumentResource documentResource = new DocumentResource(new RestTemplate());


    @PostMapping("/rules/annotate/{id}")
    public List<Anonymization> annotate(@PathVariable String id) throws Exception {
        ResponseEntity<Document> resp = documentResource.getDocument(id);
        Document doc = resp.getBody();

        return annotationService.annotate(doc);
    }

}
