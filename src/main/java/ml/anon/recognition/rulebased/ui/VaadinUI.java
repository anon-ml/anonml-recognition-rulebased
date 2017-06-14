package ml.anon.recognition.rulebased.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import ml.anon.recognition.rulebased.api.repository.RuleRepository;

import javax.annotation.Resource;

/**
 * Created by mirco on 14.06.17.
 */
@SpringUI(path = "/admin")
@Theme("valo")
@Slf4j
public class VaadinUI extends UI {

    public static final String ALL = "";
    public static final String REGEX = "REGEX";
    @Resource
    public RuleRepository repo;
    @Resource
    public AllRulesOverview all;
    @Resource
    public RegExRuleOverview regEx;
    public Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();

        layout.setMargin(false);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);

        navigator.addView(ALL, all);
        navigator.addView(REGEX, regEx);
    }
}
