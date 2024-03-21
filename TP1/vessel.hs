module Vessel ( Vessel, newV, freeCellsV, loadV, loadxBahia, loadContainer, netV  )
 where

import Container
import Stack
import Route

data Vessel = Ves [ Stack ] Route deriving (Eq, Show)

newV :: Int -> Int -> Route -> Vessel  -- construye un barco según una cantidad de bahias, la altura de las mismas y una ruta
newV bahias altura ruta = Ves (replicate bahias(newS altura)) ruta

freeCellsV :: Vessel -> Int            -- responde la celdas disponibles en el barco
freeCellsV (Ves bahias _) = sum (map freeCellsS bahias) 


 
{-loadV :: Vessel -> Container -> Vessel -- carga un contenedor en el barco
loadV (Ves bahias ruta) container = Ves (map (loadContainer container ruta) bahias) ruta

loadContainer :: Container -> Route -> Stack -> Stack --chequea si se puede cargar el container
loadContainer container route stack | holdsS stack container route = stackS stack container
                                    | otherwise = stack-}


loadV :: Vessel -> Container -> Vessel
loadV (Ves bahias ruta) container = Ves (loadxBahia bahias container ruta) ruta

loadxBahia :: [Stack] -> Container -> Route -> [Stack]
loadxBahia [] _ _ = []  -- no hay mas bahias para revisar
loadxBahia (x:xs) container ruta    | holdsS x container ruta = stackS x container : xs  -- Si la bahia puede aceptar el contenedor lo carga y devuelve la lista sin cambiar las otras bahias
                                    | otherwise = x : loadxBahia xs container ruta  -- Si la bahia no puede aceptar el contenedor se pasa a la siguiente bahia

loadContainer :: Container -> Route -> Stack -> Stack
loadContainer container ruta stack  | holdsS stack container ruta = stackS stack container
                                    | otherwise = stack

--unloadV :: Vessel -> String -> Vessel  -- responde un barco al que se le han descargado los contenedores que podían descargarse en la ciudad


netV :: Vessel -> Int                  -- responde el peso neto en toneladas de los contenedores en el barco
netV (Ves bahias _) | null bahias = 0
                    | otherwise = sum (map netS bahias)