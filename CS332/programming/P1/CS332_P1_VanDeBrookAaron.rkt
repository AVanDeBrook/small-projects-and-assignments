; CS 332 - Programming Assignment 1
; Name: Aaron Van De Brook
; Date: 09/30/2019

#lang racket
; Lists from the example on the PDF for testing
(define l1 '(3 12 9 5))
(define l2 '(7 7 12 2))
(define l3 '(8 11 12 9))
(define l4 '(12 5 16 3))
(define testList (list l1 l2 l3 l4))

; Tests for intersections between 2 given rectangles (list1 and list2).
(define (testIntersection list1 list2)
  (if (and
      ; r1 left side < r2 right side
      (<= (car list1) (car (cdr (cdr list2))))
      ; r1 right side > r2 left side
      (>= (car (cdr (cdr list1))) (car list2))
      ; r1 bottom side < r2 top side
      (<= (car (cdr (cdr (cdr list1)))) (car (cdr list2)))
      ; r1 top side > r2 bottom side
      (>= (car (cdr list1)) (car (cdr (cdr (cdr list2)))))
      )
      true
      false
      )
  )

; Finds which rectangles in a given list (lists) are intersecting with a
; given rectangle (key).
(define (getIntersections key lists)
  (if (= (length lists) 0)
      '()
      (if (testIntersection key (car lists))
          (append (list (car lists)) (getIntersections key (cdr lists)))
          (getIntersections key (cdr lists))
          )
      )
  )

; Recurses through the list of rectangles and outputs a list of intersecting
; rectangles for each corresponding rectangle in the list
(define (findIntersectionsRec lists [listCopy lists])
  (if (= (length lists) 0)
      '()
      (append (list (getIntersections (car lists) (remove (car lists) listCopy)))
              (findIntersectionsRec (remove (car lists) lists) listCopy))
      )
  )

; Test function call
(findIntersectionsRec testList)