#!/usr/bin/env groovy

def call(String value) {
   def intValue = value.toInteger() - 2
    return intValue.toString()
}