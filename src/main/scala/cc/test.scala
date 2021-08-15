import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.event.ActionEvent

object test extends JFXApp {
    stage = new JFXApp.PrimaryStage{
        title = "Test GUI"
        scene = new Scene(400,300){
            val button = new Button("Click me!")
            button.layoutX = 100
            button.layoutY = 50
            button.onAction = (e:ActionEvent) => {
                val selected = listView.selectionModel.apply().getSelectedItems()
                listView.items = listView.items.apply.diff(selected)
                println("Button was clicked")
            }

            val comboBox = new ComboBox(List("Scala","Java","C++","Haskell"))
            comboBox.layoutX=200
            comboBox.layoutY=50
            comboBox.onAction = (e:ActionEvent) => {
                listView.items.apply() += comboBox.selectionModel.apply().getSelectedItem()
            }

            val listView = new ListView(List("AWT", "Swing", "JavaFX", "ScalaFX"))
            listView.layoutX=100
            listView.layoutY=100
            listView.prefHeight=150

            content = List(button, comboBox, listView)
        }
    }
}