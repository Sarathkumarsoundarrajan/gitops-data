#!/usr/bin/env groovy

def call(String gitUrl) {
   // Define a regular expression pattern to match the project name in the Git URL
    def pattern = ~/\/([^\/]+)\.git$/
    
    // Use the pattern to find the project name in the Git URL
    def matcher = (gitUrl =~ pattern)
    
    // Check if a match is found
    if (matcher.find()) {
        // Return the captured group (project name)
        return matcher[0][1]
    } else {
        // If no match is found, return null or handle the case accordingly
        return null
    }
}