def call() { 
    sh """
    python3 --version
    python3 -m pip install --upgrade pip
    python3 -m pip install -r ./AI_POC/Chatbot/requirements.txt
    """
}
