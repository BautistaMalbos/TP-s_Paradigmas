module Stack ( Stack, newS, freeCellsS, stackS, netS )
 where

import Container
import Distribution.Utils.Generic (sndOf3)


data Stack = Sta [ Container ] Int deriving (Eq, Show)

newS :: Int -> Stack                          -- construye una Pila con la capacidad indicada 
newS = Sta []

freeCellsS :: Stack -> Int                    -- responde la celdas disponibles en la pila
freeCellsS (Sta con capacidad) = capacidad - length con

stackS :: Stack -> Container -> Stack         -- apila el contenedor indicado en la pila
stackS (Sta listaDeContenedores pilas)  contenedor = Sta (listaDeContenedores ++ [contenedor]) pilas
                                                


--EJEMPLOS:
-----------------------------------
s1 :: Stack
s1 = newS 3

s2 :: Stack
s2 = newS 6

contenedor1 :: Container
contenedor1 = newC "Ciudad A" 10

contenedor2 :: Container
contenedor2 = newC "Ciudad B" 8

apilamiento1 :: Stack
apilamiento1 = stackS s1 contenedor1

apilamiento2 :: Stack
apilamiento2 = stackS s2 contenedor1

apilamiento3 :: Stack
apilamiento3 = stackS s2 contenedor2

lugareslibres1 :: Int
lugareslibres1 = freeCellsS apilamiento1 -- cuantos lugares le quedan a apilamiento 1 --> apilamiento 1 es Sta [con "Ciudad A" 10] 3

------------------------------------

netS :: Stack -> Int                          -- responde el peso neto de los contenedores en la pila
netS (Sta pilaDeContenedores num)| length pilaDeContenedores == 1 = netC(head pilaDeContenedores)
                                 | otherwise = netC(head pilaDeContenedores) + netS(Sta (tail pilaDeContenedores) num)

--EJEMPLOS:
------------------------------------
pesoNeto1 :: Int
pesoNeto1 = netS apilamiento1

pesoNeto2 :: Int
pesoNeto2 = netS apilamiento3

pesoNeto3 :: Int
pesoNeto3 = netS apilamiento1 + netS apilamiento3
