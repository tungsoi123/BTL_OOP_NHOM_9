package application;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.util.Optional;

public class CreateFolder {

    public static boolean create(TreeItem<String> selectedItem) {
        if (selectedItem != null) {
            TextInputDialog dialog = new TextInputDialog("Thư mục mới");
            dialog.setTitle("Tạo thư mục mới");
            dialog.setHeaderText("Nhập tên thư mục mới");
            dialog.setContentText("Tên:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String newFolderName = result.get();
                TreeItem<String> newFolderItem = new TreeItem<>(newFolderName);
                selectedItem.getChildren().add(newFolderItem);
                selectedItem.setExpanded(true);
                
                // tạo thư mục mới
                File selectedFolder = new File(selectedItem.getValue());
                File newFolder = new File(selectedFolder, newFolderName);
                if (newFolder.mkdirs()) {
                    return true;
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Không thể tạo thư mục mới");
                    alert.setContentText("Không thể tạo thư mục mới. Vui lòng kiểm tra và thử lại.");
                    alert.showAndWait();
                    selectedItem.getChildren().remove(newFolderItem);
                }
            }
        }
        return false;
    }
}
