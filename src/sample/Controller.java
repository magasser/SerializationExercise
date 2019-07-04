package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable, PropertyChangeListener {

    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private NumberField ageField;
    @FXML private Label firstnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label ageLabel;
    @FXML private Button saveButton;
    @FXML private Button loadButton;
    @FXML private Button languageButton;

    private Person person;

    private ResourceBundle resourceBundle;

    private void setLanguageTexts() {
        this.firstnameLabel.setText(this.resourceBundle.getString("firstname"));
        this.lastnameLabel.setText(this.resourceBundle.getString("lastname"));
        this.ageLabel.setText(this.resourceBundle.getString("age"));
        this.saveButton.setText(this.resourceBundle.getString("save"));
        this.loadButton.setText(this.resourceBundle.getString("load"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.person = new Person();
        setLanguageTexts();
        this.languageButton.setText("EN");

        this.firstnameField.textProperty().addListener((observable, oldValue, newValue) -> this.person.setFirstname(newValue));
        this.firstnameField.textProperty().addListener((observable, oldValue, newValue) -> this.person.setLastname(newValue));
        this.firstnameField.textProperty().addListener((observable, oldValue, newValue) -> this.person.setAge(Integer.valueOf(newValue)));
    }

    @FXML
    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Person.data"))){
            oos.writeObject(this.person);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Person.data"))) {
            Person person = (Person) ois.readObject();

            this.person.setFirstname(person.getFirstname());
            this.person.setLastname(person.getLastname());
            this.person.setAge(person.getAge());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchLanguage() {
        if (this.languageButton.getText().equals("EN")){
            this.languageButton.setText("DE");
            this.resourceBundle = ResourceBundle.getBundle("sample.language", new Locale(""));
            setLanguageTexts();

        }else{
            this.languageButton.setText("EN");
            this.resourceBundle = ResourceBundle.getBundle("sample.language", new Locale("en", "EN"));
            setLanguageTexts();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case Person.FIRSTNAME_CHANGED:
                this.firstnameField.setText(evt.getNewValue().toString());
                break;
            case Person.LASTNAME_CHANGED:
                this.lastnameField.setText(evt.getNewValue().toString());
                break;
            case Person.AGE_CHANGED:
                this.ageField.setText(evt.getNewValue().toString());
                break;
        }
    }
}
