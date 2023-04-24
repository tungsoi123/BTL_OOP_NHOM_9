package application;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FolderRenamer {

    public static void renameFolder(TreeView<String> treeView) {
        // Lấy mục đã chọn từ TreeView
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
    
        // Kiểm tra nếu một mục được chọn và nó không phải là thư mục gốc
        if (selectedItem != null && selectedItem.getParent() != null) {
            // Tạo một TextField để người dùng nhập tên mới cho thư mục được chọn
            TextField textField = new TextField(selectedItem.getValue());
    
            // Hiển thị TextField thay cho tên thư mục hiện tại
            selectedItem.setGraphic(textField);
    
            // Chọn toàn bộ nội dung của TextField
            textField.selectAll();
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    // Lấy tên mới của thư mục được nhập bởi người dùng
                    String newName = textField.getText().trim();
    
                    // Kiểm tra xem tên mới của thư mục có hợp lệ và duy nhất không
                    if (!newName.isEmpty() && isUniqueName(selectedItem.getParent(), newName)) {
                        // Lấy đường dẫn đến thư mục hiện tại và đường dẫn đến thư mục mới sau khi đổi tên
                        Path folderPath = Paths.get(selectedItem.getParent().getValue(), selectedItem.getValue());
                        Path newFolderPath = Paths.get(selectedItem.getParent().getValue(), newName);
    
                        // Đổi tên thư mục bằng cách di chuyển thư mục hiện tại đến thư mục mới
                        try {
                            Files.move(folderPath, newFolderPath);
                            // Cập nhật tên thư mục trong cây hiển thị
                            selectedItem.setValue(newName);
                        } catch (IOException e) {
                            // Nếu xảy ra lỗi khi đổi tên thư mục, hiển thị thông báo lỗi
                            showAlert("Lỗi", "Không thể đổi tên thư mục.");
                        }
                    } else {
                        // Nếu tên thư mục mới không hợp lệ hoặc đã được sử dụng, hiển thị thông báo lỗi
                        showAlert("Lỗi", "Tên thư mục không hợp lệ hoặc đã được sử dụng.");
                    }
    
                    // Đặt lại nội dung của TextField thành tên thư mục cũ
                    textField.setText(selectedItem.getValue());
    
                    // Ẩn TextField và hiển thị lại tên thư mục
                    selectedItem.setGraphic(null);
                }
            });
            textField.requestFocus();
        }
    }
    

    private static boolean isUniqueName(TreeItem<String> parent, String name) {
        return parent.getChildren().stream().noneMatch(child -> child.getValue().equalsIgnoreCase(name));
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
