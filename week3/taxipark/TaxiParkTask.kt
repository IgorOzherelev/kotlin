package taxipark

import kotlin.math.absoluteValue

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {

    val realDrivers: List<Driver> = this.trips.map{ elem -> elem.driver };
    return this.allDrivers.filter { elem -> elem !in realDrivers }.toSet();
}


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter{ passanger ->
            this.trips.count { trip ->
                passanger in trip.passengers } >= minTrips}.toSet();

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter { passenger ->
            this.trips.filter{ trip ->
                passenger in trip.passengers}.count{ trip ->
                trip.driver == driver} > 1 }.toSet();

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {

    fun smartOrNot(passenger: Passenger) : Boolean {
        val passengerTrips: List<Trip> = this.trips.filter{ trip -> passenger in trip.passengers};
        val noDiscountTrips: List<Trip> = passengerTrips.filter { trip ->  trip.discount == null};

        val countTrips: Int = passengerTrips.size;
        val noDiscountTripsPart: Double = noDiscountTrips.size.toDouble() / countTrips.toDouble();
        return !passengerTrips.isEmpty() && noDiscountTripsPart < 0.5;
    }

    return this.allPassengers.filter { passanger -> smartOrNot(passanger) }.toSet();

}
/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    fun giveRangeByDuration(duration: Int) : IntRange?{
        val tmp = (duration / 10).absoluteValue;

        return 10 * tmp until 10 * (tmp + 1)
    }

    val tripByRanges: Map<IntRange?, List<Trip>>? = this.trips.groupBy{ trip ->
        giveRangeByDuration(trip.duration)};

    return tripByRanges?.maxBy {  tripByRange -> tripByRange.value.size }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    if (this.trips.isEmpty()) return false;

    val oneFifths: Int = this.allDrivers.size / 5
    var oneFifthsTotal: Double = 0.0;

    val totalSum: Double = this.trips.map { trip -> trip.cost }.sum();

    val driversPerTrips: Map<Driver, List<Trip>> = this.trips.groupBy { trip ->
        trip.driver };
    val driversPerCost: MutableMap<Driver, Double> = driversPerTrips.mapValues { pair ->
        pair.value.map{ it.cost }.sum() }.toMutableMap();

    repeat(oneFifths){

        val wealthyDriver = driversPerCost.maxBy { it.value };
        oneFifthsTotal += driversPerCost.values.max()!!;

        driversPerCost.remove(wealthyDriver!!.key);
    }

    return oneFifthsTotal >= totalSum * 0.8;
}