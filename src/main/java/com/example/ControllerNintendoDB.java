package com.example;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ControllerNintendoDB {
    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ListView<NintendoItem> itemList;

    @FXML
    private ImageView detailImage;

    @FXML
    private Label detailName;

    @FXML
    private GridPane detailInfo;

    private Game[] games;
    private Character[] characters;
    private Console[] consoles;

    @FXML
    public void initialize() {

        // Leer los json y meter los objetos que salen de los json 
        // en listas de objetos, con la informacion extraida
        JsonReader reader = new JsonReader();
        try{
            games = reader.leerGames();
            characters = reader.leerCharacters();
            consoles = reader.leerConsoles();
        } catch (Exception e) { 
            e.printStackTrace();
        }


        // Configurar el comboBox
        categoryComboBox.getItems().addAll( "Jocs", "Personatges", "Consoles"); 
        categoryComboBox.setValue("Jocs");
        categoryComboBox.valueProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue == null){
                    return;
                }

                itemList.getItems().clear();

                switch (newValue) {
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
            }
        );


        // Configurar el itemList para mostrar los NintendoItem que corresponden
        itemList.setCellFactory(listView -> {
        
            ListCell<NintendoItem> cell = new ListCell<>() {
            
                private final ImageView imageView = new ImageView();
                private final Label label = new Label();
                private final HBox box = new HBox(10);
            
                {
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);
                
                    label.setTextOverrun(OverrunStyle.ELLIPSIS);
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
            cell.prefWidthProperty().bind(
                listView.widthProperty().subtract(20) 
            );
        
            return cell;
        });

        // Configurar el itemList para que al seleccionar salga a la derecha la imagen, titulo, etc...
        itemList.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            
                if (newValue == null) {
                    return;
                }
            
                detailName.setText(newValue.getName());
            
                Image image = new Image(
                    getClass().getResourceAsStream(
                        "/assets/images/" + newValue.getImage()
                    )
                );
            
                detailImage.setImage(image);

                // Limpiar informacion del grid anterior
                detailInfo.getChildren().clear();

                // Poner informacion segun el tipo de item en el grid
                if (newValue instanceof Game) { 
                    Game game = (Game) newValue; 
                    addInfoRow( "Any", String.valueOf(game.getYear()), 0,false); 
                    addInfoRow( "Tipus", game.getType(), 1,false); 
                    
                    // Configurar el argument porque ocupa mucho
                    Label plotTitle = new Label("Argument:"); 
                    detailInfo.add(plotTitle, 0, 2);
                    Label plotLabel = new Label(game.getPlot());
                    plotLabel.setWrapText(true); 
                    plotLabel.setMaxWidth(Double.MAX_VALUE);
                    GridPane.setColumnSpan(plotLabel, 2);
                    detailInfo.add(plotLabel, 0, 3);

                } else if (newValue instanceof Character) { 
                    Character character = (Character) newValue;
                    addInfoRow( "Color", character.getColor(), 0, false); 
                    addInfoRow( "Joc", character.getGame(), 1, false); 

                } else if (newValue instanceof Console) {
                    Console console = (Console) newValue; 
                    addInfoRow( "Data", console.getDate(), 0, false); 
                    addInfoRow( "Processador", console.getProcessor(), 1, false); 
                    addInfoRow( "Color", console.getColor(), 2, false); 
                    addInfoRow( "Unitats venudes", String.valueOf(console.getUnitsSold()), 3 ,false); 
                } 
            }
        );

        // Configurar para que al iniciar se vean bien los juegos y se seleccione el primero de la lista
        for (Game game : games) {
            itemList.getItems().add(game);
        }
        itemList.getSelectionModel().selectFirst();
    }

    // Metodo para añadir informacion en el grid
    private void addInfoRow(String title, String value, int row, boolean wrapText) {

        Label titleLabel = new Label(title + ":");
        Label valueLabel = new Label(value);

        valueLabel.setWrapText(wrapText);

        detailInfo.add(titleLabel, 0, row);
        detailInfo.add(valueLabel, 1, row);
    }

}
