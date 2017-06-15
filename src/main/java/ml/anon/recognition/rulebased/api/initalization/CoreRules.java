package ml.anon.recognition.rulebased.api.initalization;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import ml.anon.model.anonymization.Label;
import ml.anon.recognition.rulebased.api.model.AbstractRule;
import ml.anon.recognition.rulebased.api.model.IBANRule;
import ml.anon.recognition.rulebased.api.model.LicencePlateRule;
import ml.anon.recognition.rulebased.api.model.RegExpRule;

/**
 * Created by mirco on 11.06.17.
 */
class CoreRules {

    @Getter
    private final Multimap<Label, AbstractRule> map = ArrayListMultimap.create();


    public CoreRules() {

        addBirthDates();
        addPhoneNumbers();
        addUrls();

        map.put(Label.IBAN, new IBANRule());
        map.put(Label.LICENCE_PLATE, new LicencePlateRule());

    }


    // https://projects.lukehaas.me/regexhub/
    private void addUrls() {

        map.put(Label.URL, RegExpRule.builder().core(true).label(Label.URL).regExp("/^((https?|ftp|file):\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/").build());
        map.put(Label.IP, RegExpRule.builder().core(true).label(Label.URL).regExp("/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/").name("IP v4").build());
        map.put(Label.IP, RegExpRule.builder().core(true).label(Label.URL).regExp("/^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}" +
                "(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4})" +
                "{1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]" +
                "{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$/").name("IP v6").build());

    }

    // https://projects.lukehaas.me/regexhub/
    private void addPhoneNumbers() {
        map.put(Label.TELEPHONE_NUMBER, RegExpRule.builder().core(true).label(Label.TELEPHONE_NUMBER).regExp("/^\\+?(\\d.*){3,}$/").build());
    }

    private void addBirthDates() {
        map.put(Label.BIRTHDATE, RegExpRule.builder().core(true).label(Label.BIRTHDATE).regExp("\\b\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}\\[,\\.]?b").build());
    }


}
