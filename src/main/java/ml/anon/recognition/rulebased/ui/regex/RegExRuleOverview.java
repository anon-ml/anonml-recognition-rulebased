package ml.anon.recognition.rulebased.ui.regex;

import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import ml.anon.model.anonymization.Label;
import ml.anon.recognition.rulebased.api.model.RegExpRule;
import ml.anon.recognition.rulebased.api.repository.RuleRepository;
import ml.anon.recognition.rulebased.ui.Menu;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

/**
 * Created by mirco on 14.06.17.
 */
@SpringComponent
@UIScope
public class RegExRuleOverview extends VerticalLayout implements View {


    private RuleRepository repo;
    private Grid<RegExpRule> grid = new Grid<>();

    @Autowired
    public RegExRuleOverview(RuleRepository repo) {

        this.repo = repo;
        setSizeFull();
        RuleEditor editor = new RuleEditor(repo, grid);
        VerticalLayout mainLayout = new VerticalLayout(grid, editor);
        mainLayout.setMargin(false);

        addComponent(new Menu());
        addComponent(mainLayout);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                editor.changeBoundRule((e.getValue()));
            }
        });

        mainLayout.setSizeFull();

        grid.setDataProvider(new RegExProvider());

        grid.addColumn(r -> BooleanUtils.toString(r.isActive(), "T", "F")).setCaption("Aktiv");
        Grid.Column<RegExpRule, String> name = grid.addColumn(RegExpRule::getName).setCaption("Name");
        Grid.Column<RegExpRule, Label> label = grid.addColumn(RegExpRule::getLabel).setCaption("Label");
        Grid.Column<RegExpRule, Double> weight = grid.addColumn(RegExpRule::getWeight).setCaption("Gewicht");
        Grid.Column<RegExpRule, String> regEx = grid.addColumn(r -> StringUtils.abbreviate(StringUtils.defaultString(r.getRegExp()), 50)).setCaption("RegEx");
        name.setExpandRatio(1);
        label.setExpandRatio(1);
        regEx.setExpandRatio(1);

        grid.setCaption("RegEx Regeln");

        grid.setSizeFull();
        editor.setWidth(100, Unit.PERCENTAGE);
    }

    @PostConstruct
    public void init() {

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        System.out.print(viewChangeEvent);
    }

    private class RegExProvider extends AbstractBackEndDataProvider<RegExpRule, Void> {

        @Override
        protected Stream<RegExpRule> fetchFromBackEnd(Query<RegExpRule, Void> query) {
            return repo.findAllEditable().stream();
        }

        @Override
        protected int sizeInBackEnd(Query<RegExpRule, Void> query) {
            return repo.findAllEditable().size();
        }
    }

}
