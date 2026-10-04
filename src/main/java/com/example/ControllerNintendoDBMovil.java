package com.example;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;


public class ControllerNintendoDBMovil {

    @FXML
    private ListView<String> categoryList;

    @FXML
    private ListView<NintendoItem> itemList;

    @FXML
    private Button backButton;

    @FXML
    private Label headerTitle;

    @FXML
    private VBox detailPane;

    @FXML
    private ImageView detailImage;

    @FXML
    private Label detailName;

    @FXML
    private GridPane detailInfo;

    private Game[] games;
    private Character[] characters;
    private Console[] consoles;

    private String currentCategory;



    @FXML
    public void initialize() {

        JsonReader reader = new JsonReader(); 
        try { 
            games = reader.leerGames();
            characters = reader.leerCharacters(); 
            consoles = reader.leerConsoles();
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        categoryList.getItems().addAll(
            "Jocs",
            "Personatges",
            "Consoles"
        );


        categoryList.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {

                if (newValue == null) {
                    return;
                }

                itemList.getItems().clear();

                switch (newValue){
                    case "Jocs":
                        for (Game game : games) {
                            itemList.getItems().add(game);
                        }
                        break;

                    case "Personatges":
                        for (Character character : characters) {
                            itemList.getItems().add(character);
                        }
                        break;

                    case "Consoles":
                        for (Console console : consoles) {
                            itemList.getItems().add(console);
                        }
                        break;
                }

                categoryList.setVisible(false);
                categoryList.setManaged(false);

                itemList.setVisible(true);
                itemList.setManaged(true);

                currentCategory = newValue;
                headerTitle.setText(newValue);
                backButton.setVisible(true);
            }
        );

        // Configurar el itemList para mostrar los NintendoItem que corresponden
        itemList.setCellFactory(listView -> {
        
            ListCell<NintendoItem> cell = new ListCell<>() {
            
                private final ImageView imageView = new ImageView();
                private final Label label = new Label();
                private final HBox box = new HBox(15);
            
                {
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(70);
                    imageView.setPreserveRatio(true);
                
                    label.setMaxWidth(Double.MAX_VALUE);
                
                    HBox.setHgrow(label, Priority.ALWAYS);
                
                    box.setAlignment(Pos.CENTER_LEFT);
                    box.getChildren().addAll(imageView, label);
                }
            
                @Override
                protected void updateItem(NintendoItem item, boolean empty) {
                
                    super.updateItem(item, empty);
                
                    if (empty || item == null) {
                    
                        setText(null);
                        setGraphic(null);
                    
                    } else {
                    
                        label.setText(item.getName());
                    
                        Image image = new Image(
                            getClass().getResourceAsStream(
                                "/assets/images/" + item.getImage()
                            )
                        );
                    
                        imageView.setImage(image);
                    
                        setText(null);
                        setGraphic(box);
                    }
                }
            };
            return cell;
        });

        itemList.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            
                if (newValue == null) {
                    return;
                }
            
                showDetail(newValue);
            }
        );

        backButton.setOnAction(event -> {
        
            // Si estamos viendo el detalle de un elemento
            if (detailPane.isVisible()) {
            
                detailPane.setVisible(false);
                detailPane.setManaged(false);
            
                itemList.setVisible(true);
                itemList.setManaged(true);
            
                // Volvemos a mostrar el nombre de la categoría
                headerTitle.setText(currentCategory);
            
                backButton.setVisible(true);
            
            }
            // Si estamos viendo la lista de elementos
            else if (itemList.isVisible()) {
            
                itemList.setVisible(false);
                itemList.setManaged(false);
            
                categoryList.setVisible(true);
                categoryList.setManaged(true);
            
                headerTitle.setText("Nintendo DB");
                backButton.setVisible(false);
            
                categoryList.getSelectionModel().clearSelection();
            }
        });

        headerTitle.setText("Nintendo DB");
        backButton.setVisible(false);

        itemList.setVisible(false);
        itemList.setManaged(false);

        detailPane.setVisible(false);
        detailPane.setManaged(false);
    }

    private void showDetail(NintendoItem item) {

        itemList.setVisible(false);
        itemList.setManaged(false);

        detailPane.setVisible(true);
        detailPane.setManaged(true);

        headerTitle.setText(item.getName());

        Image image = new Image(
            getClass().getResourceAsStream(
                "/assets/images/" + item.getImage()
            )
        );

        detailImage.setImage(image);
        detailName.setText(item.getName());

        detailInfo.getChildren().clear();

        if (item instanceof Game) {

            Game game = (Game) item;

            addInfoRow(
                "Any",
                String.valueOf(game.getYear()),
                0
            );

            addInfoRow(
                "Tipus",
                game.getType(),
                1
            );

            Label plotTitle = new Label("Argument:");
            detailInfo.add(plotTitle, 0, 2);

            Label plotLabel = new Label(game.getPlot());
            plotLabel.setWrapText(true);
            plotLabel.setMaxWidth(Double.MAX_VALUE);

            GridPane.setColumnSpan(plotLabel, 2);

            detailInfo.add(plotLabel, 0, 3);

        } else if (item instanceof Character) {
            Character character = (Character) item;

            addInfoRow(
                "Color",
                character.getColor(),
                0
            );
        
            addInfoRow(
                "Joc",
                character.getGame(),
                1
            );

        } else if (item instanceof Console) {
            Console console = (Console) item;

            addInfoRow(
                "Data",
                console.getDate(),
                0
            );
        
            addInfoRow(
                "Processador",
                console.getProcessor(),
                1
            );
        
            addInfoRow(
                "Color",
                console.getColor(),
                2
            );
        
            addInfoRow(
                "Unitats venudes",
                String.valueOf(console.getUnitsSold()),
                3
            );
        }

    }

    private void addInfoRow(
            String title,
            String value,
            int row) {

        Label titleLabel = new Label(title + ":");
        Label valueLabel = new Label(value);

        detailInfo.add(titleLabel, 0, row);
        detailInfo.add(valueLabel, 1, row);
    }
}


