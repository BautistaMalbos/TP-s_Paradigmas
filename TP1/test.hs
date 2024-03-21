import Container
import Route
import Stack
import Vessel


double :: Int -> Int
double n = 2*n

container1 = newC "Puerto A" 4
container2 = newC "Puerto B" 8
container3 = newC "Puerto C" 16

ruta1 = newR ["Puerto A", "Puerto B", "Puerto C", "Puerto D"]

stack1 = newS 4
stack2 = stackS stack1 container1

barco1 = newV 1 2 ruta1

barco2 = loadV barco1 container1

barco3 = loadV barco2 container2

test = [
    netV barco3 ==  double (netV barco2)]

