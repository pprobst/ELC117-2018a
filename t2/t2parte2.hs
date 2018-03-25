import Data.Char

-- #1
-- Verifica se uma dada string representa um número binário ou não
isBin :: String -> Bool
isBin "" = False
isBin str
        | tail str == "" && headOneZero = True
        | headOneZero = isBin (tail str)
        | otherwise = False
    where 
        headOneZero = head str == '0' || head str == '1'

-- #2
-- Mesmo que acima; não recursiva
isBin' :: String -> Bool
isBin' str = all (\x -> x `elem` "01") str

-- #3
-- Implementar auxBin2Dec de forma recursiva, para que bin2dec converta
-- corretamente uma lista de 0s e 1s (binário) em seu valor inteiro
bin2dec :: [Int] -> Int
bin2dec [] = undefined
bin2dec bits = auxBin2Dec bits ((length bits)-1)

auxBin2Dec :: [Int] -> Int -> Int
auxBin2Dec _ (-1) = 0
auxBin2Dec (x:xs) exp = x*(2^exp) + auxBin2Dec xs (exp-1)

-- #4
-- Mesmo que #3; não recursiva
bin2dec' :: [Int] -> Int
bin2dec' [] = undefined
bin2dec' bits = foldl (\acc x -> acc*2 + x) 0 bits

-- #5
-- Recebe um inteiro positivo e retorna a sua representação em binário,
-- sob forma de uma lista de 0s e 1s. Funções auxiliares autorizadas: 
-- mod, div e reverse.
dec2bin :: Int -> [Int]
dec2bin 0 = []
dec2bin n
    | mod n 2 == 1 = dec2bin (div n 2) ++ [1]
    | mod n 2 == 0 = dec2bin (div n 2) ++ [0]


-- #6 
-- Verifica se uma dada string é hexadecimal ou não
isHex :: String -> Bool
isHex "" = False
isHex str = head str == '0' && 
            head (tail str) == 'x' && 
            all (\x -> x `elem` "ABCDEFabcdef0123456789") (tail (tail str))

-- isHex "0x1FA" ---> True
-- isHex "1FA"   ---> False
