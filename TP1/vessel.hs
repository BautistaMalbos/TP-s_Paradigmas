module Vessel ( Vessel, newV, freeCellsV, loadV, loadContainer )
 where

import Container
import Stack
import Route

data Vessel = Ves [ Stack ] Route deriving (Eq, Show)

newV :: Int -> Int -> Route -> Vessel  -- construye un barco según una cantidad de bahias, la altura de las mismas y una ruta
newV bahias altura ruta = Ves (replicate bahias(newS altura)) ruta

freeCellsV :: Vessel -> Int            -- responde la celdas disponibles en el barco
freeCellsV (Ves bahias _) = sum (map freeCellsS bahias) 


 
loadV :: Vessel -> Container -> Vessel -- carga un contenedor en el barco
loadV (Ves bahias ruta) container = Ves (map (loadContainer container ruta) bahias) ruta

loadContainer :: Container -> Route -> Stack -> Stack
loadContainer container route stack
    | holdsS stack container route = stackS stack container
    | otherwise = stack


--unloadV :: Vessel -> String -> Vessel  -- responde un barco al que se le han descargado los contenedores que podían descargarse en la ciudad


--netV :: Vessel -> Int                  -- responde el peso neto en toneladas de los contenedores en el barco
