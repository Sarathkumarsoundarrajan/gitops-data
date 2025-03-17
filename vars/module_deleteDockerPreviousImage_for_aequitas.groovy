#!/usr/bin/env groovy

def call() {
def process = "curl -si -u coherent:Coherent@5 -H 'Accept: application/vnd.docker.distribution.manifest.v2+json' -X HEAD 'https://registry.coherent.in/v2/aequitas-customer-service/manifests/640'".execute()
process.waitFor()
def output = process.text
println "$process"
def matcher = output =~ /docker-content-digest: (\S+)/
if (matcher) {
    println(matcher[0][1])
} else {
    println("Docker-Content-Digest not found in response headers.")
}
}
