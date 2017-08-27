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
public class AnnotationController {

  @Resource
  private AnnotationService annotationService;
  @Resource
  private DocumentResource documentResource;

  @PostMapping("/rules/annotate/{id}")
  public List<Anonymization> annotate(@PathVariable String id) throws Exception {
    Document doc = documentResource.findById(id);
    return annotationService.annotate(doc);
  }

}
