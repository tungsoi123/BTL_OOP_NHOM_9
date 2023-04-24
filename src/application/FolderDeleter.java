package application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

public class FolderDeleter {

    public static void deleteFolder(TreeView<String> treeView) {
        // Lấy mục đã chọn trong treeView
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();

        // Kiểm tra nếu một mục được chọn và nó không phải là thư mục gốc
        if (selectedItem != null && selectedItem.getParent() != null) {
            // Nhận đường dẫn tệp tương ứng với mục đã chọn
            TreeItem<String> parentItem = selectedItem.getParent();
            String folderName = selectedItem.getValue();
            Path folderPath = Paths.get(parentItem.getValue(), folderName);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText("Bạn có chắc muốn xóa thư mục này?");
            alert.setContentText("Xóa thư mục này.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Xóa thư mục trong hệ thống tập tin
                try {
                    Files.walk(folderPath)
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                } catch (IOException e) {
                    // Xử lý ngoại lệ
                    e.printStackTrace();
                    Alert loi = new Alert(Alert.AlertType.ERROR);
                    loi.setTitle("Lỗi");
                    loi.setHeaderText("Không thể xóa thư mục");
                    loi.setContentText("Không thể xóa thư mục này. Vui lòng kiểm tra và thử lại.");
                    loi.showAndWait();
                    return;
                }
                // Xóa phần tử được chọn khỏi TreeView
                parentItem.getChildren().remove(selectedItem);
            }
        }
    }
}
