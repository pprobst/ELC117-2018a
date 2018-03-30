--------------------------
-- ELC117-2018a         --
-- T2 - PARTE 1         --
-- PEDRO PROBST MININI  --
--------------------------

-- #1
-- Produz uma lista com n tuplas, cada tupla com números de n a 1 e seus 
-- respectivos quadrados:
--
geraTabela :: Int -> [(Int,Int)]
geraTabela 1 = (1,1) : []
geraTabela n = (n,n*n) : geraTabela (n-1)

-- #2
-- Verifica se o caractere passado como parâmetro está contido na string 
-- igualmente passada como parämetro:
--
contido :: Char -> String -> Bool
contido ch "" = False
contido ch (x:xs) 
    | ch == x = True 
    | otherwise = contido ch xs

-- #3
-- Recebe uma lista de coordenadas de pontos 2D e desloca esses pontos em 
-- 2 unidades:
--
translate :: [(Float,Float)] -> [(Float,Float)]
translate [] = []
translate (x:xs) = (\(a, b) -> (a+2, b+2)) x : translate xs
-- É praticamente um map. O mesmo poderia ser resolvido com:
-- map (\(a,b) -> (a+2,b+2)) lista

-- #4
-- Mesmo que #1, mas em ordem contrária:
--
-- Modo mais "Scheme-like":
geraTabela' :: Int -> [(Int,Int)]
geraTabela' 1 = (1,1) : []
geraTabela' n = fazMagica (n-(n-1)) n

fazMagica :: Int -> Int -> [(Int,Int)]
fazMagica n 0 = []
fazMagica n cont = (n,n*n) : (fazMagica (n+1) (cont-1))

-- Modo tosquinho:
geraTabela3 :: Int -> [(Int,Int)]
geraTabela3 1 = (1,1) : []
geraTabela3 n = reverse((n,n*n) : geraTabela (n-1))
