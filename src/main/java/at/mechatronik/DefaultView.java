package at.mechatronik;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

/**
 * Created by toriser on 20.01.17.
 */
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "";

    @PostConstruct
    void init() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        Label lbTitle = new Label("Mechatronik");
        lbTitle.addStyleName("h1");
        lbTitle.addStyleName("green");
        lbTitle.setSizeUndefined();
        addComponent(lbTitle);
        setComponentAlignment(lbTitle, Alignment.MIDDLE_RIGHT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}