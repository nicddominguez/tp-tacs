package server.tacsgrupo3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["server"])
class Grupo3Application

fun main(args: Array<String>) {
	runApplication<Grupo3Application>(*args)
}
