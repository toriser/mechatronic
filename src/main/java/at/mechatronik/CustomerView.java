package at.mechatronik;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.filter.Like;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringView(name = CustomerView.VIEW_NAME)
public class CustomerView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "customers";

    @Autowired
    private CustomerService service;

    private Grid grid = new Grid();
    private ObjectProperty<String> property = new ObjectProperty<String>("");
    private TextField tfKunde1 = new TextField("Kunde", property);
    private TextField tfKunde2 = new TextField("Kunde Detail 1");
    private TextField tfKunde3 = new TextField("Kunde Detail 2");
    private TextField tfAddress1 = new TextField("Postfach");
    private TextField tfAddress2 = new TextField("Anschrift");
    private TextField tfAddress3 = new TextField("PLZ Ort");
    private TextField tfPhone = new TextField("Telefon");
    private TextField tfEmail = new TextField("Email");
    private TextField tfSearch = new TextField("Suche");
    private Button save = new Button("Speichern", e -> saveCustomer());
    private Button btnDelete = new Button("Löschen", e -> deleteCustomer());
    private PropertysetItem inputItems = new PropertysetItem();
    private FieldGroup inputBinder;
    private Customer selectedCustomer;

    @PostConstruct
    void init() {
        tfSearch.addTextChangeListener(new FieldEvents.TextChangeListener() {

            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                Container.Filterable filter = (Container.Filterable) (grid.getContainerDataSource());
                filter.removeAllContainerFilters();

                String filterString = event.getText();
                if (filterString.length() > 0) {
                    filter.addContainerFilter(new Like("name1", "%" + filterString + "%"));
                }

            }
        });

        updateGrid();
        grid.setColumns("name1", "name2", "name3", "address1", "address2", "address3", "phone", "email");
        grid.getColumn("name1").setHeaderCaption("Kunde");
        grid.getColumn("name2").setHeaderCaption("Kunde Detail");
        grid.getColumn("name3").setHeaderCaption("Kunde Detail");
        grid.getColumn("address1").setHeaderCaption("Postfach");
        grid.getColumn("address2").setHeaderCaption("Anschrift");
        grid.getColumn("address3").setHeaderCaption("PLZ Ort");
        grid.getColumn("phone").setHeaderCaption("Telefon");
        grid.getColumn("email").setHeaderCaption("Email");

        grid.addSelectionListener(e -> updateForm());

        tfSearch.setSizeUndefined();
        HorizontalLayout lKunde = new HorizontalLayout(tfKunde1, tfKunde2, tfKunde3);
        lKunde.setSpacing(true);
        HorizontalLayout lAddresse = new HorizontalLayout(tfAddress1, tfAddress2, tfAddress3);
        lAddresse.setSpacing(true);
        HorizontalLayout lPhoneEmail = new HorizontalLayout(tfPhone, tfEmail);
        lPhoneEmail.setSpacing(true);

        tfKunde1.setBuffered(true);
        tfKunde1.setRequired(true);
        tfKunde1.setRequiredError("Kundenfeld muß eingegeben werden!");
        tfKunde3.setNullRepresentation("");
        tfKunde1.setNullSettingAllowed(false);
        tfKunde1.setValidationVisible(false);
        tfKunde1.setImmediate(true);

        Label filler = new Label("<center><p style=\"background-color:rgb(55,55,55);font-size:50px;color:rgb(255,255,255)\">LABEL</p><center>", Label.CONTENT_RAW);
        filler.setSizeUndefined();
        save.setSizeUndefined();
        btnDelete.setSizeUndefined();
        filler.setWidth("100%");
        HorizontalLayout actionLayout = new HorizontalLayout(save, btnDelete);
        actionLayout.setSizeUndefined();
        actionLayout.setComponentAlignment(tfSearch, Alignment.BOTTOM_LEFT);
//        actionLayout.setComponentAlignment(filler, Alignment.BOTTOM_RIGHT);
        actionLayout.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
        actionLayout.setComponentAlignment(btnDelete, Alignment.BOTTOM_RIGHT);


        HorizontalLayout actionParent = new HorizontalLayout(tfSearch, actionLayout);

        VerticalLayout inputLayout = new VerticalLayout(lKunde, lAddresse, lPhoneEmail);
        inputLayout.addStyleName("bordered");

        Panel inputPanel = new Panel("Kunden Details", inputLayout);
        inputPanel.setSizeFull();
        inputPanel.addStyleName("bordered");

        lKunde.setWidth("100%");
        tfKunde1.setWidth("100%");
        tfKunde2.setWidth("100%");
        tfKunde3.setWidth("100%");
        lAddresse.setWidth("100%");
        lPhoneEmail.setWidth("100%");
        inputLayout.setWidth("100%");

        setSizeFull();
        VerticalLayout layout = new VerticalLayout(actionLayout/*, grid, inputPanel*/);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        addComponent(layout);
        grid.setSizeFull();
    }

    private void updateGrid() {
        grid.setSizeFull();
        List<Customer> customers = service.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
        setFormVisible(!grid.getSelectedRows().isEmpty());
    }

    private void updateForm() {
        if (grid.getSelectedRows().isEmpty()) {
            setFormVisible(false);
        } else {
            selectedCustomer = (Customer) grid.getSelectedRow();
            BeanFieldGroup.bindFieldsUnbuffered(selectedCustomer, this);
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
        if (visible && selectedCustomer != null) {
            tfKunde1.setValue(selectedCustomer.getName1());
            tfKunde2.setValue(selectedCustomer.getName2());
            tfKunde3.setValue(selectedCustomer.getName3());
            tfAddress1.setValue(selectedCustomer.getAddress1());
            tfAddress2.setValue(selectedCustomer.getAddress2());
            tfAddress3.setValue(selectedCustomer.getAddress3());
            tfPhone.setValue(selectedCustomer.getPhone());
            tfEmail.setValue(selectedCustomer.getEmail());
            tfKunde1.focus();
        } else {
            tfKunde1.setValue("");
            tfKunde2.setValue("");
            tfKunde3.setValue("");
            tfAddress1.setValue("");
            tfAddress2.setValue("");
            tfAddress3.setValue("");
            tfPhone.setValue("");
            tfEmail.setValue("");
            tfKunde1.focus();
        }
    }

    private void saveCustomer() {
        try {
            tfKunde1.commit();
            Customer aktCustomer = new Customer(selectedCustomer != null ? selectedCustomer.getId() : null,
                    tfKunde1.getValue(),
                    tfKunde2.getValue(),
                    tfKunde3.getValue(),
                    tfAddress1.getValue(),
                    tfAddress2.getValue(),
                    tfAddress3.getValue(),
                    tfPhone.getValue(),
                    tfEmail.getValue());
            service.update(aktCustomer);
            updateGrid();
            selectedCustomer = null;
        } catch (Validator.EmptyValueException e) {
            tfKunde1.setValidationVisible(true);
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        service.delete(selectedCustomer.getId());
        updateGrid();
        selectedCustomer = null;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}