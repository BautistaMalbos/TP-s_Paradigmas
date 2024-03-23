import Container
import Route
import Stack
import Vessel

import Control.Exception
import System.IO.Unsafe

double :: Int -> Int
double n = 2*n

--DEFINIMOS CONTAINERS DE EJEMPLO
container1 = newC "Puerto A" 5
container2 = newC "Puerto B" 5
container3 = newC "Puerto C" 10

--DEFINIMOS RUTA DE EJEMPLO
ruta1 = newR ["Puerto A", "Puerto B", "Puerto C", "Puerto D"]

--DEFINIMOS STACKS DE EJEMPLO
pila1 = newS 2
pila1Con1 = stackS pila1 container1
pila1Con2 = stackS pila1Con1 container2

--DEFINIMOS STACKS DESCARGADOS
pila1Unloaded = popS pila1Con2 "Puerto B"

--DEFINIMOS BARCOS DE EJEMPLO
barco0 = newV 2 2 ruta1
barcoA = loadV barco0 container1
barcoAB = loadV barcoA container2
barcoABC = loadV barcoAB container3

--DEFINIMOS BARCOS DESCARGADOS
barcoAUnloaded = unloadV barcoAB "Puerto B"

test = [
    netV barcoAB ==  double (netV barcoA), -- netV
    unloadV barcoAB "Puerto B" == barcoA, -- unloadV
    freeCellsV barco0 == 4, -- newV 
    freeCellsV barcoA == 3, -- loadV
    freeCellsV barcoAB == 2, -- loadV
    freeCellsV barcoABC == 1, -- loadxBahias
    freeCellsV barcoAUnloaded == 3, -- unloadV
    freeCellsS pila1Unloaded == 1 -- popS
    ]

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()

