; Exercise 2.60: 
; We specified that a set would be represented as a list
; with no duplicates. Now suppose we allow duplicates. For instance, the 
; set {1,2,3} could be represented as the list (2 3 2 1 3 2 2). 
; Design procedures element-of-set?, adjoin-set, union-set, and 
; intersection-set that operate on this representation. How does the 
; efficiency of each compare with the corresponding procedure for the 
; non-duplicate representation? Are there applications for which you would 
; use this representation in preference to the non-duplicate one?

(define (element-of-set? x set)
  (cond ((null? set) false)
        ((equal? x (car set)) true)
        (else (element-of-set? x (cdr set)))))

(define (adjoin-set x set)
  (cons x set))

(define (intersection-set set1 set2)
  (cond ((or (null? set1) (null? set2)) 
         '())
        ((element-of-set? (car set1) set2)
         (cons (car set1)
            (intersection-set (cdr set1) 
                                 set2)))
        (else (intersection-set (cdr set1) 
                                set2))))

(define (union-set set1 set2)
  (cond ((null? set1) set2)
        ((null? set2) set1)
        (else (cons (car set1) (union-set (cdr set1) set2)))))

; Tests
(define set1 (list 1 2 3 4 5))
(define set2 (list 2 5 6 8 9))
(intersection-set set1 set2)
(union-set set1 set2)

; union-set has gotten more efficient because we don't need to check if
; the element to be adjoined is already in the second set or not.
; Application: https://en.wikipedia.org/wiki/Multiset
; (I literally searched "sets with repeated elements application" on Startpage)
