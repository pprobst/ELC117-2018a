; Exercise 2.35:
; Redefine count-leaves from Section 2.2.2 as an accumulation:

(define t (cons (list 1 2) (list 3 4)))

(define (accumulate op initial sequence)
  (if (null? sequence)
      initial
      (op (car sequence)
          (accumulate op initial (cdr sequence)))))

; Old
(define (count-leaves-orig t)
  (cond ((null? t) 0)
        ((not (pair? t)) 1)
        (else (+ (count-leaves-orig (car t))
                 (count-leaves-orig (cdr t))))))

(count-leaves-orig (list t t)) ; 8

; New
(define (count-leaves t)
  (accumulate 
    + 0 (map (lambda (x)
             (if (pair? x)
                 (count-leaves x)
                 1))
             t)))

; Test
(count-leaves (list t t)) ; 8
