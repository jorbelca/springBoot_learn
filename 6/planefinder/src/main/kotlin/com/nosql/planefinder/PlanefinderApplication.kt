package com.nosql.planefinder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlanefinderApplication

fun main(args: Array<String>) {
	runApplication<PlanefinderApplication>(*args)
}
