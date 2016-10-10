;;; 8. Lambda the Ultimate
;(define rember-f
;  (lambda (test? a l)
;    (cond
;      ((null? l) (quote ()))
;      ((test? (car l) a) (cdr l))
;      (else (cons (car l)
;                  (rember-f test? a (cdr l)))))))

(define eq?-c
  (lambda (a)
    (lambda (x)
      (eq? x a))))

(define eq?-salad (eq?-c 'salad))

(define rember-f
  (lambda (test?)
    (lambda (a l)
      (cond
        ((null? l) (quote ()))
        ((test? (car l) a) (cdr l))
        (else (cons (car l)
                    ((rember-f test?) a
                     (cdr l))))))))
