package ml.anon.recognition.rulebased.api.resource;

import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import ml.anon.recognition.rulebased.api.model.Rule;
import ml.anon.recognition.rulebased.api.model.RuleImpl;
import ml.anon.resource.Create;
import ml.anon.resource.Delete;
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
@Slf4j
@Component
public class RuleResource implements Create<RuleImpl>, Read<RuleImpl>, Update<RuleImpl>,
    Delete {


  private RestTemplate restTemplate = new RestTemplate();

  private String baseUrl = "http://localhost:9002" + "/api/rule";

  @Override
  public RuleImpl findById(String id) {
    log.trace("Find Rule with id {}", id);
    ResponseEntity<RuleImpl> response = restTemplate
        .getForEntity(baseUrl + "/{id}", RuleImpl.class, id);
    return response.getBody();
  }

  @Override
  public List<RuleImpl> findAll() {
    log.trace("Find all rules");
    ResponseEntity<List<RuleImpl>> result = restTemplate
        .exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<RuleImpl>>() {
        });
    return result.getBody();
  }

  @Override
  public RuleImpl update(RuleImpl instance) {
    log.trace("Update rule {}", instance);
    return null;
  }

  @Override
  public RuleImpl create(RuleImpl instance) {

    log.trace("Create rule {}", instance);
    return null;
  }

  @Override
  public void delete(String id) {
    log.trace("Delete rule with id {}", id);
  }
}
