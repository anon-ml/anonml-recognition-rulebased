package ml.anon.recognition.rulebased.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import ml.anon.recognition.rulebased.api.model.Rule;
import ml.anon.recognition.rulebased.api.model.RuleImpl;
import ml.anon.recognition.rulebased.repository.RuleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mirco on 16.08.17.
 */
@RestController

public class RuleApiController {

  @Resource
  private RuleRepository ruleRepository;

  @GetMapping(path = "/api/rule/{id}")
  public RuleImpl getById(@RequestParam String id) {
    return ruleRepository.findOne(id);
  }

  @GetMapping(path = "/api/rule")
  public List<RuleImpl> getAll() {
    return ruleRepository.findAll();
  }


}
