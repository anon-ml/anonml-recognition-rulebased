package ml.anon.recognition.rulebased.ui.overview;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import ml.anon.recognition.rulebased.api.model.AbstractRule;
import ml.anon.recognition.rulebased.api.repository.RuleRepository;
import ml.anon.recognition.rulebased.ui.Menu;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mirco on 14.06.17.
 */
@SpringComponent
@UIScope
@Slf4j
public class AllRulesOverview extends VerticalLayout implements View {


    private RuleRepository repo;
    private Grid<AbstractRule> grid = new Grid<>(AbstractRule.class);

    @Autowired
    public AllRulesOverview(RuleRepository repo) {
        this.repo = repo;
        setSizeFull();
        addComponents(new Menu(), grid);
        grid.setItems(repo.findAll());
        grid.asSingleSelect();
        grid.setSizeFull();
        grid.setCaption("Alle Regeln");


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        grid.markAsDirtyRecursive();
        grid.getDataProvider().refreshAll();
    }


}
