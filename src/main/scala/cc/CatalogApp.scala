package cc

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scala.collection.mutable
import scalafx.scene.layout.FlowPane
import scalafx.geometry.Orientation
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import javafx.event.ActionEvent
import scalafx.scene.text.Text
import scalafx.scene.text.TextAlignment

object CatalogApp extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Course Catalog"
    scene = new Scene(1200, 600) {
      var catalog = new CourseCatalog()
      var screen = new FlowPane(Orientation.VERTICAL) {
        children = Array(
          new Label("Welcome to Your Course Catalog"),
          new Button("continue") { onAction = (e: ActionEvent) => home() }
        )
      }
      screen.prefWrapLength.value = 580

      def home(): Unit = {
        screen.children = Array(
          new Label("Home Page"),
          new Button("View Grades & GPA") {
            onAction = (e: ActionEvent) => { gradesGpa() }
          },
          new Button("View Credit") {
            onAction = (e: ActionEvent) => { credit() }
          },
          new Button("View Current Classes") {
            onAction = (e: ActionEvent) => { current() }
          },
          new Button("View Next Semester") {
            onAction = (e: ActionEvent) => { next() }
          },
          new Button("View All Courses") {
            onAction = (e: ActionEvent) => { all() }
          },
          new Button("View Graduation Requirements"){
            onAction = (e:ActionEvent) =>{gradReqs()}
          }
        )
      }
      def gradReqs():Unit = {
        var temp = catalog.getGradShort()
        temp.foreach(x=> x.onAction=(e:ActionEvent)=>gradReq(catalog.reqs(x.text.value)))
        screen.children = Array(
          new Label("Graduation Requirements: "),
          new Label(catalog.getReqCompl()))++temp++Array(
          new Button("Home") { onAction = (e: ActionEvent) => home() }
        )
      }
      def gradReq(x:Requirement):Unit = {
        screen.children = Array(
          new Text( x.toString()),
          new Button("Back"){onAction=(e:ActionEvent)=>gradReqs()},
          new Button("Home") { onAction = (e: ActionEvent) => home() }
        )
      }
      def gradesGpa(): Unit = {
        screen.children = Array(
          new Label("Grades"),
          new Label(catalog.getGrades),
          new Label("GPA"),
          new Label(catalog.getGPA),
          new Button("Home") { onAction = (e: ActionEvent) => home() }
        )
      }
      def credit(): Unit = {
        screen.children = Array(
          new Label("Credit\n"),
          new Label(catalog.getCredit()),
          new Label("\nTotal: " + catalog.getHours() + " hours"),
          new Button("Home") { onAction = (e: ActionEvent) => home() }
        )
      }
      def current(): Unit = {
        var temp = catalog.getCurrent()
        temp.foreach(x =>
          x.onAction = (e: ActionEvent) =>
            courseLong(
              catalog.courses(x.text.value.substring(0, 9)),
              new Button("Back") { onAction = (e: ActionEvent) => current() }
            )
        )
        screen.children = Array(
          new Label("Current Semester: " + catalog.current.name)
        ) ++ temp ++ Array(new Label("Total Hours: "+catalog.current.getHours()),new Button("Home") {
          onAction = (e: ActionEvent) => home()
        })
      }
      def courseLong(x: Course, back: Button): Unit = {
        var temp = new Text(x.getLong())
        temp.setTextAlignment(TextAlignment.LEFT)
        temp.wrappingWidth.value = 800
        screen.children = Array(
          temp,
          back,
          new Button("Home") { onAction = (e: ActionEvent) => home() }
        )
      }
      def courseEdit(x: Course, back: Button): Unit = {
        var temp = new Text(x.getLong())
        temp.setTextAlignment(TextAlignment.LEFT)
        temp.wrappingWidth.value = 800
        screen.children = Array(
          temp,
          new Button("Remove Course") {
            onAction = (e: ActionEvent) => catalog.next.remove(x)
          },
          back,
          new Button("Next Semester") { onAction = (e: ActionEvent) => next() }
        )
      }
      def courseAdd(x: Course, back: Button):Unit = {
        var temp = new Text(x.getLong())
        temp.setTextAlignment(TextAlignment.LEFT)
        temp.wrappingWidth.value = 800
        screen.children = Array(
          temp,
          new Button("Add Course") {
            onAction = (e: ActionEvent) => catalog.next.add(x)
          },
          back,
          new Button("Next Semester") { onAction = (e: ActionEvent) => next() }
        )
      }
      def next(): Unit = {
        var temp = catalog.getNext()
        temp.foreach(x =>
          x.onAction = (e: ActionEvent) =>
            courseEdit(
              catalog.courses(x.text.value.substring(0, 9)),
              new Button("Back") { onAction = (e: ActionEvent) => next() }
            )
        )
        screen.children = Array(
          new Label("Next Semester: " + catalog.next.name),new Label("Recommended Courses: Data Abstr., CS Coll., Func. Lang.")
        ) ++ temp ++ Array(new Label("Total Hours: "+catalog.next.getHours()),new Button("Add a Course"){onAction=(e:ActionEvent)=>allEdit(new Button("Back"){onAction=(e:ActionEvent)=>next()})},new Button("Home") {
          onAction = (e: ActionEvent) => home()
        })
      }
      def all(): Unit = {
        var temp = catalog.getAll()
        temp.foreach(x =>
          x.onAction = (e: ActionEvent) =>
            courseLong(
              catalog.courses(x.text.value.substring(0, 9)),
              new Button("Back") { onAction = (e: ActionEvent) => all() }
            )
        )
        screen.children = Array(
          new Label("All Courses: ")
        ) ++ temp ++ Array(new Button("Home") {
          onAction = (e: ActionEvent) => home()
        })
      }
      def allEdit(back: Button): Unit = {
        var temp = catalog.getReady()
        temp.foreach(x =>
          x.onAction = (e: ActionEvent) =>
            courseAdd(
              catalog.courses(x.text.value.substring(0, 9)),
              new Button("Back"){onAction=(e:ActionEvent)=>allEdit(back)}
            )
        )
        screen.children = Array(
          new Label("Available Courses: ")
        ) ++ temp ++ Array(
          back,
          new Button("Home") {
            onAction = (e: ActionEvent) => home()
          }
        )
      }

      content = screen
    }
  }
}
