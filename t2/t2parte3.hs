-- A codificação EAN-13 é um padrão de código de barras usado em vários 
-- tipos de produtos. O número codificado em barras tem 13 dígitos (0-9),
-- sendo o último um dígito verificador (exemplo: 5901234123457, dígito 
-- verificador 7). O cálculo do dígito verificador obedece a algumas 
-- regras simples disponíveis em: 
-- https://www.gs1.org/services/how-calculate-check-digit-manually
--
-- Você deverá implementar uma função isEanOk :: String -> Bool, que 
-- verifique se uma dada string representa um número EAN-13 com dígito 
-- verificador válido.

import Data.Char

isEanOk :: String -> Bool
isEanOk str 
    | length str /= 13 = False
    | digitToInt (last str) == checkDigit (str2int str) = True
    | otherwise = False

-- > isEanOk "6291041500213"
-- True

checkDigit :: [Int] -> Int
checkDigit lst = 10 - (mod (sumEvenAndOdds lst) 10)

str2int :: String -> [Int]
str2int lst = map (\c -> digitToInt c) (init lst)

sumPosition :: [Int] -> (Int -> Bool) -> Int
sumPosition lst oddEven = sum (map fst (filter (\x -> oddEven (snd x)) (zip lst [1..])))
-- sumPosition lst oddEven = sum $ map fst $ filter (oddEven . snd) $ zip lst [1..]
-- (oddEven . snd) é (\x -> oddEven (snd x))
-- (f $ g $ h $ x) é (f (g (h x))) ou ((f . g . h) x)

sumEvenAndOdds :: [Int] -> Int
sumEvenAndOdds lst  = (sumPosition lst odd) + 3*(sumPosition lst even)
