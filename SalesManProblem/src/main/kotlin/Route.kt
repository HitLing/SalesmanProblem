import java.util.function.Consumer

class Route {
    private var cities: ArrayList<City?> = ArrayList<City?>()
    private var isFitnessChanged = true
    private var fitness = 0.0

    constructor(cities: ArrayList<City>){
        this.cities.addAll(cities)
        this.cities.shuffle()
    }


    constructor(geneticAlgorithm: GeneticAlgorithm){
        geneticAlgorithm.initialRoute!!.forEach(Consumer { x: City -> cities.add(null) })
    }

    public fun getCitiesName(): ArrayList<String>{
        var citiesName: ArrayList<String> = ArrayList<String>()
        cities.forEach(Consumer { x:City? -> citiesName.add(x!!.getName()) })
        return citiesName
    }

    public fun getCities(): ArrayList<City?> {
        isFitnessChanged = true
        return cities
    }

    public fun getFitness(): Double{
        if(isFitnessChanged == true){
            fitness = (1/calculateTotalDistance()) * 10000
            isFitnessChanged = false
        }
        return fitness
    }

    public fun calculateTotalDistance(): Double{
        val citiesSize = this.cities.size
        return cities.stream().mapToDouble { x ->
            val cityIndex = cities.indexOf(x)
            var returnValue = 0.0
            if (cityIndex < citiesSize - 1) returnValue = x!!.measureDistance(cities[cityIndex + 1]!!)
            returnValue
        }.sum().toDouble() + this.cities.get(0)!!.measureDistance(this.cities.get(citiesSize-1)!!)
    }

}
