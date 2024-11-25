﻿Banking Application
This project is a sample Banking Application deployed in a Kubernetes cluster, which consists of several services including a Zookeeper, Kafka, MongoDB, and the Banking App. The Banking App interacts with Kafka for messaging and MongoDB for data storage. The application is containerized and deployed using Terraform with Kubernetes resources.

Prerequisites
Before running the Banking App, ensure you have the following tools and services set up:

Kubernetes Cluster: You should have a running Kubernetes cluster (e.g., Minikube).
Terraform: This project uses Terraform to manage Kubernetes resources.
Docker: Docker is used for containerizing the Banking App and other services (Zookeeper, Kafka, MongoDB).
Helm (Optional): For deploying dependencies like Zookeeper and Kafka, if not using the provided Docker images.
Kafka and Zookeeper: Kafka is used for messaging between services, and Zookeeper is used as the coordinator for Kafka.
MongoDB: MongoDB is used for storing banking data.
Project Structure
graphql
Copy code
.
├── main.tf               # Root Terraform configuration to deploy Kubernetes resources
├── modules
│   ├── zookeeper         # Module for Zookeeper deployment
│   ├── kafka             # Module for Kafka deployment
│   ├── banking_app       # Module for Banking App deployment
│   └── mongo             # Module for MongoDB deployment
├── terraform.tfvars      # Variables for Terraform
└── README.md             # Project documentation
Setup Instructions
1. Clone the Repository
Start by cloning the repository:

bash
Copy code
git clone https://github.com/yourusername/banking-app.git
cd banking-app
2. Set Up Kubernetes Cluster
Ensure you have a Kubernetes cluster running. If you're using Minikube, you can start it with:

bash
Copy code
minikube start
3. Configure Terraform
You need to have your Kubernetes configuration file (kubeconfig) in place. Make sure your kubeconfig file is located at the default path ~/.kube/config or specify the path in the main.tf file.

hcl
Copy code
provider "kubernetes" {
  config_path = "C:\\Users\\admin\\.kube\\config"
  config_context = "minikube"
}
4. Define Variables
Update the terraform.tfvars file to provide necessary values like the Docker image for the Banking App:

hcl
Copy code
banking_app_image = "your-docker-image-here"
5. Apply Terraform Configuration
Run the following Terraform commands to deploy the resources to your Kubernetes cluster:

bash
Copy code
terraform init
terraform apply
Confirm the action by typing yes when prompted. This will create the following Kubernetes resources:

Zookeeper (used by Kafka)
Kafka (for messaging between services)
MongoDB (for banking data)
Banking App (main application service)
6. Verify Deployments
Check if the pods are running successfully:

bash
Copy code
kubectl get pods
You should see the following pods running:

zookeeper
kafka
mongo
banking-app
7. Access the Banking App
Expose the Banking App to your local machine by creating a service with port forwarding:

bash
Copy code
kubectl port-forward svc/banking-app 8089:8089
Now, you should be able to access the Banking App at http://localhost:8089.

Services
Zookeeper Service
Zookeeper is used for managing the Kafka cluster. It's exposed as a Kubernetes service on port 2181.

Kafka Service
Kafka is used for messaging between different components. It is exposed as a Kubernetes service on port 9092.

MongoDB Service
MongoDB is used to store banking data and is exposed as a Kubernetes service on port 27017.

Banking App
The Banking App is the main application service, which is exposed on port 8089 and communicates with Kafka for message consumption and production, and MongoDB for data persistence.

Notes
Make sure that the Docker images for Kafka, Zookeeper, MongoDB, and Banking App are available in your Docker registry or pull the latest ones from the respective Docker repositories.
The application is configured to use Spring Boot for the backend. Ensure that your Spring Boot configuration correctly references the Kafka and MongoDB services.
Troubleshooting
If the pods are not starting, check the logs:

bash
Copy code
kubectl logs <pod-name>
If the application can't connect to Kafka or MongoDB, ensure that the services are up and running and that the application has the correct environment variables configured.
