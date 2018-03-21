-- #1
-- Produz uma lista com n tuplas, cada tupla com números de n a 1 e seus 
-- respectivos quadrados:
geraTabela :: Int -> [(Int,Int)]
geraTabela 1 = (1,1) : []
geraTabela n = (n,n*n) : geraTabela (n-1)

-- #2
-- Verifica se o caractere passado como parâmetro está contido na string 
-- igualmente passada como parämetro:
contido :: Char -> String -> Bool
contido ch "" = False
contido ch (x:xs) 
    | ch == x = True 
    | otherwise = contido ch xs

-- #3
-- Recebe uma lista de coordenadas de pontos 2D e desloca esses pontos em 
-- 2 unidades:
translate :: [(Float,Float)] -> [(Float,Float)]
translate [] = []
translate (x:xs) = (\(x, y) -> (x+2, y+2)) x : translate xs
-- É praticamente um map. O mesmo poderia ser resolvido com:
-- map (\(x,y) -> (x+2,y+2)) lista

-- #4
-- Mesmo que #1, mas em ordem contrária:
