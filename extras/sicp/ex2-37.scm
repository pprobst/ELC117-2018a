; Exercise 2.37:
;
; | 1  2  3  4 |
; | 4  5  6  6 | ---> Matrix
; | 6  7  8  9 |
;
; represented as ((1 2 3 4) (4 5 6 6) (6 7 8 9)).


;;; Main procedures ;;; 

(define (dot-product v w)
  (accumulate + 0 (map * v w)))

; Fill in the missing:

 (define (matrix-*-vector m v) 
   (map 
     (lambda (r) (dot-product r v)) 
     m))

(define (transpose mat)
  (accumulate-n cons '() mat))

(define (matrix-*-matrix m n)
  (let ((cols (transpose n)))
    (map 
      (lambda (r) 
        (matrix-*-vector cols r)) m)))

;;; Auxiliary procedures ;;; 

(define (accumulate op initial sequence)
  (if (null? sequence)
      initial
      (op (car sequence)
          (accumulate op initial (cdr sequence)))))

(define (accumulate-n op init seqs)
  (if (null? (car seqs))
      '()
      (cons (accumulate op init (map car seqs))
            (accumulate-n op init (map cdr seqs)))))


;;; Tests ;;;
(define matrix (list (list 1 2 3 4) (list 4 5 6 6) (list 6 7 8 9)))
(define matrix2 (list (list 4 5 6 7) (list 10 2 3 1) (list 6 1 3 1) (list 1 2 3 4)))

(dot-product (list 1 2 3) (list 10 10 10))
(matrix-*-vector matrix (list 1 2 3 4))
(transpose matrix)
(matrix-*-matrix matrix matrix2)
