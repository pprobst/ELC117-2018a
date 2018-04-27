; Exercise 2.33:
; Fill in the missing expressions to complete the following definitions
; of some basic list-manipulation operations as accumulations.

; (define (map p sequence)
;   (accumulate (lambda (x y) <??>) nil sequence))
; (define (append seq1 seq2)
;   (accumulate cons <??> <??>))
; (define (length sequence)
;   (accumulate <??> 0 sequence))

; obs.: nil = '()

; The 'accumulate' function from the book
(define (accumulate op initial sequence)
  (if (null? sequence)
      initial
      (op (car sequence)
          (accumulate op initial (cdr sequence)))))

; Solutions
(define (muh-map p sequence)
  (accumulate (lambda (x y) (cons (p x) y)) '() sequence))

(define (muh-append seq1 seq2)
  (accumulate cons seq2 seq1))

(define (muh-length sequence)
  (accumulate (lambda (x y) (+ y 1)) 0 sequence))

; Tests
(muh-map square (list 1 2 3 4))
(muh-append (list 1 2) (list 3 4))
(muh-length (list 1 2 3 4))
