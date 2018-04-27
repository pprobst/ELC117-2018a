; Exercise 2.39:
; Complete the following definitions of reverse (Exercise 2.18) in terms of 
; fold-right and fold-left from Exercise 2.38.

(define (muh-reverse1 sequence)
  (fold-right (lambda (x y) (append y (list x))) '() sequence))

(define (muh-reverse2 sequence)
  (fold-left (lambda (x y) (cons y x)) '() sequence))


(muh-reverse1 (list 1 2 3 4 5))
(muh-reverse2 (list 1 2 3 4 5))
