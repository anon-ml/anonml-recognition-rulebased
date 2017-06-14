package ml.anon.recognition.rulebased.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import ml.anon.model.anonymization.Label;
import ml.anon.recognition.rulebased.api.model.RegExpRule;
import ml.anon.recognition.rulebased.api.repository.RuleRepository;
import org.apache.commons.lang.BooleanUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.annotation.Resource;
import java.util.stream.Stream;

/**
 * Created by mirco on 14.06.17.
 */
@SpringUI(path = "/admin")
@Theme("valo")
@Slf4j
public class MainUI extends UI {

    @Resource
    private RuleRepository repo;

    @Resource
    private MongoOperations ops;


    private Grid<RegExpRule> grid = new Grid<>();


    @Override
    protected void init(VaadinRequest request) {
        RuleEditor editor = new RuleEditor(repo, grid);
        VerticalLayout mainLayout = new VerticalLayout(grid, editor);

        setContent(mainLayout);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                editor.changeBoundRule((e.getValue()));
            }
        });

        mainLayout.setSizeFull();

        grid.setDataProvider(new RegExProvider());

        grid.addColumn(r -> BooleanUtils.toString(r.isActive(), "x", "")).setCaption("Aktiv");
        Grid.Column<RegExpRule, String> name = grid.addColumn(RegExpRule::getName).setCaption("Name");
        Grid.Column<RegExpRule, Label> label = grid.addColumn(RegExpRule::getLabel).setCaption("Label");
        Grid.Column<RegExpRule, Double> weight = grid.addColumn(RegExpRule::getOrder).setCaption("Gewicht");
        Grid.Column<RegExpRule, String> regEx = grid.addColumn(r -> StringUtils.abbreviate(StringUtils.defaultString(r.getRegExp()), 50)).setCaption("RegEx");
        name.setExpandRatio(1);
        label.setExpandRatio(1);
        regEx.setExpandRatio(1);

        grid.setCaption("RegEx Regeln");
        grid.sort(label);

        grid.setSizeFull();
        editor.setWidth(100, Unit.PERCENTAGE);

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
