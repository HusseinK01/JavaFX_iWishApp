/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import DTOs.RegisterDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author husse
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField txtFn;
    @FXML
    private TextField txtUn;
    @FXML
    private TextField txtEm;
    @FXML
    private TextField txtPh;
    @FXML
    private TextField txtCc;
    @FXML
    private Button BtnRe;
    @FXML
    private Hyperlink HyperLogin;
    @FXML
    private PasswordField txtPa;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    PrintStream ps;
    Socket s;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ps = SocketHolder.getInstance().getPrintStream();
        BtnRe.setCursor(Cursor.HAND);
        

        
        BtnRe.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txtUn.getText().isEmpty()||txtFn.getText().isEmpty()|| txtCc.getText().isEmpty()|| txtPa.getText().isEmpty()|| txtEm.getText().isEmpty()|| txtPh.getText().isEmpty()){
                     Alert alert = new Alert(AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setGraphic(null);
                    Label label = new Label("Please fill out all fields.");
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill:  #f7bc50;");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setStyle("-fx-background-color: #35415A;");
                    Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
                    okButton.setCursor(javafx.scene.Cursor.HAND);
                    okButton.setStyle("-fx-background-color: #f7bc50; -fx-text-fill: #35415A; -fx-font-weight: bold;");
                    dialogPane.setContent(label);
                    alert.show();
                  }
                else {
                RegisterDTO register = new RegisterDTO("RegisterRequest", txtUn.getText(),
                        txtFn.getText(), txtPh.getText(), txtEm.getText(), txtCc.getText(),
                        5000, txtPa.getText());
                Gson gson = new Gson();
                String request = gson.toJson(register, RegisterDTO.class);       
                ps.println(request);
                
                //switch to login view
                try {
                    root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                }  
                
                
            }
        });
        
        HyperLogin.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
}
