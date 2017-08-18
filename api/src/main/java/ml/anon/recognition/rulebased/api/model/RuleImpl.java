package ml.anon.recognition.rulebased.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import ml.anon.anonymization.model.Anonymization;
import ml.anon.anonymization.model.Label;
import ml.anon.anonymization.model.Producer;
import ml.anon.documentmanagement.model.Document;
import ml.anon.documentmanagement.resource.ReplacementResource;
import ml.anon.recognition.rulebased.api.constraints.Constraint;
import org.springframework.data.annotation.Id;

/**
 * Created by mirco on 16.08.17.
 */

@Builder
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection = "Rule")
public class RuleImpl implements Rule {

  @Id
  private String id;

  private String regExp;

  private boolean core;
  private boolean active = true;
  private boolean editable = false;
  private double weight;
  private String name;
  private Label label;

  @Singular
  private List<Constraint> constrains;


  @Override
  public List<Anonymization> apply(Document doc, ReplacementResource repl) throws Exception {
    Matcher matcher = Pattern.compile(regExp).matcher(doc.getFullText());
    List<Anonymization> regExpResults = new ArrayList<>();

    while (matcher.find()) {
      regExpResults.add(Anonymization.builder().label(label)
          .replacement(repl.generateReplacement(matcher.group(0), label))
          .producer(Producer.RULE)
          .original(matcher.group(0)).build());
    }
    return applyConstrains(regExpResults);
  }

  protected List<Anonymization> applyConstrains(List<Anonymization> anonymizations) {
    if (constrains != null) {
      return anonymizations.stream()
          .filter(s -> constrains.stream().allMatch(c -> c.test(s.getOriginal()))).collect(
              Collectors.toList());
    }
    return anonymizations;

  }

}