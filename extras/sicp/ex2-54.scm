; Exercise 2.54:
; Implement 'equal' as a procedure.
; (equal? '(this is a list) '(this is a list)) --> #t
; (equal? '(this is a list) '(this (is a) list)) --> #f

(define (muh-equal? a b)
  (cond ((and (null? a) (null? b)) #t)
        ((or (null? a) (null? b)) #f)
        ((and (pair? a) (pair? b)
              (muh-equal? (car a) (car b))
              (muh-equal? (cdr a) (cdr b))))
        (else (eq? a b))))

(muh-equal? '(1 2 3) '(1 2 3))
(muh-equal? '(7 6) '(7 6))
(muh-equal? (list 7 6) (list 7 6))
(muh-equal? '(1 4 5 (7 6)) '(1 4 5 (7 6)))
(muh-equal? '(1 4 5 (7 6 5)) '(1 4 5 (7 6)))
(muh-equal? '(1 4 5 (7 6 5) 3 4 (1 1) (2 2)) '(1 4 5 (7 6 5) 3 4 (1 1) (2 2)))
(muh-equal? '() '())
(muh-equal? '() '(2 1))
