import Container
import Route
import Stack
import Vessel

container1 = newC "Puerto A" 4
container2 = newC "Puerto B" 6
container3 = newC "Puerto C" 10

ruta1 = newR ["Puerto A", "Puerto B", "Puerto C", "Puerto D"]

stack1 = newS 4
stack2 = stackS stack1 container1

barco1 = newV 1 2 ruta1





