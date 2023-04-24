package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
 
public class Main extends Application {
	 // Khởi tạo các thành phần của giao diện
    private TreeView<String> treeView;
    private TextField searchField;
    private Button searchButton;
    private TableView<File> resultTable;

	FolderDeleter folderDeleter = new FolderDeleter();
    FolderRenamer folderRenamer = new FolderRenamer();
    CreateFolder createFolder = new CreateFolder();
    TreeCreator treeCreator = new TreeCreator();
    FileSearch searchFiles = new  FileSearch();

    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo cây thư mục và file hiện tại
        File[] roots = File.listRoots();
        TreeItem<String> rootItem = new TreeItem<>("My Computer");
        rootItem.setExpanded(true);
        for (File root : roots) {
            TreeItem<String> rootItemChild = new TreeItem<>(root.getAbsolutePath());
            rootItemChild.setExpanded(true);
            rootItem.getChildren().add(rootItemChild);
            TreeCreator.createTree(root, rootItemChild);
        }
        treeView = new TreeView<>(rootItem);

        // Khởi tạo ô tìm kiếm và nút tìm kiếm
        searchField = new TextField();
        searchButton = new Button("Search");
        searchButton.setOnAction(event -> FileSearch.search(searchField.getText(), new File(treeView.getSelectionModel().getSelectedItem().getValue()), resultTable));

        // Thêm nút "New Folder"
        Button newFolderButton = new Button("New Folder");
        newFolderButton.setOnAction(event -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            CreateFolder.create(selectedItem);
        });

        //Thêm nút "Xóa Folder"
        Button deleteButton = new Button("Delete Folder");
        deleteButton.setOnAction(event -> {
            FolderDeleter folderDeleter = new FolderDeleter();
            folderDeleter.deleteFolder(treeView);
        });

        //Thêm nút "Đổi tên Folder"
        Button renameButton = new Button("Rename Folder");
        renameButton.setOnAction(event -> {
            FolderRenamer folderRenamer = new FolderRenamer();
            folderRenamer.renameFolder(treeView);
        });

        // Khởi tạo bảng kết quả
        resultTable = new TableView<>();
        TableColumn<File, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

        TableColumn<File, String> pathColumn = new TableColumn<>("Path");
        pathColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAbsolutePath()));

        resultTable.getColumns().addAll(nameColumn, pathColumn);
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(newFolderButton, deleteButton,renameButton);

        // Tạo layout cho giao diện
        BorderPane root = new BorderPane();
        root.setTop(searchField);
      
        root.setLeft(treeView);
        root.setRight(searchButton);
        root.setCenter(resultTable);
        root.setBottom(hbox);

        // Hiển thị cửa sổ
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

		public static void main(String[] args) {
            launch(args);
        }
}