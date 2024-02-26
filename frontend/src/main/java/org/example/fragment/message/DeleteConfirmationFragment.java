package org.example.fragment.message;

import io.grpc.StatusRuntimeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.example.DeleteEmployerResponse;
import org.example.Employer;
import org.example.EmployerNoSalaries;
import org.example.Salary;
import org.example.config.BasicConfig;
import org.example.service.employerService.EmployerClientService;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;

@Fragment(
        id = BasicConfig.DELETE_MESSAGE_FRAGMENT,
        viewLocation = "/fxml/fragment/delete-confirmation-view.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        scope = Scope.PROTOTYPE
)
public class DeleteConfirmationFragment extends ManagedFragment {
    @FXML
    private Label returnButton;

    @FXML
    private Label continueButton;

    @Resource
    private Context context;

    @Autowired
    private EmployerClientService employerClientService;

    public void init(EmployerNoSalaries employerNoSalaries) {
        returnButton.setOnMouseClicked(event -> returnButtonClicked(event, employerNoSalaries));
        continueButton.setOnMouseClicked(event -> returnButtonClicked(event, employerNoSalaries));
        if (employerNoSalaries != null) {
            try {
                Employer employer = employerClientService.getEmployerById(employerNoSalaries.getId());
                ObservableList<Salary> salaries = FXCollections.observableArrayList(employer.getSalariesList());
            } catch (StatusRuntimeException e) {

            }
        }
    }

    private void returnButtonClicked(MouseEvent event, EmployerNoSalaries employerNoSalaries) {
        context.send(BasicConfig.PERSPECTIVE_MAIN, "offDeleteConfirmation");
    }

    private void continueButtonClicked(MouseEvent event, EmployerNoSalaries employerNoSalaries) {
        DeleteEmployerResponse employerResponse = employerClientService.deleteEmployerById(employerNoSalaries.getId());
        if (employerResponse.getSuccess()) {
            context.send(BasicConfig.PERSPECTIVE_MAIN, "offDeleteConfirmation");
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), "delete");
        }
    }

}
