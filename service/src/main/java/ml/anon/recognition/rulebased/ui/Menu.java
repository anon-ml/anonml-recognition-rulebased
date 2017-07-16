package ml.anon.recognition.rulebased.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by mirco on 14.06.17.
 */

public class Menu extends CustomComponent {


    private HorizontalLayout menu = new HorizontalLayout();


    public Menu() {

        Button all = new Button("Übersicht", FontAwesome.HOME);
        all.addClickListener(e -> getUI().getNavigator().navigateTo(""));

        Button edit = new Button("RegEx", FontAwesome.ROCKET);
        edit.addClickListener(e -> getUI().getNavigator().navigateTo("REGEX"));
        menu.addComponents(all, edit);
        menu.setSizeUndefined();
        menu.setSpacing(true);
        setSizeUndefined();
        setCompositionRoot(menu);


    }


}
