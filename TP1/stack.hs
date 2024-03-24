module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
 where

import Container
import Route
import Distribution.Utils.Generic (sndOf3)


data Stack = Sta [ Container ] Int deriving (Eq, Show)

newS :: Int -> Stack                          -- construye una Pila con la capacidad indicada 
newS capacidad | capacidad > 0 = Sta [] capacidad

freeCellsS :: Stack -> Int                    -- responde la celdas disponibles en la pila
freeCellsS (Sta con capacidad) = capacidad - length con

stackS :: Stack -> Container -> Stack         -- apila el contenedor indicado en la pila
stackS (Sta listaDeContenedores pilas)  contenedor | freeCellsS (Sta listaDeContenedores pilas) > 0  = Sta (listaDeContenedores ++ [contenedor]) pilas


netS :: Stack -> Int                          -- responde el peso neto de los contenedores en la pila
netS (Sta pilaDeContenedores num)   |null pilaDeContenedores = 0
                                    |length pilaDeContenedores == 1 = netC (head pilaDeContenedores)
                                    |otherwise = netC (head pilaDeContenedores) + netS (Sta (tail pilaDeContenedores) num)


holdsS :: Stack -> Container -> Route -> Bool -- indica si la pila puede aceptar el contenedor considerando las ciudades en la ruta
                                            -- nuevo contenedor no puede tener destino posterior al de abajo
holdsS sta container ruta 
                          | verificarCondicionPesoContenedor container && verificarCondicionDestino sta container ruta && verificarCondicionPesoStack container sta = True
                          | otherwise = False

verificarCondicionPesoContenedor :: Container -> Bool
verificarCondicionPesoContenedor container | netC container > 20 = False
                                           | otherwise = True

verificarCondicionDestino:: Stack -> Container -> Route -> Bool
verificarCondicionDestino (Sta listacontenedores capacidad) container ruta| null listacontenedores = True
                                                                          | inOrderR ruta (destinationC (last listacontenedores)) (destinationC container) = True
                                                                          | destinationC container == destinationC (last listacontenedores) = True

verificarCondicionPesoStack:: Container -> Stack -> Bool
verificarCondicionPesoStack container sta | netC container + netS sta > 20 || freeCellsS sta < 1 = False
                                          | otherwise = True

popS :: Stack -> String -> Stack              -- quita del tope los contenedores con destino en la ciudad indicada
popS (Sta listacontenedores capacidad) destino  | null listacontenedores = Sta [] capacidad  -- Manejo del caso de lista vacía
                                                |destinationC (last listacontenedores) == destino = popS (Sta (init listacontenedores) capacidad) destino
                                                |destinationC (last listacontenedores) /= destino = (Sta listacontenedores capacidad) 
                                               


 