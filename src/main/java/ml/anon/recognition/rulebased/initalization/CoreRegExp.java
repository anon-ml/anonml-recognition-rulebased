package ml.anon.recognition.rulebased.initalization;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import ml.anon.model.anonymization.Label;
import ml.anon.recognition.rulebased.model.RegExp;

/**
 * Created by mirco on 11.06.17.
 */
class CoreRegExp {

    @Getter
    private final Multimap<Label, RegExp> map = ArrayListMultimap.create();


    public CoreRegExp() {

        addBirthDates();
        addPhoneNumbers();
        addUrls();

    }


    // https://projects.lukehaas.me/regexhub/
    private void addUrls() {
        map.put(Label.URL, RegExp.builder().core(true).label(Label.URL).order(1).regExp("/^((https?|ftp|file):\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/").build());
        map.put(Label.IP, RegExp.builder().core(true).label(Label.URL).order(1).regExp("/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/").comment("IP v4").build());
        map.put(Label.IP, RegExp.builder().core(true).label(Label.URL).order(1).regExp("/^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}" +
                "(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4})" +
                "{1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]" +
                "{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$/").comment("IP v6").build());

    }

    // https://projects.lukehaas.me/regexhub/
    private void addPhoneNumbers() {
        map.put(Label.TELEPHONE_NUMBER, RegExp.builder().core(true).order(1).label(Label.TELEPHONE_NUMBER).regExp("/^\\+?(\\d.*){3,}$/").build());
    }

    private void addBirthDates() {
        map.put(Label.BIRTHDATE, RegExp.builder().core(true).label(Label.BIRTHDATE).order(1).regExp("\\b\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}\\b").build());
    }


}
