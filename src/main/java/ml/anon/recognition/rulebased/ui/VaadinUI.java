package ml.anon.recognition.rulebased.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import ml.anon.recognition.rulebased.api.model.RegExpRule;
import ml.anon.recognition.rulebased.api.model.RuleRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.vaadin.ui.NumberField;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by mirco on 14.06.17.
 */
@SpringUI(path = "/admin")
@Theme("valo")
@Slf4j
public class VaadinUI extends UI {

    @Resource
    private RuleRepository repo;

    @Resource
    private MongoOperations ops;


    private Grid<RegExpRule> grid = new Grid<>();


    @Override
    protected void init(VaadinRequest request) {
        RuleEditor editor = new RuleEditor(grid);
        VerticalLayout mainLayout = new VerticalLayout(grid, editor);
        setContent(mainLayout);

        addClickListener(e -> grid.deselectAll());

        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(70, Unit.PERCENTAGE);
        grid.asSingleSelect().addValueChangeListener(e -> {
            log.info(e.toString());
            editor.editRule((e.getValue()));
        });
        mainLayout.setSizeFull();

        grid.setItems(getEditableRules());
        grid.addColumn(RegExpRule::getName).setCaption("Name");
        Grid.Column<RegExpRule, ml.anon.model.anonymization.Label> label = grid.addColumn(RegExpRule::getLabel).setCaption("Label");
        Grid.Column<RegExpRule, Double> gewicht = grid.addColumn(RegExpRule::getOrder).setCaption("Gewicht");

        grid.addColumn(RegExpRule::getRegExp).setCaption("RegEx");

    }

    protected List<RegExpRule> getEditableRules() {
        Query q = new Query().restrict(RegExpRule.class);
        return ops.find(q, RegExpRule.class);
    }

    @org.springframework.stereotype.Component
    @Scope("prototype")
    public class RuleEditor extends VerticalLayout {

        private final Grid<RegExpRule> grid;

        private RegExpRule rule = new RegExpRule();

        private TextField name = new TextField("Name");

        private TextArea regExp = new TextArea("RegEx");

        private NumberField order = new NumberField("Gewicht");

        private CheckBox active = new CheckBox(("Aktiv"));

        private Button save = new Button("Speichern", FontAwesome.SAVE);

        private Button delete = new Button("Löschen", FontAwesome.REMOVE);

        private Binder<RegExpRule> binder = new Binder<>(RegExpRule.class);

        private CssLayout actions = new CssLayout(save, delete);

        public RuleEditor(Grid<RegExpRule> grid) {
            this.grid = grid;
            HorizontalLayout top = new HorizontalLayout();
            top.addComponents(name, order, active);
            addComponents(top, regExp, save, delete);
            regExp.setSizeFull();
            active.setSizeUndefined();
            name.setSizeFull();
            order.setSizeUndefined();
            top.setSizeFull();

            top.setComponentAlignment(active, Alignment.MIDDLE_CENTER);
            binder.forField(active).bind("active");
            binder.forField(regExp).bind("regExp");
            binder.forField(name).bind("name");
            binder.forField(order).withConverter(NumberField.getConverter("...")).bind("order");
            binder.setBean(rule);
            setSpacing(true);
            actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
            save.setStyleName(ValoTheme.BUTTON_PRIMARY);
            save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

            save.addClickListener(e -> {
                repo.save(rule);
                grid.deselectAll();
                grid.setItems(getEditableRules());
                rule = new RegExpRule();
                binder.setBean(rule);
            });
            delete.addClickListener(e -> {
                repo.delete(rule);
                grid.deselectAll();
                grid.setItems(getEditableRules());
                rule = new RegExpRule();
                binder.setBean(rule);
            });

        }

        public final void editRule(RegExpRule r) {
            if (r != null) {
                final boolean persited = r.getId() != null;
                if (persited) {
                    rule = (RegExpRule) repo.findOne(r.getId());
                } else {
                    rule = r;
                }
                binder.setBean(rule);
                save.focus();
                name.selectAll();
            }

        }


    }
}
