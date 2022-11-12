import java.util.stream.IntStream

class Population {
    private var routies = ArrayList<Route>(GeneticAlgorithm.POPULATION_SIZE)

    constructor(populationSize: Int, geneticAlgorithm: GeneticAlgorithm){
        IntStream.range(0, populationSize).forEach { x: Int -> routies.add(Route(geneticAlgorithm.initialRoute!!)) }
    }

    constructor(populationSize: Int, cities: ArrayList<City>){
        IntStream.range(0, populationSize).forEach { x: Int -> routies.add(Route(cities)) }

    }


    public fun getRouties(): ArrayList<Route>{ return routies }
    public fun sortRoutesByFitness(){
        routies.sortWith(Comparator { route1, route2 ->
            var flag = 0
            if (route1.getFitness() > route2.getFitness()) flag =
                -1 else if (route1.getFitness() < route2.getFitness()) flag = 1
            flag
        })
    }



}