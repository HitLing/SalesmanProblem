import java.util.stream.IntStream

class GeneticAlgorithm(var initialRoute: ArrayList<City>?) {
    //val initialRoute: ArrayList<City>? = initialRoute

    //fun getInitialRoutea(): ArrayList<City>{return initialRoute!!}

    fun crosoverPopulation(population: Population): Population {
        val crosoverPopulation = Population(population.getRouties().size, this)
        IntStream.range(0, NUMBER_OF_ELITE_ROUTES).forEach { x: Int ->
            crosoverPopulation.getRouties()[x] = population.getRouties()[x]
        }
        IntStream.range(NUMBER_OF_ELITE_ROUTES, crosoverPopulation.getRouties().size).forEach { x: Int ->
            val route1 = selectTournamentPopulation(population).getRouties()[0]
            val route2 = selectTournamentPopulation(population).getRouties()[0]
            crosoverPopulation.getRouties()[x] = crosoverRoute(route1, route2)
        }
        return crosoverPopulation
    }

    private fun fillNullsInCrossoverRoute(crossoverRoute: Route, route: Route): Route {
        route.getCities().stream().filter { x: City? ->
            !crossoverRoute.getCities().contains(x)
        }.forEach { cityX: City? ->
            for (y in route.getCities().indices) {
                if (crossoverRoute.getCities()[y] == null) {
                    crossoverRoute.getCities()[y] = cityX!!
                    break
                }
            }
        }
        return crossoverRoute
    }

    fun mutatePopulation(population: Population): Population {
        population.getRouties().stream().filter { x: Route? ->
            population.getRouties().indexOf(x) >= NUMBER_OF_ELITE_ROUTES
        }.forEach { x: Route -> mutateRoute(x) }
        return population
    }

    fun crosoverRoute(route1: Route, route2: Route): Route {
        val crossoverRoute = Route(this)
        var tempRoute1 = route1
        var tempRoute2 = route2
        if (Math.random() < 0.5) {
            tempRoute1 = route2
            tempRoute2 = route1
        }
        for (x in 0 until crossoverRoute.getCities().size / 2) crossoverRoute.getCities()[x] = tempRoute1.getCities()[x]
        return fillNullsInCrossoverRoute(crossoverRoute, tempRoute2)
    }

    fun selectTournamentPopulation(population: Population): Population {
        val tournamentPopulation = Population(TOURNAMENT_SELECTION_SIZE, this)
        IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach { x: Int ->
            tournamentPopulation.getRouties()[x] =
                population.getRouties()[(Math.random() * population.getRouties().size).toInt()]
        }
        tournamentPopulation.sortRoutesByFitness()
        return tournamentPopulation
    }

    fun mutateRoute(route: Route): Route {
        route.getCities().stream().filter { x: City? -> Math.random() < MUTATION_RATE }
            .forEach { cityX: City? ->
                val y = (route.getCities().size * Math.random()).toInt()
                val cityY = route.getCities()[y]
                route.getCities()[route.getCities().indexOf(cityX)] = cityY
                route.getCities()[y] = cityX!!
            }
        return route
    }

    public fun evolve(population: Population): Population{
        return mutatePopulation(crosoverPopulation(population))
    }

    companion object {
        const val MUTATION_RATE = 0.25
        const val POPULATION_SIZE = 10
        const val TOURNAMENT_SELECTION_SIZE = 3
        const val NUMBER_OF_ELITE_ROUTES = 1
        const val NUMB_OF_GENERATIONS = 100
    }
}