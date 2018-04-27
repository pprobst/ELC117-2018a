; Exercise 2.34:
; Horner's rule: 
; AnX^n + A(n-1)X^(n-1) + ... + A1X + A0
; In other words, we start with An, multiply by X, add A(n-1),
; multiply by X, and so on, until we reach A0.

; Fill in the following template to produce a procedure that
; evaluates a polynomial using Horner's rule. Assume that the
; coefficients of the polynomial are arranged in a sequence,
; from A0 through An.
; Example: to compute 1 + 3X + 5X^3 + X^5 at X = 2 you would evaluate
; (horner-eval 2 (list 1 3 0 5 0 1))

(define (horner-eval x coefficient-sequence)
  (accumulate (lambda (this-coeff higher-terms) 
                (+ this-coeff (* higher-terms)))
              0
              coefficient-sequence))

(define (accumulate op initial sequence)
  (if (null? sequence)
      initial
      (op (car sequence)
          (accumulate op initial (cdr sequence)))))

(horner-eval 2 (list 1 3 0 5 0 1))
