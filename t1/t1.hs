--------------------------
-- ELC117-2018a         --
-- T1                   --
-- PEDRO PROBST MININI  --
--------------------------

import Data.Char 

-- #1
-- Verifica se um caractere é vogal ou não:
isVowel :: Char -> Bool
isVowel ch = elem ch "aeiouAEIOU" -- strings são listas de chars
-- Obs.: 'elem' retorna True se o primeiro argumento contém algo da
-- lista tomada como segundo argumento.

-- Modo menos elegante de fazer a mesma coisa: 
isVowelSpartan :: Char -> Bool
isVowelSpartan ch = ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
 -- usarei 'elem' quando precisar disso em outras funções!
 
-- #2
-- Adiciona uma vírgula no final de cada string contida na lista
addComma :: [String] -> [String]
addComma lst = map (++ ",") lst

-- #3
-- Recebe uma lista de strings e retorne outra lista contendo as strings 
-- formatadas como itens de lista em HTML
htmlListItems :: [String] -> [String]
htmlListItems lst = map (concatStartEnd) lst

htmlListItemsLambda :: [String] -> [String]
htmlListItemsLambda lst = map (\x -> "<LI>" ++ x ++ "</LI>") lst

concatStartEnd :: String -> String
concatStartEnd str = "<LI>" ++ str ++ "</LI>"

-- #4
-- Recebe uma string e retira as vogais dela.
semVogais :: String -> String
semVogais str = filter (`notElem` "aeiouAEIOU") str

semVogais2 :: String -> String
semVogais2 str = filter (\c -> not (isVowel c)) str

semVogais3 :: String -> String
semVogais3 str = filter (\c -> notElem c "aeiouAEIOU") str

-- #5
-- Recebe uma string e substitui os caracteres por '-', mantendo espaços.
codifica :: String -> String
codifica str = map (\c -> if isAlphaNum c then '-' else c) str

-- #6
-- Recebe uma string com o nome completo de alguém e retorna o primeiro nome.
firstName :: String -> String
firstName str = head (words str)
-- 'words' separa a string em uma lista de strings, separadas pelo whitespace
-- 'head' pega o primeiro elemento da lista

-- #7
-- Verifica se uma string só contém dígitos de 0 a 9.
isInt :: String -> Bool
isInt = all isDigit

isInt2 :: String -> Bool
isInt2 str = length (filter (isDigit) str) == length str

-- #8
-- Recebe uma string com o nome completo de alguém e pega seu último nome.
lastName :: String -> String
lastName str = last (words str)

-- #9
-- Recebe a string com o nome completo de alguém cria um username:
-- Ex: userName "Linus Benedict Torvalds" --> "ltorvalds"
userName :: String -> String
userName str = toLower(head (firstName str)) 
               : 
               map (\c -> toLower c) (lastName str)
-- aqui usou-se : ao invés de ++, pois o resultado de (head (firstName str))
-- é um char, e ++ só funciona entre listas.

-- #10
-- Substitui vogais em uma string conforme o esquema abaixo:
-- a = 4, e = 3, i = 2, o = 1, u = 0
encodeName :: String -> String
encodeName str = map (\c -> encodeValues c) str

encodeValues :: Char -> Char
encodeValues ch
    | ch == 'a' || ch == 'A' = '4'
    | ch == 'e' || ch == 'E' = '3'
    | ch == 'i' || ch == 'I' = '2'
    | ch == 'o' || ch == 'O' = '1'
    | ch == 'u' || ch == 'U' = '0'
    | otherwise = ch

-- #11
-- Mesmo que #10, mas com esquema diferente:
-- a = 4, e = 3, i = 1, o = 0, u = 00
betterEncode :: String -> String
betterEncode str = concatMap (\c -> if c == '0' then "00"
                                    else if c == '1' then "0"
                                    else if c == '2' then "1"
                                    else [c])
                   (encodeName str)

-- #12
-- Recebe uma lista de strings e produz outra lista com strings de 10
-- caracteres usando o seguinte esquema:
-- strings de entrada com mais de 10 caracteres são truncadas, strings 
-- com até 10 caracteres são completadas com '.' até ficarem com 10 caracteres.
func :: [String] -> [String]
func lst = map (\s -> if length s == 10 then s
                      else if length s > 10 then take 10 s 
                      else addDots s 10) lst

addDots :: String -> Int -> String
addDots str size
    | length str >= size = str
    | otherwise = str ++ (replicate (size-length str) '.')
