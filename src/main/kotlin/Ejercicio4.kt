import utils.OSController
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.seconds

private val FS = File.separator

private lateinit var pB: Process

/*
En el ejercicio original se pedia que el tiempo de lanzamiento tuviese en cuenta a la cantidad de pasajeros de la capsula
Veia excesivo el tener que esperar hasta 1 minuto o mas haciendo las pruebas, asi que lo deje como un elemento random

La solucion para el ejercicio seria seleccionar la linea escogida (que por suerte siempre es un numero), pasarla a int
Luego a segundos, y entonces sumarlo al 1..5 random en tiempoFinal.
*/

fun main() {
    val CAPSULAS = 4
    var contadorID = 0

    val comprobador: Boolean = OSController.init()
    println("Preparando capsulas...")

    val userDir = System.getProperty("user.dir")
    val pathJar = Paths.get(userDir + FS + "build" + FS + "libs")

    var line: String?

    if (!comprobador) {

        for (i in 0 until CAPSULAS) {
            contadorID += 1
            pB = ProcessBuilder("cmd.exe", "/c", "cd $pathJar & java -jar ProcesosKotlin4-1.0.jar").start()
            val reader = pB.inputStream.bufferedReader()
            while (reader.readLine().also { line = it } != null) {
                println("Capsula: $contadorID | Pasajeros: ${line.toString()}")

                val tiempoFinal = (5..10).random().seconds + (1..5).random().seconds

                println("Lanzando capsula, tiempo estimado: $tiempoFinal ")
                Thread.sleep(tiempoFinal.inWholeMilliseconds)
            }
        }
        System.err.println("Mision completada...")
        exitProcess(2025)

    } else {

        for (i in 0 until CAPSULAS) {
            contadorID += 1
            pB = ProcessBuilder("bash", "-c", "cd $pathJar && java -jar ProcesosKotlin4-1.0.jar").start()
            val reader = pB.inputStream.bufferedReader()
            while (reader.readLine().also { line = it } != null) {
                println("Capsula: $contadorID | Pasajeros: ${line.toString()}")

                val tiempoFinal = (10..20).random().seconds + (1..5).random().seconds

                println("Lanzando capsula, tiempo estimado: $tiempoFinal ")
                Thread.sleep(tiempoFinal.inWholeMilliseconds)
            }
        }
        System.err.println("Mision completada...")
        exitProcess(2024)
    }

}