package application;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import java.io.File;
import java.util.ArrayList;

public class FileSearch {

    public static void search(String searchText, File searchFile, TableView<File> resultTable) {
        ArrayList<File> searchList = new ArrayList<>();
        createSearchList(searchFile, searchList);

        if (searchList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không có file hoặc thư mục nào để tìm kiếm.");
            alert.showAndWait();
            return;
        }

        if (searchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vui lòng nhập từ khóa tìm kiếm.");
            alert.showAndWait();
            return;
        }

        boolean found = false;
        for (File file : searchList) {
            if (file.getName().equalsIgnoreCase(searchText)) {
                resultTable.getItems().add(file);
                found = true;
            }
        }

        if (!found) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy kết quả tìm kiếm cho từ khóa \"" + searchText + "\".");
            alert.showAndWait();
        }
    }

    private static void createSearchList(File file, ArrayList<File> searchList) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    searchList.add(childFile);
                    createSearchList(childFile, searchList);
                }
            }
        } else {
            searchList.add(file);
        }
    }
}
