import scala.collection.mutable
import scala.io.Source

// Dictionary to store the information of the checkboxes
val profesores_horas: mutable.Map[(String, String), List[Int]] = mutable.Map()

// Function to load checkbox information from the file
def loadCheckboxes(filename: String): Unit = {
  try {
    val file = Source.fromFile(filename)
    for (line <- file.getLines()) {
      val data = line.split(" \\(")
      val profesor = data(0)
      val dia_hora = data(1).replaceAll("\\)", "").split(", ")
      val dia = dia_hora(0)
      val hora = dia_hora(1).toInt
      val key = (profesor, dia)
      val horasSeleccionadas = profesores_horas.getOrElse(key, List())
      profesores_horas.put(key, hora :: horasSeleccionadas)
    }
    file.close()
  } catch {
    case e: Exception => println("Error while loading the file: " + e.getMessage)
  }
}

// Function to export checkbox information to the file
def exportCheckboxes(filename: String): Unit = {
  try {
    val file = new java.io.PrintWriter(filename)
    for ((key, value) <- profesores_horas) {
      val (profesor, dia) = key
      for (hora <- value) {
        file.println(s"$profesor ($dia, $hora)")
      }
    }
    file.close()
  } catch {
    case e: Exception => println("Error while exporting the file: " + e.getMessage)
  }
}

// Function to add checkbox information
def checkboxClick(profesor: String, dia: String, hora: Int): Unit = {
  val key = (profesor, dia)
  val horasSeleccionadas = profesores_horas.getOrElse(key, List())
  profesores_horas.put(key, hora :: horasSeleccionadas)
}

// Function to print the dictionary
def printDictionary(): Unit = {
  println("Professors and selected hours:")
  for ((key, value) <- profesores_horas) {
    val (profesor, dia) = key
    println(s"$profesor ($dia) => $value")
  }
}

// Load checkbox information from the file (checkboxes.txt is preloaded)
val filename = "checkboxes.txt"
loadCheckboxes(filename)

// Print the dictionary
printDictionary()

// Export checkbox information to the file
exportCheckboxes(filename)
