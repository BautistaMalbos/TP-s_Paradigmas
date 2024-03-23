module Vessel ( Vessel, newV, freeCellsV, loadV, loadxBahia, netV, unloadV, unloadBahias  )
 where
import Container
import Stack
import Route

data Vessel = Ves [ Stack ] Route deriving (Eq, Show)

newV :: Int -> Int -> Route -> Vessel  -- construye un barco según una cantidad de bahias, la altura de las mismas y una ruta
newV bahias altura ruta = Ves (replicate bahias(newS altura)) ruta


freeCellsV :: Vessel -> Int            -- responde la celdas disponibles en el barco
freeCellsV (Ves bahias _) = sum (map freeCellsS bahias) 


loadV :: Vessel -> Container -> Vessel
loadV (Ves bahias ruta) container = Ves (loadxBahia bahias container ruta) ruta

loadxBahia :: [Stack] -> Container -> Route -> [Stack]
loadxBahia [] _ _ = []  -- no hay mas bahias para revisar
loadxBahia (x:xs) container ruta    | holdsS x container ruta = stackS x container : xs  --si la bahia puede aceptar el contenedor lo carga y devuelve la lista sin cambiar las otras bahias
                                    | otherwise = x : loadxBahia xs container ruta  -- si la bahia no puede aceptar el contenedor se pasa a la siguiente bahia


unloadV :: Vessel -> String -> Vessel
unloadV (Ves bahias ruta) des = Ves (unloadBahias bahias des) ruta


unloadBahias :: [Stack] -> String -> [Stack]
unloadBahias [] _ = []
unloadBahias sta des = popS (head sta) des : unloadBahias (tail sta) des


netV :: Vessel -> Int                  -- responde el peso neto en toneladas de los contenedores en el barco
netV (Ves bahias _) | null bahias = 0
                    | otherwise = sum (map netS bahias)