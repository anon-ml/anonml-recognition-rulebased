package ml.anon.recognition.rulebased.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import ml.anon.recognition.rulebased.api.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * Created by mirco on 14.06.17.
 */
@SpringComponent
@UIScope
@Slf4j
public class AllRulesOverview extends VerticalLayout implements View {


    private RuleRepository repo;

    @Autowired
    public AllRulesOverview(RuleRepository repo) {
        this.repo = repo;
        addComponents(new Menu(), new Label("test"));
        log.info("->>" + Objects.toString(repo));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
