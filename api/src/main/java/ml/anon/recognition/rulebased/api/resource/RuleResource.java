package ml.anon.recognition.rulebased.api.resource;

import java.util.List;
import javax.annotation.Resource;
import ml.anon.recognition.rulebased.api.model.Rule;
import ml.anon.recognition.rulebased.api.model.RuleImpl;
import ml.anon.resource.Create;
import ml.anon.resource.Read;
import ml.anon.resource.Update;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mirco on 16.08.17.
 */
@Component
public class RuleResource implements Create<RuleImpl>, Read<RuleImpl>, Update<RuleImpl> {


  private RestTemplate restTemplate = new RestTemplate();

  private String baseUrl = "http://localhost:9002" + "/api/rule";

  @Override
  public RuleImpl findById(String id) {
    ResponseEntity<RuleImpl> response = restTemplate
        .getForEntity(baseUrl + "/{id}", RuleImpl.class, id);
    return response.getBody();
  }

  @Override
  public List<RuleImpl> findAll() {
    ResponseEntity<List<RuleImpl>> result = restTemplate
        .exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<RuleImpl>>() {
        });
    return result.getBody();
  }

  @Override
  public RuleImpl update(RuleImpl instance) {
    return null;
  }

  @Override
  public RuleImpl create(RuleImpl instance) {
    return null;
  }
}
