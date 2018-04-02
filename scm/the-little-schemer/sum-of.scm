(define sum-of 
  (lambda (numlist)
    (if (null? numlist)
        0
        (+ (car numlist)
           (sum-of (cdr numlist))))))

