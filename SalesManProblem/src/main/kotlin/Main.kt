import java.util.*
import java.util.function.Consumer

fun printPopulation(population: Population) {
    population.getRouties().forEach(Consumer { x: Route ->
        println(
            Arrays.toString(
                x.getCitiesName().toTypedArray()
            ) + " |   " + String.format("%.4f", x.getFitness()) + "    |     " + String.format(
                "%.2f",
                x.calculateTotalDistance()
            )
        )
    })
    println()
}

fun printHeading(generatioNumber: Int, initialRoute: ArrayList<City>) {
    println("> Generation #$generatioNumber")
    val headingColumn1 = "Route"
    val remainingHeadingColumns = "  Fitness   |     Distance"
    var cityNamesLength = 0
    for (x in 0 until initialRoute.size) cityNamesLength += initialRoute.get(x).getName().length
    val arrayLength: Int = cityNamesLength + initialRoute.size * 2
    val partiaLength = (arrayLength - headingColumn1.length) / 2
    for (x in 0 until partiaLength) print(" ")
    print(headingColumn1)
    for (x in 0 until partiaLength) print(" ")
    if (arrayLength % 2 == 0) print(" ")
    println(" | $remainingHeadingColumns")
    cityNamesLength += remainingHeadingColumns.length + 6
    for (x in 0 until cityNamesLength + initialRoute.size * 2) print("-")
    println("")
}



fun main(args: Array<String>) {
    var internalRoad: ArrayList<City> = ArrayList(Arrays.asList(
        City("Sergeev Posad",56.1800, 38.0800),
    City("Pereslavl Zalesski",56.4417,38.5122),
    City("Rostov",  57.2013,39.4564),
    City("Iaroslavl",57.6299,39.8737),
    City("Kostroma", 57.7665,40.9269),
    City("Ivanovo",	56.9972,40.9714),
    City("Suzdal",56.2516,40.2656),
    City("Vladimir", 56.0800,40.2500),
    City("Uglich", 57.3139,38.1954),
    City("Riazan", 	54.6269,  39.6916),
    ))
    var population = Population(GeneticAlgorithm.POPULATION_SIZE,internalRoad)
    population.sortRoutesByFitness()
    val geneticAlgorithm = GeneticAlgorithm(internalRoad)
    var generatioNumber = 0
    printHeading(generatioNumber++,internalRoad)
    printPopulation(population)
    while (generatioNumber < GeneticAlgorithm.NUMB_OF_GENERATIONS){
        printHeading(generatioNumber++,internalRoad)
        population = geneticAlgorithm.evolve(population)
        population.sortRoutesByFitness()
        printPopulation(population)
    }
    println("Best Route Found: "+ population.getRouties().get(0).getCitiesName() + "\nTotal distance: "+ String.format("%.2f", population.getRouties().get(0).calculateTotalDistance()))//population.getRouties().get(0).calculateTotalDistance().toString())
}