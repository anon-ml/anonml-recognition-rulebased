package ml.anon.recognition.rulebased.api.resource;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import ml.anon.recognition.rulebased.api.model.Rule;
import ml.anon.resource.Create;
import ml.anon.resource.Delete;
import ml.anon.resource.Read;
import ml.anon.resource.Update;
import org.springframework.beans.factory.annotation.Value;
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
public class RuleResource implements Create<Rule>, Read<Rule>, Update<Rule>,
    Delete {


  private RestTemplate restTemplate = new RestTemplate();
  @Value("${rulebased.service.url}")
  private String rulebasedUrl;
  private String baseUrl;


  @PostConstruct
  public void init() {
    baseUrl = rulebasedUrl + "/api/rule";
  }

  @Override
  public Rule findById(String id) {
    log.debug("Find Applicable with id {}", id);
    ResponseEntity<Rule> response = restTemplate
        .getForEntity(baseUrl + "/{id}", Rule.class, id);
    return response.getBody();
  }

  @Override
  public List<Rule> findAll() {
    log.debug("Find all rules");
    ResponseEntity<List<Rule>> result = restTemplate
        .exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Rule>>() {
        });
    return result.getBody();
  }

  @Override
  public Rule update(String id, Rule instance) {
    log.debug("Update rule {}", instance);
    ResponseEntity<Rule> exchange = restTemplate
        .exchange(baseUrl + "/{id}", HttpMethod.PUT, new HttpEntity<>(instance), Rule.class,
            id);
    return exchange.getBody();
  }

  @Override
  public Rule create(Rule instance) {
    log.debug("Create rule {}", instance);
    ResponseEntity<Rule> exchange = restTemplate
        .exchange(baseUrl + "", HttpMethod.POST, new HttpEntity<>(instance), Rule.class
        );
    return exchange.getBody();
  }

  @Override
  public void delete(String id) {
    log.debug("Delete rule with id {}", id);
    restTemplate.delete(baseUrl + "/{id}", id);
  }
}
