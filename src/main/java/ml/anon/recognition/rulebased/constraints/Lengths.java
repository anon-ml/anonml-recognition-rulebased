package ml.anon.recognition.rulebased.constraints;

import lombok.AllArgsConstructor;

import java.util.function.Predicate;

/**
 * Created by mirco on 14.06.17.
 */
public class Lengths {


    public static Predicate<String> minLength(int length) {
        return new Length(length, true);
    }

    public static Predicate<String> maxLength(int length) {
        return new Length(length, false);
    }


    @AllArgsConstructor
    public static class Length implements Predicate<String> {

        private int length;
        private boolean minLength;

        @Override
        public boolean test(String s) {
            return minLength ? s.length() >= length : s.length() <= length;
        }
    }


}




