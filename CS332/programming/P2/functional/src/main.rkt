; CS 332 - Programming Assignment 2 (functional)
; Date: 11/15/2019
; Name: Aaron Van De Brook
;
(define fsm_states '())
(define initState '())
(define finalStates '())
(define transitions (list '() '()))

(define FSM (list
    fsm_states
    initState
    finalStates
    transitions
))

(define strings '())

(define (parseStrings inputFile)
    (if (symbol 'EOF)
        (append strings (read-line inputFile))
        (append strings '())
    )
)
