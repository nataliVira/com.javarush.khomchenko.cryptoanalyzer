package com.javarush;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.javarush.data.UserInfo;
import com.javarush.exception.ProcessException;
import com.javarush.service.FileService;
import com.javarush.service.UserInterfaceService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class HelloController /*implements Initializable*/ {

    private static final String ERROR_TRANSACTION = "Unable to retrieve transaction information. Due to: ";
    private static final String ERROR_PROCESSING = "The file is not being processed. Due to: ";

    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    TextField strPath;

    @FXML
    TextField strKey;

    @FXML
    TextArea textArea;

    @FXML
    private void initialize() {
        comboBox1.getItems().addAll("ENCRYPTION", "DECRYPTION","CRYPTANALYSIS");
        comboBox1.getSelectionModel().selectFirst();

        ObservableList<String> items = FXCollections.observableArrayList("UTF_8","WINDOWS_1251");
        comboBox2.setItems(items);
        comboBox2.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        UserInfo userInfo = null;
        try {
            userInfo = UserInterfaceService.getUserInfo(comboBox1.getValue(), comboBox2.getValue(), strPath.getText(), strKey.getText());
        } catch (Exception e) {
            textArea.setText(ERROR_TRANSACTION + e.getMessage());
            System.out.printf(ERROR_TRANSACTION);
            e.printStackTrace();
            return;
        }
        List<Path> result;
        try {
            result = FileService.process(userInfo);
        } catch (IOException | ProcessException e) {
            textArea.setText(ERROR_PROCESSING + e.getMessage());
            System.out.println(ERROR_PROCESSING);
            e.printStackTrace();
            return;
        }
        textArea.setText("Результирующий файл " + Arrays.toString(result.toArray()));
    }

}