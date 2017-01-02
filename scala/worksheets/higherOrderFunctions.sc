// scala worksheet
println("Hola Mundo")

def cube(x: Int): Int = x * x * x

def fact(a: Int): Int = if (a == 0) 1 else a * fact(a - 1)

def sumInts(a: Int, b: Int): Int =
  if (a > b) 0 else a + sumInts(a + 1, b)
println("sumInts() = " + sumInts(3, 6))

def sumCubes(a: Int, b: Int): Int =
  if (a > b) 0 else cube(a) + sumCubes(a + 1, b)
println("sumCubes() = " + sumCubes(3, 6))

def sumFactorials(a: Int, b: Int): Int =
  if (a > b) 0 else fact(a) + sumFactorials(a + 1, b)
println("sumFactorials() = " + sumFactorials(3, 6))