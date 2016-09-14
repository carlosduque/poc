(define grade
  (lambda (score)
    (cond
      ((< score 40) "D")
      ((<= score 40 59) "C")
      ((<= score 60 79) "B")
      ((>= score 80) "A")
    )
  )
)
