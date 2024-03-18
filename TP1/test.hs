import Container
import Route
import Stack

container1 = newC "Puerto A" 4
container2 = newC "Puerto B" 6
container3 = newC "Puerto C" 10

ruta1 = newR ["Puerto A", "Puerto B", "Puerto C", "Puerto D"]

stack1 = newS 4
stack2 = stackS stack1 container1



