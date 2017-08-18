package ml.anon.recognition.rulebased.ui.regex;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import ml.anon.anonymization.model.Label;
import ml.anon.recognition.rulebased.api.model.RuleImpl;
import ml.anon.recognition.rulebased.repository.RuleRepository;
import org.springframework.context.annotation.Scope;
import org.vaadin.ui.NumberField;

/**
 * Created by mirco on 14.06.17.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
@Slf4j
public class RuleEditor extends VerticalLayout {

  private final Grid<RuleImpl> grid;

  private RuleImpl rule = RuleImpl.builder().build();

  private TextField name = new TextField("Name");

  private TextArea regExp = new TextArea("RegEx");

  private NumberField order = new NumberField("Gewicht");

  private CheckBox active = new CheckBox(("Aktiv"));

  private ComboBox<ml.anon.anonymization.model.Label> label = new ComboBox<>("Label");

  private Button save = new Button("Speichern", FontAwesome.SAVE);

  private Button delete = new Button("Löschen", FontAwesome.REMOVE);

  private Binder<RuleImpl> binder = new Binder<>(RuleImpl.class);

  private CssLayout actions = new CssLayout(save, delete);

  private RuleRepository repo;

  public RuleEditor(RuleRepository repo, Grid<RuleImpl> grid) {
    this.grid = grid;
    HorizontalLayout top = new HorizontalLayout();
    HorizontalLayout buttons = new HorizontalLayout();
    buttons.addComponents(save, delete);
    top.addComponents(name, order, label, active);
    addComponents(top, regExp, buttons);
    regExp.setSizeFull();
    active.setSizeUndefined();
    name.setSizeFull();
    order.setSizeUndefined();
    label.setItems(Label.values());
    top.setSizeFull();

    top.setComponentAlignment(active, Alignment.MIDDLE_CENTER);
    binder.forField(active).bind("active");
    binder.forField(regExp).bind("regExp");
    binder.forField(name).bind("name");

    label.addValueChangeListener(l -> rule.setLabel(l.getValue()));
    binder.forField(order).withConverter(NumberField.getConverter("...")).bind("weight");
    binder.setBean(rule);
    setSpacing(true);
    actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    save.setStyleName(ValoTheme.BUTTON_PRIMARY);
    save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

    save.addClickListener(e -> {
      repo.save(rule);
      onChange(repo, grid);
    });
    delete.addClickListener(e -> {
      repo.delete(rule);
      onChange(repo, grid);
    });

    this.components = components;
    this.repo = repo;
  }

  private void onChange(RuleRepository repo, Grid<RuleImpl> grid) {
    grid.getDataProvider().refreshAll();
    rule = RuleImpl.builder().build();
    binder.setBean(rule);

  }

  public final void changeBoundRule(RuleImpl r) {
    rule = r;
    binder.setBean(rule);
    label.setSelectedItem(r.getLabel());
    save.focus();
    name.selectAll();
  }


}
