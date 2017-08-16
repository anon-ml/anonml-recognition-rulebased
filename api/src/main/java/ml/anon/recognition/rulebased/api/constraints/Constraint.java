package ml.anon.recognition.rulebased.api.constraints;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ml.anon.recognition.rulebased.api.constraints.Length.LengthConstraint;

/**
 * Created by mirco on 16.08.17.
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @Type(value = LengthConstraint.class, name = "length"),

})
public abstract class Constraint {

  /**
   * True if the string matches the constraint
   */
  public abstract boolean test(String s);

}
