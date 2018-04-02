;;; 8. Lambda the Ultimate

(define eq?-c
  (lambda (a)
    (lambda (x)
      (eq? x a))))

(define eq?-salad (eq?-c 'salad))

;(define rember-f
;  (lambda (test? a l)
;    (cond
;      ((null? l) (quote ()))
;      ((test? (car l) a) (cdr l))
;      (else (cons (car l)
;                  (rember-f test? a (cdr l)))))))

;(define rember-eq? (rember-f eq?))

(define rember-f
  (lambda (test?)
    (lambda (a l)
      (cond
        ((null? l) (quote ()))
        ((test? (car l) a) (cdr l))
        (else (cons (car l)
                    ((rember-f test?) a (cdr l))))))))

(define seqL
  (lambda (new old l)
    (cons new (cons old l))))

(define seqR
  (lambda (new old l)
    (cons old (cons new l))))

(define insert-g
  (lambda (seq)
    (lambda (new old l)
      (cond
        ((null? l) (quote ()))
        ((eq? (car l) old)
         (seq new old (cdr l)))
        (else (cons (car l)
                    ((insert-g seq) new old (cdr l))))))))

;;;pass the definition instead of seqL
(define insertL
  (insert-g
    (lambda (new old l)
      (cons new (cons old l)))))

(define insertR (insert-g seqR))

(define insertL-f
  (lambda (test?)
    (lambda (new old l)
      (cond
        ((null? l) (quote ()))
        ((test? (car l) old)
         (cons new (cons old (cdr l))))
        (else (cons (car l)
                    ((insertL-f test?) new old (cdr l))))))))

(define seqS
  (lambda (new old l)
    (cons new l)))

(define subst (insert-g seqS))

(load "tls4.scm")
(define atom-to-function
  (lambda (x)
    (cond
      ((eq? x (quote +)) o+)
      ((eq? x (quote x)) ox)
      (else o^))))

(load "tls6.scm")
(define value
  (lambda (nexp)
    (cond
      ((atom? nexp) nexp)
      (else
        ((atom-to-function (operator nexp))
         (value (1st-sub-exp nexp))
         (value (2nd-sub-exp nexp)))))))

(define multirember-f
  (lambda (test?)
    (lambda (a lat)
      (cond
        ((null? lat) (quote ()))
        ((test? a (car lat))
         ((multirember-f test?) a (cdr lat)))
        (else (cons (car lat)
                    ((multirember-f test?) a (cdr lat))))))))

(define eq?-tuna (eq?-c (quote tuna)))

(define multirembert
  (lambda (test? lat)
    (cond
      ((null? lat) (quote ()))
      ((test? (car lat))
       (multirembert test? (cdr lat)))
      (else (cons (car lat)
                  (multirembert test? (cdr lat)))))))

(define multirember&co
  (lambda (a lat col)
    (cond
      ((null? lat)
       (col (quote ()) (quote ())))
      ((eq? (car lat) a)
       (multirember&co a (cdr lat) (lambda (new lat seen)
                                     (col newlat
                                          (cons (car lat) seen)))))
      (else
        (multirember&co a (cdr lat) (lambda (newlat seen)
                                      (col (cons (car lat) newlat)
                                           seen)))))))
(define a-friend
  (lambda (x y)
         (null? y)))

(define new-friend
  (lambda (newlat seen)
    (col newlat
         (cons (car lat) seen))))

(define latest-friend
  (lambda (newlat seen)
    (a-friend (cons (quote and) newlat) seen)))

(define last-friend
  (lambda (x y)
    (length x)))

