#!/usr/bin/env groovy

def call(String env, List<String> envList) {
   return envList.contains(env)
}