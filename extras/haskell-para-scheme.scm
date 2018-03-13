(define (somaQuad x y)
  (+ (square x) (square y)))

(somaQuad 2 3) ; 13

;;;;;;;

(define (hasEqHeads lst1 lst2)
  (if (= (car lst1) (car lst2))
	  #t
	  #f))

(hasEqHeads (list 1 2 3) (list 1 5 6)) ; #t
(hasEqHeads (list 4 2 3) (list 1 5 6)) ; #f

;;;;;;;

(define (addSuper lst)
  (map
    (lambda (x) 
      (string-append "Super" x))
    lst))

(addSuper (list "Famicom" "Nintendo")) ; ("SuperFamicom" "SuperNintendo")

;;;;;;;

(define (countSpace str)
  (count (lambda (x) (equal? x #\space)) 
         (string->list str)))

(countSpace "Nintendo Entertainment System") ; 2

;;;;;;;

(define (calcList lst)
  (map (lambda (n) (+ (* 3 (square n) (/ 2 n) 1))) lst))

(calcList (list 1 2 3 4))

;;;;;;;

(define (selectNegative lst)
  (filter negative? lst))

(selectNegative (list 1 2 -3 -4 5)) ; (-3 -4)

;;;;;;;

(define (bet-1-100 lst)
  (filter 
    (lambda (x) (and (> x 1) (< x 100)))
    lst))

(bet-1-100 (list 1 2 3 101 -2)) ; (2 3)

;;;;;;;

(define (ageAfter1980 lst)
  (filter 
    (lambda (x) (< (- 2018 x) 1980))
    lst))

(ageAfter1980 (list 28 39 30 15 50)) ; (39 50)

;;;;;;;

(define (onlyEven lst)
  (filter even? lst))

(onlyEven (list 1 2 3 4 5 6)) ; (2 4 6)

;;;;;;;

; Retorna o índice da primeira ocorrência do char;
; #f caso não encontrá-lo:
(define (charFound str ch)
  (string-find-next-char str ch))

(charFound "Pedro" #\d) ; 2
(charFound "Pedro" #\a) ; #f

; Versão "espartana" do procedimento visto acima, mas
; retornando apenas #t ou #f:
(define (charFoundSpartan str char)
  (cond ((null? str) #f)
    ((char=? char (car str)) #t)
    (else (charFoundSpartan (cdr str) char))))

(charFoundSpartan (string->list "Pedro") #\d) ; #t
(charFoundSpartan (string->list "Pedro") #\a) ; #f

;;;;;;;

(define (endsWithA lst)
  (filter 
	(lambda (x)
	   (equal? (string-ref x (- (string-length x) 1)) #\a))
	lst))

(endsWithA (list "Angela" "Pedro" "Andrea" "Edgar")) ; ("Angela" "Andrea")
