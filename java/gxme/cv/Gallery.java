package gxme.cv;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.opencv.core.Mat;
import origami.Origami;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_REDUCED_COLOR_8;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Gallery extends Application {

    private ListView<String> tagListView;
    private BorderPane borderPane;
    private ListView<TaggedRectangle> listRectsX;
    private File workDirectoy;
    private TextArea area;

    static Image matToImage(Mat m) {
        return SwingFXUtils.toFXImage(Origami.matToBufferedImage(m), null);
    }

    static Image fileToImage(File f) {
        return matToImage(imread(f.getAbsolutePath()));
    }

    static Image fileToThumbnail(File f) {
        return matToImage(imread(f.getAbsolutePath(), IMREAD_REDUCED_COLOR_8));
    }

    HashMap<File, ArrayList<TaggedRectangle>> store = new HashMap<>();
    ObservableList<String> items;
    List<File> images = new ArrayList<>();
    Pane pane;

    ArrayList<TaggedRectangle> listOfRects = new ArrayList<>();

    TaggedRectangle rect;
    double startX;
    double startY;


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setWidth(1600);
        primaryStage.setHeight(800);
        primaryStage.setTitle("Yololi");

        loadImages();
        ImageView mainImageView = new ImageView();
        mainImageView.setImage(fileToImage(images.get(0)));
        mainImageView.setFitWidth(primaryStage.getWidth() - primaryStage.getWidth() / 4);
        mainImageView.setFitHeight(primaryStage.getHeight() - primaryStage.getHeight() / 4);
        mainImageView.setPreserveRatio(true);
        mainImageView.setCursor(Cursor.CROSSHAIR);
        pane = new Pane(mainImageView);

        mainImageView.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            rect = new TaggedRectangle(startX, startY, 0, 0);
            rect.setStroke(Color.GREEN);
            rect.setStrokeWidth(5);
            rect.setOpacity(0.2);

            String selected = tagListView.getSelectionModel().getSelectedItem();
            if (selected == null) selected = tagListView.getItems().get(0);
            rect.tag = selected;

            pane.getChildren().add(rect);
        });


        mainImageView.setOnMouseDragged(e -> {
            if (rect != null) {
                double x = e.getX();
                double y = e.getY();

                double rx = Math.min(x, startX);
                double ry = Math.min(y, startY);
                double rx2 = Math.max(x, startX);
                double ry2 = Math.max(y, startY);

                rect.setX(rx);
                rect.setY(ry);
                rect.setHeight(ry2 - ry);
                rect.setWidth(rx2 - rx);
            }
        });


        mainImageView.setOnMouseReleased(e -> {
            listOfRects.add(rect);
            // FIXME: why do we have to set the items again here?
            listRectsX.setItems(FXCollections.observableArrayList(listOfRects));

            save();
            rect = null;
        });

        HBox imagesStore = new HBox(4);

        asyncLoadThumbnails(primaryStage, mainImageView, imagesStore);

        ScrollPane scrollPane = new ScrollPane(imagesStore);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMaxWidth(primaryStage.getWidth());
        borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setTop(scrollPane);

        VBox buttons = new VBox();
        Button save = new Button();
        save.setText("Save");
        save.setOnMouseClicked(e -> {
            save();
        });
        Button load = new Button();
        load.setText("Load");
        load.setOnMouseClicked(e -> {
            load();
        });

        Button Debug = new Button();
        Debug.setText("Debug");
        Debug.setOnMouseClicked(e -> {
            log(listOfRects);
            log(store);
            log(Arrays.asList(items.toArray(new String[items.size()])));
        });

        Button clearOne = new Button();
        clearOne.setText("Clear");
        clearOne.setOnMouseClicked(ev -> {
            Platform.runLater(() -> {
                listOfRects.clear();
                // FIXME: why do we have to set the items again here?
                listRectsX.setItems(FXCollections.observableArrayList(listOfRects));
                pane.getChildren().removeIf(e -> e.getClass().equals(TaggedRectangle.class));
            });

        });

        items = FXCollections.observableArrayList("tag1", "tag2");
        tagListView = new ListView<>(items);
        tagListView.setCellFactory(TextFieldListCell.forListView());
        tagListView.setEditable(true);
        tagListView.setOnMouseClicked(e -> {
            // log(e.getSource());
        });
        tagListView.setOnKeyPressed(keyEvent -> {
            final String selectedItem = tagListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                items.remove(selectedItem);
            }
        });

        Button addTag = new Button();
        addTag.setText("Add Tag");
        addTag.setOnAction((ActionEvent event) -> {
            items.add(addTag.getText());
        });

        area = new TextArea();

        Button yolo = new Button();
        yolo.setText("YOLO");
        yolo.setPrefWidth(200.0);
        yolo.setOnAction((ActionEvent event) -> {
            try {
                yolo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        /**
         * RECTS
         */
        listRectsX = new ListView<>();
        listRectsX.setTooltip(new Tooltip("" + System.currentTimeMillis()));
        listRectsX.setEditable(true);
        listRectsX.setCellFactory(
                TextFieldListCell.forListView(
                        new StringConverter<TaggedRectangle>() {
                            public String toString(TaggedRectangle object) {
                                return object.toString();
                            }

                            public TaggedRectangle fromString(String string) {
                                return null;
                            }
                        })
        );
        listRectsX.setOnMouseClicked(e -> {
            // log(e.getSource());
        });

        HBox hbuttons = new HBox();
        hbuttons.getChildren().addAll(Debug, save, load, clearOne);
        buttons.getChildren().addAll(hbuttons, tagListView, addTag, listRectsX, area, yolo);
        buttons.setMaxWidth(500.0);
        borderPane.setRight(buttons);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void asyncLoadThumbnails(Stage primaryStage, ImageView mainImageView, HBox imagesStore) {
        new Thread(() -> {
            imagesStore.getChildren().clear();
            for (File image : images) {
                ImageView thumbnailImageView = new ImageView();
                thumbnailImageView.setOnMouseClicked(event -> {
                            mainImageView.setImage(thumbnailImageView.getImage());
                            mainImageView.setPreserveRatio(true);
                            loadRectanglesForImage(image);
                        }
                );
                thumbnailImageView.setImage(fileToThumbnail(image));
                thumbnailImageView.setFitWidth(primaryStage.getWidth() / 4);
                thumbnailImageView.setFitHeight(primaryStage.getHeight() / 4 - 50);
                thumbnailImageView.setPreserveRatio(true);
                store.put(image, new ArrayList<>());
                Platform.runLater(() -> {
                    imagesStore.getChildren().add(thumbnailImageView);
                });
            }
            load();
        }).start();
    }

    private void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private void yolo() throws IOException {
        String _yolo = workDirectoy.getAbsolutePath() + File.separator + "yolo";
        File yolo = new File(_yolo);
        yolo.mkdirs();
        deleteDirectoryStream(Path.of(_yolo));
        log("Yolo Dir Created");

        File img = new File(_yolo+File.separator+"img");
        img.mkdirs();
        log("Img Dir Created");

        for(File i : images) {
            ArrayList<TaggedRectangle> rects = store.get(i);
            if(rects.size()==0) continue;

            File j = new File(img.getPath()+File.separator+i.getName());
            Files.copy(i.toPath(), j.toPath());

            String txtj = j.getPath().substring(0,j.getPath().lastIndexOf("."))+".txt";
            FileWriter jtxt = new FileWriter(txtj);
            for(TaggedRectangle r : rects) {
                jtxt.write(r.toYolo()+"\n");
            }
            jtxt.close();
        }
        log("Image folder Completed");

        // train.txt
        FileWriter train_txt = new FileWriter(_yolo+File.separator+"train.txt");
        train_txt.close();
        log("generated train.txt");

        // valid.txt
        FileWriter valid_txt = new FileWriter(_yolo+File.separator+"test.txt");
        valid_txt.close();
        log("generated valid.txt");

        // obj.data
        FileWriter data = new FileWriter(_yolo + File.separator + "obj_data.txt");
        int classCount = tagListView.getItems().size();
        data.write("classes\t= " + classCount +"\n");
        data.write("train\t= train.txt"+"\n");
        data.write("valid\t= test.txt"+"\n");
        data.write("names\t= obj_names.txt"+"\n");
        data.write("backup\t= weights"+"\n");
        data.close();
        log("generated obj_data.txt");

        // obj.names
        FileWriter names = new FileWriter(_yolo + File.separator + "obj_names.txt");
        for (String str : tagListView.getItems()) {
            names.write(str +"\n");
        }
        names.close();
        log("generated obj_names.txt");

        // yolo.cfg
        Path path = Paths.get("yolo.cfg");
        Charset charset = StandardCharsets.UTF_8;
        String content = Files.readString(path, charset);
        content = content.replaceAll("classes=1", "classes="+classCount);
        Files.write(Paths.get(yolo+File.separator+"yolo.cfg"), content.getBytes(charset));
        log("generated yolo.cfg");

        // backup folder
        File weights = new File(_yolo+File.separator+"weights");
        weights.mkdir();
        log("generated weights folder");

        // script train.sh
        FileWriter train_sh = new FileWriter(_yolo + File.separator + "train.sh");
        train_sh.write("darknet detector train obj_data.txt yolo.cfg darknet53.conv.74\n");
        train_sh.close();
        log("generated train.sh");

        // open folder
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new File(_yolo));
        }
    }

    private void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(workDirectoy+"/store.bkp");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(store);
            List<String> tags = Arrays.asList(items.toArray(new String[items.size()]));
            objectOut.writeObject(tags);
            objectOut.close();
            log("Store saved: " + store.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void log(Object msg) {
        System.out.println(msg);
        area.appendText(msg.toString()+"\n");
    }

    private void load() {
        try {
            FileInputStream fileOut = new FileInputStream(workDirectoy+"/store.bkp");
            ObjectInputStream objectOut = new ObjectInputStream(fileOut);
            store = (HashMap<File, ArrayList<TaggedRectangle>>) objectOut.readObject();
            log("Store Loaded:" + store.size());

            List<String> tags = (List<String>) objectOut.readObject();
            Platform.runLater(() -> {
                items.clear();
                items.addAll(tags);
            });
            log("Tags Loaded:" + tags.size());

            objectOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRectanglesForImage(File image) {
        pane.getChildren().removeIf(e -> e.getClass().equals(TaggedRectangle.class));
        listOfRects = store.get(image);
        listRectsX.setItems(FXCollections.observableArrayList(listOfRects));
        for (TaggedRectangle r : listOfRects) {
            pane.getChildren().add(r);
        }

    }

    private void loadImages() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory");
        images.clear();
        workDirectoy = directoryChooser.showDialog(null);
        File[] files = workDirectoy.listFiles();
        String fileName;
        for (File file : files) {
            fileName = file.getName().toLowerCase();
            if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".bmp")) {
                images.add(file.getAbsoluteFile());
            }
        }
    }

    public static void main(String[] args) {
        Origami.init();
        launch(args);
    }
}