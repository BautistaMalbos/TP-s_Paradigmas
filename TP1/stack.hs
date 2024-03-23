module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
 where

import Container
import Route
import Distribution.Utils.Generic (sndOf3)


data Stack = Sta [ Container ] Int deriving (Eq, Show)

newS :: Int -> Stack                          -- construye una Pila con la capacidad indicada 
newS = Sta []

freeCellsS :: Stack -> Int                    -- responde la celdas disponibles en la pila
freeCellsS (Sta con capacidad) = capacidad - length con

stackS :: Stack -> Container -> Stack         -- apila el contenedor indicado en la pila
stackS (Sta listaDeContenedores pilas)  contenedor  | freeCellsS (Sta listaDeContenedores pilas) <= 0 = Sta listaDeContenedores pilas
                                                    | netS (Sta listaDeContenedores pilas) + netC contenedor > 20 = Sta listaDeContenedores pilas
                                                    | otherwise = Sta (listaDeContenedores ++ [contenedor]) pilas
                                                

netS :: Stack -> Int                          -- responde el peso neto de los contenedores en la pila
netS (Sta pilaDeContenedores num)   |null pilaDeContenedores = 0
                                    |length pilaDeContenedores == 1 = netC(head pilaDeContenedores)
                                    |otherwise = netC(head pilaDeContenedores) + netS(Sta (tail pilaDeContenedores) num)


holdsS :: Stack -> Container -> Route -> Bool
holdsS (Sta listacontenedores capacidad) container ruta | freeCellsS (Sta listacontenedores capacidad) == 0 = False
                                                        | null listacontenedores = True  -- Manejo del caso de lista vacía
                                                        | inOrderR ruta (destinationC container) (destinationC (last listacontenedores)) = False
                                                        | netS (Sta listacontenedores capacidad) + netC container > 20 = False
                                                        | otherwise = True


popS :: Stack -> String -> Stack              -- quita del tope los contenedores con destino en la ciudad indicada
popS (Sta listacontenedores capacidad) destino  | null listacontenedores = Sta [] capacidad  -- Manejo del caso de lista vacía
                                                |destinationC (last listacontenedores) == destino = popS (Sta (init listacontenedores) capacidad) destino
                                                |otherwise = Sta listacontenedores capacidad


