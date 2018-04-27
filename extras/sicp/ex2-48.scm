; Exercise 2.48:
; Use your vector representation to define a representation for segments
; with a constructor make-segment and selectors start-segment
; and end-segment.

; Constructor and selectors
(define (make-vect x y)
  (cons x y))

(define (xcor-vect v)
  (car v))

(define (ycor-vect v)
  (cdr v))

; Arithmetic ops
(define (add-vect v1 v2)
  (make-vect 
    (+ (xcor-vect v1) (xcor-vect v2)) 
    (+ (ycor-vect v1) (ycor-vect v2))))

(define (sub-vect v1 v2)
  (make-vect 
    (- (xcor-vect v1) (xcor-vect v2)) 
    (- (ycor-vect v1) (ycor-vect v2))))

(define (scale-vect v scalar)
  (make-vect
    (* (xcor-vect v) scalar)
    (* (ycor-vect v) scalar)))

; Segments
(define (make-segment v1 v2)
  (cons v1 v2))

(define (start-segment v)
  (car v))

(define (end-segment v)
  (cdr v))

; Tests
(define seg (make-segment (make-vect 1 1) (make-vect 6 6)))
(start-segment seg)
(end-segment seg)
