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
import org.springframework.http.HttpEntity;
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
    log.debug("Find Rule with id {}", id);
    ResponseEntity<RuleImpl> response = restTemplate
        .getForEntity(baseUrl + "/{id}", RuleImpl.class, id);
    return response.getBody();
  }

  @Override
  public List<RuleImpl> findAll() {
    log.debug("Find all rules");
    ResponseEntity<List<RuleImpl>> result = restTemplate
        .exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<RuleImpl>>() {
        });
    return result.getBody();
  }

  @Override
  public RuleImpl update(String id, RuleImpl instance) {
    log.debug("Update rule {}", instance);
    ResponseEntity<RuleImpl> exchange = restTemplate
        .exchange(baseUrl + "/{id}", HttpMethod.PUT, new HttpEntity<>(instance), RuleImpl.class,
            id);
    return exchange.getBody();
  }

  @Override
  public RuleImpl create(RuleImpl instance) {
    log.debug("Create rule {}", instance);
    ResponseEntity<RuleImpl> exchange = restTemplate
        .exchange(baseUrl + "", HttpMethod.POST, new HttpEntity<>(instance), RuleImpl.class
        );
    return exchange.getBody();
  }

  @Override
  public void delete(String id) {
    log.debug("Delete rule with id {}", id);
    restTemplate.delete(baseUrl + "/{id}", id);
  }
}
