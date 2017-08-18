package ml.anon.recognition.rulebased.initalization;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import ml.anon.anonymization.model.Label;
import ml.anon.recognition.rulebased.api.constraints.Length;

import ml.anon.recognition.rulebased.api.model.Rule;

/**
 * Created by mirco on 11.06.17.
 */
class CoreRules {

  @Getter
  private final Multimap<Label, Rule> map = ArrayListMultimap.create();


  public CoreRules() {

    map.put(Label.BIRTHDATE, Rule.builder().core(true).label(Label.BIRTHDATE).active(true)
        .regExp("(geboren|geb\\.?)\\s(am\\s)?" +
            "(\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}" +
            "|" +
            "\\d{1,2}\\.?\\s[A-Za-z]+\\s\\d{2,4})").build());

    map.put(Label.TELEPHONE_NUMBER, Rule.builder().core(true).label(Label.TELEPHONE_NUMBER)
        .regExp("/^\\+?(\\d.*){3,}$/").build());

    map.put(Label.URL, Rule.builder().core(true).active(true).label(Label.URL).regExp(
        "/^((https?|ftp|file):\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/")
        .build());
    map.put(Label.IP, Rule.builder().core(true).active(true).label(Label.URL).regExp(
        "/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/")
        .name("IP v4").build());
    map.put(Label.IP, Rule.builder().core(true).active(true).label(Label.URL).regExp(
        "/^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}"
            +
            "(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4})"
            +
            "{1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]"
            +
            "{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$/")
        .name("IP v6").build());

    map.put(Label.IBAN,
        Rule.builder().active(true).core(true).editable(false).label(Label.IBAN).constrain(
            Length.minLength(10)).constrain(Length.maxLength(36)).name("IBAN")
            .regExp("[A-Z]{2}\\s?\\d{2}\\s?([0-9a-zA-Z]{4}\\s?){2,8}([A-Z0-9]{2})?[\\.,\\s]{0,1}")
            .build());

    map.put(Label.LICENCE_PLATE,
        Rule.builder().active(true).core(true).editable(false).label(Label.LICENCE_PLATE)
            .constrain(
                Length.minLength(3)).constrain(Length.maxLength(7))
            .regExp("\\b[A-Z]{1,3} [A-Z]{1,4} [1-9]\\d{0,3}\\.?\\b").name("Kennzeichen")
            .build());

    map.put(Label.CLIENT_NUMBER,
        Rule.builder().core(true).active(true).label(Label.CLIENT_NUMBER)
            .regExp(
                "\\b(Kunden|Vertrags|Liefer|Klienten)-?([Nn]ummer|Nr\\.?|\\sNr\\.?):?\\s[A-Z0-9]+\\b")
            .build());

    map.put(Label.E_MAIL,
        Rule.builder().core(true).label(Label.E_MAIL).active(true).regExp("/^.+@.+$/").build());

  }


}
