#!/bin/bash

# Define the base folder
base_folder="projects"

# Prompt user for project name
read -p "Enter the project name: " project_name

# Prompt user for service name
read -p "Enter the service name: " service_name

# Prompt user for environment name
read -p "Enter the environment name: " environment_name

# Construct folder paths
project_path="$base_folder/$project_name"
service_path="$base_folder/$project_name/$service_name"
project_service_path="$project_name/$service_name"
config_path="$service_path/config"
argo_application_config_path="$service_path/argo-application-config"
argo_application_name="$project_name-$service_name-$environment_name-application"
argo_application_config_file="$service_path/argo-application-config/$argo_application_name.yaml"
k8s_config_path="$service_path/k8s-config"
persistent_volumes_path="$service_path/persistent-volumes"

k8s_environment_path="$k8s_config_path/$environment_name"
k8s_deployment_file="$k8s_environment_path/deployment.yaml"
k8s_ingress_file="$k8s_environment_path/ingress.yaml"
k8s_service_file="$k8s_environment_path/service.yaml"
config_docker_file="$config_path/Dockerfile"
config_nginx_config_file="$config_path/nginx.conf"
service_jenkins_file="$service_path/Jenkinsfile"

argo_project_path="$service_path/k8s-config/$environment_name"

fileNotExists() {
    local file="$1"

    # Check if the file does not exist
    if [ ! -e "$file" ]; then
        return 0  # Return 0 for true (file does not exist)
    else
        return 1  # Return 1 for false (file exists)
    fi
}

create_files() {
     # Create additional folders and files
        mkdir -p "$config_path" "$k8s_config_path" "$argo_application_config_path" "$persistent_volumes_path" "$k8s_environment_path"
        touch "$k8s_deployment_file" "$k8s_ingress_file" "$k8s_service_file" "$config_docker_file"
        cp common/argo-application-template.yaml "$argo_application_config_file"
        sed -i '' "s/APPLICATION_NAME_HERE/$argo_application_name/g" "$argo_application_config_file"
        echo $argo_project_path
        sed -i '' "s|PROJECT_PATH_HERE|$argo_project_path|g" "$argo_application_config_file"
        
        if fileNotExists "$service_jenkins_file"; then
            cp common/Jenkinsfile "$service_jenkins_file"
            sed -i '' "s|PROJECT_SERVICE_PATH_HERE|$project_service_path|g" "$service_jenkins_file"
        else
            echo "$service_jenkins_file exists."
        fi
        # Check if additional folders and files creation was successful
        if [ $? -eq 0 ]; then
            echo "Additional folders and files created successfully."
        else
            echo "Error creating additional folders and files."
        fi
}

# Check if project folder already exists
if [ -d "$project_path" ]; then
    echo "Project folder '$project_path' already exists."
      create_files  
       
else
    # Create the project folder
    mkdir -p "$project_path"
    
    # Check if project folder creation was successful
    if [ $? -eq 0 ]; then
        echo "Project folder '$project_path' created successfully."
        
        create_files
    else
        echo "Error creating project folder '$project_path'."
    fi
fi
