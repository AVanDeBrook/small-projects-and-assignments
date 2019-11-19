; CS 332 - Programming Assignment 2 (functional)
; Date: 11/15/2019
; Name: Aaron Van De Brook
;
; To run: make sure the files are in the same directory (or that you know where the files are)
; Run it on the command line: racket main.rkt fsm.txt strings.txt
; E.g. in this ZIP file: racket main.rkt ..\example\fsm.txt ..\example\strings.txt
#lang racket

; Members of the FSM data structure
(define fsmStates '())
(define initState '())
(define finalStates '())
(define transitions '())

; Finite State Machine data structure. We assume and alphabet of {a, b}
(define FSM (list
    fsmStates
    initState
    finalStates
    transitions
))

; List of strings from the input file
(define strings '())

; Small function to test if a string read from a file is an end of file (EOF)
; character.
(define (testEOF str)
    (if (eq? str eof)
        #true
        #false
    )
)

; Reads all strings from an input file and adds them to the list of strings
; defined above.
(define (parseStrings fileBuffer)
    ; Temp variable for the current line
    (define currentLine (read-line fileBuffer))
    ; Add a string to the list if we're not at the end of a file
    (if (testEOF currentLine)
        (close-input-port fileBuffer)
        (set! strings (cons (string-trim currentLine) strings))
    )
    ; Recurse if we haven't hit the EOF character
    (if (testEOF currentLine)
        (close-input-port fileBuffer)
        (parseStrings fileBuffer)
    )
)

; Parse the transition table from the fsm definition
(define (parseTransitions fileBuffer)
    (define currentLine (read-line fileBuffer))
    ; Read table indices until EOF is read
    (if (testEOF currentLine)
        (close-input-port fileBuffer)
        (set! transitions (cons (string-split (string-replace (string-trim currentLine) " " "") #:trim? #t ",") transitions))
    )
    ; Recurse through rest of the file
    (if (testEOF currentLine)
        (close-input-port fileBuffer)
        (parseTransitions fileBuffer)
    )
)

; Set the FSM variables based on the definitions in the file
(define (parseFSM fileBuffer)
    (set! fsmStates (string-split (string-trim (string-replace (read-line fileBuffer) " " "")) #:trim? #t ","))
    (set! initState (string-trim (read-line fileBuffer)))
    (set! finalStates (string-split (string-trim (string-replace (read-line fileBuffer) " " "")) #:trim? #t ","))
    (parseTransitions fileBuffer)
    (set! transitions (reverse transitions))
    (set! FSM (list fsmStates initState finalStates transitions))
    (close-input-port fileBuffer)
)

; Wrapper function to parse the FSM and strings
(define (parse fsmFile stringFile)
    (parseFSM (open-input-file fsmFile))
    (displayln FSM)
    (parseStrings (open-input-file stringFile))
    (set! strings (reverse strings))
    (displayln strings)
)

; Process a string recursively, loops through processing one char at a time
; until the string is of length 0.
(define (processString string [currentState initState])
    (if (= (string-length string) 0)
        currentState
        (processString (substring string 1 (string-length string)) (getNextState currentState (string-ref string 0)))
    )
)

; Runs processString and determines whether a string is in the defined language
; or not.
(define (runStateMachine string)
    (define currentState initState)
    (set! currentState (processString string currentState))
    (if (member currentState finalStates)
        #true
        #false
    )
)

; Determines the next state based on the recieved symbol.
(define (getNextState state symbol)
    (if (char=? symbol #\a)
        (car (list-ref transitions (string->number state)))
        (car (cdr (list-ref transitions (string->number state))))
    )
)

; Wrapper function to run the entire program (to be called from main)
(define (runProgram args)
    (parse (vector-ref args 0) (vector-ref args 1))
    (for ([i strings])
        (if (runStateMachine i)
            (displayln (string-append i " : accept"))
            (displayln (string-append i " : reject"))
        )
    )
)

; main function, just to make it easier to call lately
(define (main [args (current-command-line-arguments)])
    (if (not (= (vector-length args) 2))
        (display "Run the program like so: racket main.rkt [fsm file] [string file]\nE.g. racket main.rkt fsm.txt strings.txt\n")
        (runProgram args)
    )
)

(main)