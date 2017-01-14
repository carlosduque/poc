;;; 6. Shadows
(define numbered?
  (lambda (aexp)
    (cond
      ((atom? aexp) (number? aexp))
      (else
        (and (numbered? (car aexp))
             (numbered?
               (car (cdr (cdr aexp)))))))))

(define 1st-sub-exp
  (lambda (aexp)
      (car (cdr aexp))))

(define 2nd-sub-exp
  (lambda (aexp)
    (car (cdr (cdr aexp)))))

(define operator
  (lambda (aexp)
    (car aexp)))

(define value
  (lambda (nexp)
    (cond
      ((atom? nexp) nexp)
      ((eq? (operator nexp) (quote +))
       (o+ (value (1st-sub-exp nexp))
           (value (2nd-sub-exp nexp))))
      ((eq? (operator nexp) (quote x))
       (ox (value (1st-sub-exp nexp))
           (value (2nd-sub-exp nexp))))
      (else
        (o^ (value (1st-sub-exp nexp))
            (value (2nd-sub-exp nexp)))))))
