class City {
    companion object{
        private final val EARTH_EQUATOR_RADIUS = 6378.1370
        private final val CONVERT_DEGREES_TO_RADIANS = Math.PI/180.0

    }
    private var longitude: Double
    private var latitude: Double
    private var name: String



    constructor(name: String, longitude: Double, latitude: Double){
        this.name = name
        this.longitude = longitude * CONVERT_DEGREES_TO_RADIANS
        this.latitude = latitude + CONVERT_DEGREES_TO_RADIANS
    }



    public fun getName(): String{ return name}
    public fun getLongitude(): Double{return longitude}
    public fun getLatitude(): Double{return latitude}
    public fun measureDistance(city: City): Double{
        var deltaLongitude = (city.getLongitude() - this.getLongitude())
        var deltaLatitude = (city.getLatitude() - this.getLatitude())
        val a = Math.pow(Math.sin(deltaLatitude / 2.0), 2.0) +
                Math.cos(getLatitude()) * Math.cos(city.getLatitude()) *
                Math.pow(Math.sin(deltaLongitude / 2.0), 2.0)
        return EARTH_EQUATOR_RADIUS * 2.0 *Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a))
    }

}