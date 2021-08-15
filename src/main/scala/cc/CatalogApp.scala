package cc

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scala.collection.mutable
import scalafx.scene.layout.FlowPane
import scalafx.geometry.Orientation
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.scene.text.Text
import scalafx.scene.text.TextAlignment
import scalafx.geometry.Insets
import scalafx.scene.paint.Color
import scalafx.event
import scalafx.event.EventHandler

object CatalogApp extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Course Catalog"
    scene = new Scene(800,400) {
      
      val catalog = new CourseCatalog("all.txt",List("ap.txt","fl20.txt","sp21.txt"),"fl21.txt","sp22.txt")

      val txt = new Text(50,50,"WELCOME TO YOUR COURSE CATALOG")
      val but1 = new Button("continue"){onAction = (e:ActionEvent) => home;layoutX=50;layoutY=100}
      val but2 = new Button("exit"){onAction = (e:ActionEvent) => System.exit(0);layoutX=50;layoutY=150}

      content = List(txt,but1,but2)

      def home():Unit = {
        val text = new Text(50,50,"HOME PAGE")
      val button1 = new Button("Grades"){onAction = (e:ActionEvent) => grades;layoutX=50;layoutY=100}
      val button2 = new Button("Credits & GPA"){onAction = (e:ActionEvent) => println("Credit");layoutX=50;layoutY=150}
      val button3 = new Button("Current Classes"){onAction = (e:ActionEvent) => println("Current Classes");layoutX=50;layoutY=200}
      val button4 = new Button("Next Semester"){onAction = (e:ActionEvent) => println("Next Semester");layoutX=50;layoutY=250}
      val button5 = new Button("All Courses"){onAction = (e:ActionEvent) => println("All Courses");layoutX=50;layoutY=300}
      val button6 = new Button("Graduation Requirements"){onAction = (e:ActionEvent) => println("Graduation Requirements");layoutX=50;layoutY=350}

      content = List(text,button6,button5,button4,button3,button2,button1)
      }

      def grades():Unit = {
        val text = new Text(50,50,"Grades")
        val txt1 = new Text(50,100,catalog.getGrades)
      val button1 = new Button("Home"){onAction = (e:ActionEvent) => home;layoutX=50;layoutY=350}

        //println(catalog.getGrades())
        content = List(text,txt1,button1)
      }
      /*
      welcome screen

      "WELCOME TO YOUR INTERACTIVE COURSE CATALOG"
      ("continue")
      ("exit")


      home page

      "HOME PAGE"
      ("Grades & GPA")
      ("Credit")
      ("Current Classes")
      ("Next Semester")
      ("All Courses")
      ("Graduation Requirements")

      */






























      /*
      var Lstyle = "-fx-font: normal bold 20pt sans-serif"
      var catalog = new CourseCatalog()
      var screen = new FlowPane(Orientation.VERTICAL) {
        prefWrapLength.value = 580
        padding = Insets(50,80,50,50)   //added padding; check to make sure it works
        children = Array(
          new Label("Welcome to Your Course Catalog"){style = Lstyle},
          new Button("continue") { onAction = (e: ActionEvent) => home() }
        )
      }

      def home(): Unit = {
        screen.children = Array(
          new Label("Home Page"){style=Lstyle},
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
          new Label("Graduation Requirements: "){style=Lstyle},
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
          new Label("Grades"){style=Lstyle},
          new Label(catalog.getGrades),
          new Label("GPA"){style=Lstyle},
          new Label(catalog.getGPA),
          new Button("Home") { onAction = (e: ActionEvent) => home() }
        )
      }
      def credit(): Unit = {
        screen.children = Array(
          new Label("Credit\n"){style=Lstyle},
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
          new Label("Current Semester: " + catalog.current.name){style=Lstyle}
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
          new Label("Next Semester: " + catalog.next.name)
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
          new Label("All Courses: "){style=Lstyle}
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
          new Label("Available Courses: "){style=Lstyle}
        ) ++ temp ++ Array(
          back,
          new Button("Home") {
            onAction = (e: ActionEvent) => home()
          }
        )
      }

      content = screen
    */
    }
  }
}
