package ml.anon.recognition.rulebased.api.constraints;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.beans.ConstructorProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mirco on 14.06.17.
 */
public class Length {


  public static ml.anon.recognition.rulebased.api.constraints.Constraint minLength(int length) {
    return new LengthConstraint(length, true);
  }

  public static ml.anon.recognition.rulebased.api.constraints.Constraint maxLength(int length) {
    return new LengthConstraint(length, false);
  }

  @Data
  @NoArgsConstructor
  public static class LengthConstraint extends
      Constraint {

    @JsonCreator
    @ConstructorProperties({"length", "minLength"})
    public LengthConstraint(int length, boolean minLength) {
      this.length = length;
      this.minLength = minLength;
    }

    private int length;
    private boolean minLength;

    @Override
    public String toString() {
      String str = "length: " + length;
      return minLength ? "min. " + str : "max. " + str;
    }

    @Override
    public boolean test(String s) {
      return minLength ? s.length() >= length : s.length() <= length;
    }
  }


}




