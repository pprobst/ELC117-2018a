import Data.Char

-- Converte um caracter em um inteiro
encodeChar :: Char -> Int
encodeChar c = ord c - ord 'a'

-- Converte um inteiro em um caracter
decodeChar :: Int -> Char
decodeChar n = chr (ord 'a' + n)

-- #1
-- Recebe um caracter de 'a' a 'z' e aplica um deslocamento de 'n' unidades 
-- sobre ele, produzindo outro caractere no intervalo ['a'..'z']
shiftChar :: Char -> Int -> Char
shiftChar ch n 
    | isLower ch = decodeChar (mod (encodeChar ch + n) 26)
    | otherwise = ch

-- #2
-- Codifica uma string usando um dado deslocamento; usa shiftChar
encodeStr :: String -> Int -> String
encodeStr str n = map (\ch -> shiftChar ch n) str

-- #3
-- Recebe uma string e retorna a quantidade de seus caracteres contidos no 
-- intervalo ['a'..'z']
--
-- obs.: quis resolver usando recursão só para treinar um pouco; claro que com
-- filter (ver #4) fica bem mais simples
countValids :: String -> Int
countValids str = countAux str 0
    where
        countAux [] valids = valids
        countAux (x:xs) valids
            | isLower x = countAux xs (valids+1)
            | otherwise = countAux xs valids

-- #4
-- Retorna a quantidade de um dado caractere em uma string
countChar :: Char -> String -> Int
countChar ch str = length (filter (\c -> c == ch) str)

-- #5
-- Usando countValids, countChar e percent, retorna as frequências dos caracteres 
-- ['a'..'z'] numa dada string. Com list comprehension!. A frequência de um 
-- caracter é dada pelo percentual deste caracter entre os caracteres válidos 
-- da string. 
freqs :: String -> [Float]
freqs str = [percentValChar ch str | ch <- ['a'..'z']]
    where percentValChar ch str = percent (countChar ch str) (countValids str)

-- Calcula percentagem: n/m*100
percent :: Int -> Int -> Float
percent n m = (fromIntegral n / fromIntegral m)*100

-- #6
-- Retorna uma lista de posições de um dado número em uma lista
positions :: Float -> [Float] -> [Int]
positions n lst = map snd (filter (\x -> fst x == n) (zip lst [0..]))

-- Rotacao de uma lista para esquerda em n posicoes
rotate :: Int -> [a] -> [a]
rotate n xs = drop n xs ++ take n xs

-- Tabela de frequencias das letras 'a'..'z' (lingua portuguesa)
-- https://pt.wikipedia.org/wiki/Frequ%C3%AAncia_de_letras
table :: [Float]
table = [14.6, 1.0, 3.9, 5.0, 12.6, 1.0, 1.3, 1.3, 6.2, 0.4, 0.1, 2.8, 4.7, 
         5.0, 10.7, 2.5, 1.2, 6.5, 7.8, 4.3, 4.6, 1.7, 0.1, 0.2, 0.1, 0.5]

-- Distancia entre 2 listas de frequencia
chi2 :: [Float] -> [Float] -> Float
chi2 os es = sum [((o-e)^2)/e | (o,e) <- zip os es]

-- Use esta funcao para decodificar uma mensagem!
crack :: String -> String
crack cs = encodeStr cs (-factor)
           where factor = head (positions (minimum chitab) chitab)
                 chitab = [ chi2 (rotate n table' ) table | n <- [0..25] ]
                 table' = freqs cs
