package MG;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Scanner;

public class MainU extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage stage;
    public int n;//迷宫的长
    public int m;//迷宫的高

    @Override
    public void start(Stage primaryStage) {
        MainU.stage = primaryStage;//让舞台复制静态属性
        try {
            Pane v = new Pane();
            Controller c = new Controller();
            Scanner scanner = new Scanner(System.in);


            /*创建一个Gridpane并设置其属性*/
            MainU.stage = new Stage();
            GridPane pane = new GridPane();
            pane.setAlignment(Pos.BASELINE_CENTER);//对齐
            pane.setPadding(new Insets(190, 260, 190, 260));//设置上右下左的pixels,top,bottom是高度
            pane.setHgap(100);
            pane.setVgap(100);
            Label labeln = new Label("迷宫长度:");
            labeln.setFont(new Font("Arial", 20));
            pane.add(labeln, 0, 0);
            TextField myTextFieldn = new TextField();
            pane.add(myTextFieldn, 1, 0);


            Label labelm = new Label("迷宫宽度:");
            labelm.setFont(new Font("Arial", 20));
            pane.add(labelm, 0, 1);
            GridPane.setHalignment(labelm, HPos.RIGHT);//控件在单元格的对齐方式
            TextField myTextFieldm = new TextField();
            pane.add(myTextFieldm, 1, 1);


            Button map = new Button("保存");
            map.setPrefWidth(100);
            map.setPrefHeight(50);
            pane.add(map, 1, 3);
            GridPane.setHalignment(map, HPos.RIGHT);
            map.setOnAction(event -> {
                String inputn = myTextFieldn.getText();
                n = Integer.parseInt(inputn);
                String inputm = myTextFieldm.getText();
                m = Integer.parseInt(inputm);
            });

            Button view = new Button("显示");
            view.setPrefWidth(100);
            view.setPrefHeight(50);
            pane.add(view, 1, 4);
            GridPane.setHalignment(view, HPos.RIGHT);
            view.setOnAction(e -> print_map());

            ImagePattern imagePattern = new ImagePattern(new Image("MG/Image/yellow.png"), 0, 0, 1, 1, true);
            pane.setBackground(//背景
                    new Background(
                            new BackgroundFill(imagePattern,
                                    new CornerRadii(30),
                                    new Insets(0)
                            )
                    )
            );

            Scene scene = new Scene(pane);


            stage.setTitle("迷宫");
            stage.getIcons().add(new Image("MG/Image/t2.jpg"));//窗口图标
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("无解");
        }
    }

    public void print_map() {
        Controller c = new Controller();
        Button exit = new Button("退出");
        exit.setOnAction(e -> exit());
        Pane pane = new Pane();
        exit.setPrefWidth(910);
        exit.setPrefHeight(40);
        exit.setLayoutX(0);
        exit.setLayoutY(905);
        int[][] arr = new int[n][m];
        arr = c.CreatMap(n, m);
        c.Determine(arr, 0, 0);
        c.BFS(arr, arr.length, arr[0].length, arr.length - 1, arr[0].length - 1);
        c.check01(arr, arr.length, arr[0].length);
        double o = 900;
        double h = o / arr.length;
        double w = o / arr[0].length;
        stage.setResizable(false);
        Image[] image = new Image[]{
                new Image("MG/Image/top.png"),//上
                new Image("MG/Image/right.png"),//右
                new Image("MG/Image/down.png"),//下
                new Image("MG/Image/left.png"),//左
                new Image("MG/Image/t2.jpg"),
                new Image("MG/Image/obstacle.png"),//障碍
                new Image("MG/Image/road.png"),//可行
                new Image("MG/Image/black.png")//死路

        };
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 4) {
                    double k = c.check(arr, i, j);
                    if (k % 3 == 0 && (arr[i + 1][j] == 4 || arr[i + 1][j] == 2)) {
                        ImageView imageView = new ImageView(image[2]);//下
                        imageView.setFitHeight(h);
                        imageView.setFitWidth(w);
                        imageView.setX(w * j);
                        imageView.setY(h * i);
                        pane.getChildren().add(imageView);
                        arr[i][j] = 5;
                    } else if (k % 7 == 0 && (arr[i - 1][j] == 4 || arr[i - 1][j] == 2)) {
                        ImageView imageView = new ImageView(image[0]);//上
                        imageView.setFitHeight(h);
                        imageView.setFitWidth(w);
                        imageView.setX(w * j);
                        imageView.setY(h * i);
                        pane.getChildren().add(imageView);
                        arr[i][j] = 5;
                    } else if (k % 5 == 0 && (arr[i][j + 1] == 4 || arr[i][j + 1] == 2)) {
                        ImageView imageView = new ImageView(image[1]);//右
                        imageView.setFitHeight(h);
                        imageView.setFitWidth(w);
                        imageView.setX(w * j);
                        imageView.setY(h * i);
                        pane.getChildren().add(imageView);
                        arr[i][j] = 5;
                    } else if (k % 11 == 0 && (arr[i][j - 1] == 4 || arr[i][j - 1] == 2)) {
                        ImageView imageView = new ImageView(image[3]);//左
                        imageView.setFitHeight(h);
                        imageView.setFitWidth(w);
                        imageView.setX(w * j);
                        imageView.setY(h * i);
                        pane.getChildren().add(imageView);
                        arr[i][j] = 5;
                    }
                } else if (arr[i][j] == 3) {
                    ImageView imageView = new ImageView(image[6]);//可行路
                    imageView.setFitHeight(h);
                    imageView.setFitWidth(w);
                    imageView.setX(w * j);
                    imageView.setY(h * i);
                    pane.getChildren().add(imageView);
                } else if (arr[i][j] == 1) {
                    ImageView imageView = new ImageView(image[5]);//障碍
                    imageView.setFitHeight(h);
                    imageView.setFitWidth(w);
                    imageView.setX(w * j);
                    imageView.setY(h * i);
                    pane.getChildren().add(imageView);
                } else if (arr[i][j] == 2) {
                    ImageView imageView = new ImageView(image[4]);//终点
                    imageView.setFitHeight(h);
                    imageView.setFitWidth(w);
                    imageView.setX(w * j);
                    imageView.setY(h * i);
                    pane.getChildren().add(imageView);
                } else if (arr[i][j] == 0) {
                    ImageView imageView = new ImageView(image[7]);//死路
                    imageView.setFitHeight(h);
                    imageView.setFitWidth(w);
                    imageView.setX(w * j);
                    imageView.setY(h * i);
                    pane.getChildren().add(imageView);
                }
            }

        }
        pane.getChildren().add(exit);
        //设置新场景
        Scene scene = new Scene(pane,910,950);
        stage.setScene(scene);
        stage.show();
    }

    public void err() {//地图无解
        stage.setResizable(false);
        VBox v = new VBox();
        Label label = new Label("无解界面");
        label.setFont(new Font("Arial", 30));
        label.setTranslateX(400);
        label.setTranslateY(0);

        v.getChildren().add(label);
        Scene scene = new Scene(v,910,950);
        Pane root = new Pane();
        ImagePattern imagePattern = new ImagePattern(new Image("MG/Image/t2.jpg"), 0, 0, 1, 1, true);
        v.setBackground(//背景
                new Background(
                        new BackgroundFill(imagePattern,
                                new CornerRadii(30),
                                new Insets(0)
                        )
                )
        );
        Button exit = new Button("退出！");
        exit.setTranslateX(710);
        exit.setTranslateY(830);
        exit.setPrefWidth(200);
        exit.setPrefHeight(100);
        v.getChildren().add(exit);
        exit.setOnAction(e -> exit());
        //设置新场景
        stage.setScene(scene);
    }

    public void exit() {
        stage.close();//关闭窗口
    }
}
