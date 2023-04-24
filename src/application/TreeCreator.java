package application;

import javafx.scene.control.TreeItem;
import java.io.File;

public class TreeCreator {
    public static void createTree(File file, TreeItem<String> treeItem) {
        // Lấy danh sách các tệp tin và thư mục con của thư mục được đưa vào.
        File[] files = file.listFiles();
        
        // Nếu thư mục có các tệp tin và thư mục con, thêm chúng vào cây.
        if (files != null) {
            for (File childFile : files) {
                // Tạo một TreeItem mới cho tệp tin hoặc thư mục con.
                TreeItem<String> childItem = new TreeItem<>(childFile.getName());
                
                // Thêm TreeItem mới vào TreeItem cha, tạo cây thư mục.
                treeItem.getChildren().add(childItem);
                
                // Đệ quy để tạo cây con cho thư mục con.
                createTree(childFile, childItem);
            }
        }
    }
}
