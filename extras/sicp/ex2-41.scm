; Exercise 2.41:
; Write a procedure to find all ordered triples of distinct positive integers
; i, j and k less than or equal to a given integer n that sum to a given 
; integer s.

; Aux
(define (enumerate-interval low high)
  (if (> low high)
      '()
      (cons low (enumerate-interval (+ low 1) high))))

(define (flatmap proc seq)
  (fold-right append '() (map proc seq)))

(define (unique-pairs n)
  (flatmap
    (lambda (i)
      (map (lambda (j) (list i j))
           (enumerate-interval 1 (- i 1))))
    (enumerate-interval 1 n)))

; Main
(define (ordered-triples n)
  (flatmap 
    (lambda (i) 
      (map (lambda (j) 
             (cons i j))
           (unique-pairs (- i 1))))
    (enumerate-interval 1 n)))

(define (triples-sum sum n)
  (filter (lambda (x)
            (= sum (fold-right + 0 x)))
          (ordered-triples n)))

(triples-sum 8 4)

