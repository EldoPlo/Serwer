package com.journaldev.socket;

//import javafx.application.Platform;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TextField;

public class ConsoleHandler<T extends Comparable<T>> {
    private final Parser<T> parser;
    private final String commandTextField;
    private final String valueTextField;

    public ConsoleHandler(GenericParser parser, String commandTextField, String valueTextField) {
        this.parser = new Parser<>(parser);
        this.commandTextField = commandTextField;
        this.valueTextField = valueTextField;
    }
    public void handleInsert(Tree<T> binaryTree) {
        T value = parseValue();
        if (value == null) {
            displayAlert("Invalid value");
            return;
        }

        binaryTree.insert(value);
        displayAlert("Value inserted: " + value);
    }

    public void handleSearch(Tree<T> binaryTree) {
        T value = parseValue();
        if (value == null) {
            displayAlert("Invalid value");
            return;
        }

        boolean found = binaryTree.searchNode(value);
        displayAlert("Value found: " + found);
    }

    public void handleDelete(Tree<T> binaryTree) {
        T value = parseValue();
        if (value == null) {
            displayAlert("Invalid value");
            return;
        }

        binaryTree.delete(value);
        displayAlert("Value deleted: " + value);
    }

    public void handleDraw(Tree<T> binaryTree) {
        String s = binaryTree.IterativeinOrder();
        displayAlert("Tree drawn: " + s);
    }

    private T parseValue() {
        String valueStr = valueTextField; //.getText();
        try {
            return parser.parseValue(valueStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void displayAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Information");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
    }
}
