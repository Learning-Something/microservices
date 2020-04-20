package com.example.beercatalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import org.springframework.boot.CommandLineRunner
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.stereotype.Component
import java.util.function.Consumer
import java.util.stream.Stream

@EnableDiscoveryClient
@SpringBootApplication
class BeerCatalogServiceApplication

fun main(args: Array<String>) {
	runApplication<BeerCatalogServiceApplication>(*args)
}

@Entity
data class Beer(var name: String) {
	@Id
	@GeneratedValue
	var id: Long? = null
}

@RepositoryRestResource
interface BeerRepository : JpaRepository<Beer, Long>

@Component
internal class BeerInitializer(private val beerRepository: BeerRepository) : CommandLineRunner {

	@Throws(Exception::class)
	override fun run(vararg args: String) {
		Stream.of("Kentucky Brunch Brand Stout", "Good Morning", "Very Hazy", "King Julius",
				"Budweiser", "Coors Light", "PBR")
				.forEach { beer -> beerRepository.save(Beer(beer)) }

		beerRepository.findAll().forEach(Consumer<Beer> { println(it) })
	}
}