import Container
import Stack
import Vessel
import Route 

import Control.Exception
import System.IO.Unsafe

ciudad1 = "MDQ"
ciudad2 = "RSL"
ciudad3 = "BHI"
ciudad4 = "BUE"
ciudad5 = "QEQ"

ruta = newR [ ciudad1, ciudad2, ciudad3, ciudad4, ciudad5 ]
rutaInexistente = newR ["Ciudad Paraiso"]

containerMDQ = newC ciudad1 5
containerCopia = containerMDQ
containerRSL = newC ciudad2 7

containerBUE = newC ciudad4 4
containerQEQ = newC ciudad5 10
containerFallido = newC ciudad5 21

barco1x1 = newV 1 1 ruta
barco2x1 = newV 2 1 ruta
barco2x1Cargado = loadV barco2x1 containerMDQ
barco1x2 = newV 1 2 ruta
barco1x2Cargado = loadV barco1x2 containerMDQ
barco2x1Lleno = loadV barco2x1Cargado containerRSL

barco2x2 = newV 2 2 ruta
barco2x2M = loadV barco2x2 containerMDQ
barco2x2MD = loadV barco2x2M containerMDQ
barco2x2MDQ = loadV barco2x2MD containerMDQ

barco2x2MDQLleno = loadV barco2x2MDQ containerMDQ


sLL = newS 2
sXL = stackS sLL containerMDQ
sXX = stackS sXL containerMDQ

tContainer = [destinationC containerMDQ /= destinationC containerRSL, -- Los contenedores no tienen el mismo destino => True
              destinationC containerMDQ == destinationC containerCopia, -- Los contenedores tiene el mismo destino => True
              not(netC containerMDQ + netC containerRSL == netC containerQEQ),--Los pesos de los contenedores son diferentes => not(False) => True
              testF(newC "" 2), --No se le asigna ningun destino
              testF(newC "PuertoRico" (-1)), --Se le asigna un valor negativo al peso
              True]

tRoute = [inOrderR ruta ciudad3 ciudad4, -- En Orden => True
          not(inOrderR ruta "Ciudad No En Lista" ciudad1), --Ciudad no en ruta => not(False) => True
          not(inOrderR ruta ciudad5 ciudad2), -- Las ciudades no estan en Orden => not(False) => True
          inOrderR ruta (destinationC containerMDQ) (destinationC containerRSL), -- En Orden => True
          testF (newR []), --Se le asigna una ruta vacia
          True]

tStack = [freeCellsS sLL == freeCellsS sXL + 1, -- Misma cantidad de celdas => True
          netS sLL + netS sXL == netS sXL, -- Mismos pesos => True
          netS sXL == netC containerMDQ, -- Mismos pesos => True
          holdsS sXL containerMDQ ruta, -- Se puede cargar el contenedor al stack => True
          not(holdsS sXX containerMDQ ruta), -- Se pasa de peso => not(False) => True
          not(holdsS sXX containerMDQ rutaInexistente), -- Se quiere agregar un contenedor que no tiene un destino que este en la ruta => not(False) => True
          popS sXX ciudad1 == sLL, -- Se aplica Pop correctamente => True
          popS sXX ciudad2 == sXX, -- Se quiere eliminar un contenedores cuyo destino no coincide => True
          testF (newS 0), -- Se le asigna una capacidad de 0 a la pila
          testF (stackS sXX containerMDQ), -- Se intenta apilar un contenedor a un stack lleno
          testF (popS (newS 0) ""), -- Se intentan quitar contenedores a un stack vacio
          True]

tVessel = [freeCellsV barco1x2 == freeCellsV barco2x1, -- Mismas celdas libres => True
           freeCellsV barco1x2Cargado == freeCellsV barco2x1Cargado, -- Mismas celdas libres => True
           netV barco2x1Lleno == netC containerMDQ + netC containerRSL, -- Peso del barco con 2 contenedores = Suma del peso de los contenedores => True
           loadV barco2x1 containerMDQ == barco2x1Cargado, -- Carga un contenedor => True
           freeCellsV (unloadV barco2x1Lleno "RSL") == 1, -- Descarga contenedor de RSL al barco con 2 contenedores qe está lleno => True
           unloadV barco2x1Lleno "NIA" == barco2x1Lleno,-- No descarga nada, el destino no es parte de la ruta => True
           unloadV barco2x2MDQLleno "RSL" == barco2x2MDQLleno, -- No descarga nada, no hay contenedores con ese destino => True
           unloadV barco2x2MDQLleno "MDQ" == barco2x2, -- Descarga todo (todos los contenedores tienen destino MDQ) => True
           barco2x1 == unloadV (loadV barco2x1 containerBUE) "BUE", -- Carga y descarga del mismo contenedor => True
           testF (newV 0 1 ruta), -- 0 bahias => Excepcion
           testF (newV 1 0 ruta), -- 1 bahia de altura 0 => Excepcion
           True]

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()
