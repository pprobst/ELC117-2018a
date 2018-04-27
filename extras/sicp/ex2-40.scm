; Exercise 2.40:
; Define a procedure unique-pairs that, given an integer n, generates
; the sequence of pairs (i, j) with 1 <= j < i <= n. Use unique-pairs
; to simplify the definition of prime-sum-pairs given above.

(define (enumerate-interval low high)
  (if (> low high)
      '()
      (cons low (enumerate-interval (+ low 1) high))))

(define (flatmap proc seq)
  (fold-right append '() (map proc seq)))

(define (prime? n)
  (define (F n i)
    (cond ((< n (* i i)) #t)
          ((zero? (remainder n i)) #f)
          (else (F n (+ i 1)))))
  (cond ((< n 2) #f)
    (else (F n 2))))

(define (prime-sum? pair)
  (prime? (+ (car pair) (cadr pair))))

(define (unique-pairs n)
  (flatmap
    (lambda (i)
      (map (lambda (j) (list i j))
           (enumerate-interval 1 (- i 1))))
    (enumerate-interval 1 n)))

(define (make-pair-sum pair)
  (list (car pair) (cadr pair) (+ (car pair) (cadr pair))))

(define (prime-sum-pairs n)
  (map make-pair-sum
       (filter prime-sum?
               (unique-pairs n))))

(prime-sum-pairs 5)
