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
  
