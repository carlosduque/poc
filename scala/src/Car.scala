/**
  * Created by carlos on 8/25/16.
  */
class Car(val year : Int, var miles : Int) {
  def drive(distance : Int) = {
    miles += distance
  }
}

val car = new Car(2011, 0)
println(car.year)
println(car.getYear())
println(car.miles)
car.drive(10)
println(car.miles)
car drive 10
println(car.miles)