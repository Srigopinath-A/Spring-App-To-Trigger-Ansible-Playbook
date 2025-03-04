# Spring Application Setup and CronJob Configuration

## 1. Create `UserInput` and `UserService` Classes

### `UserInput` Class
Create a class named `UserInput` to handle user input.

```java name=UserInput.java
package com.example.app;

public class UserInput {
    private String name;
    private String email;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

### `UserService` Class
Create a service class named `UserService` to handle business logic related to users.

```java name=UserService.java
package com.example.app;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void processUser(UserInput userInput) {
        // Business logic to process user input
        System.out.println("Processing user: " + userInput.getName() + ", " + userInput.getEmail());
    }
}
```

## 2. Create Other Services (Example)

### `EmailService` Class
Create a service class named `EmailService` to handle email-related operations.

```java name=EmailService.java
package com.example.app;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // Logic to send email
        System.out.println("Sending email to: " + to + ", Subject: " + subject);
    }
}
```

## 3. Add `Schu` File

### `Crontask` Class
Create a class named `Crontask` to handle scheduled tasks.

```java name=Crontask.java
package com.example.app;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Crontask {

    @Scheduled(cron = "0 */5 * * * *")  // Runs every 5 minutes
    public void Jobtriggre() {
        // Trigger the Ansible playbook
        try {
            Process process = Runtime.getRuntime().exec("ansible-playbook /path/to/your/playbook.yml");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

## 4. Creating a CronJob Using Docker

### Step 1: Install Docker Engine

Follow these steps to install Docker Engine on your system.

#### For Ubuntu:

1. **Update the apt package index**:
   ```sh
   sudo apt-get update
   ```

2. **Install packages to allow apt to use a repository over HTTPS**:
   ```sh
   sudo apt-get install apt-transport-https ca-certificates curl gnupg lsb-release
   ```

3. **Add Docker’s official GPG key**:
   ```sh
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
   ```

4. **Set up the stable repository**:
   ```sh
   echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   ```

5. **Install Docker Engine**:
   ```sh
   sudo apt-get update
   sudo apt-get install docker-ce docker-ce-cli containerd.io
   ```

6. **Verify that Docker Engine is installed correctly**:
   ```sh
   sudo docker run hello-world
   ```

### Step 2: Install Docker Buildx

1. **Download the latest buildx release**:
   ```sh
   mkdir -p ~/.docker/cli-plugins/
   curl -Lo ~/.docker/cli-plugins/docker-buildx https://github.com/docker/buildx/releases/latest/download/docker-buildx-linux-amd64
   ```

2. **Make the plugin executable**:
   ```sh
   chmod +x ~/.docker/cli-plugins/docker-buildx
   ```

3. **Verify installation**:
   ```sh
   docker buildx version
   ```

### Step 3: Create `Cronjob.yml`

Create the `Cronjob.yml` file to schedule the Docker container running the Spring application.

```yaml name=Cronjob.yml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: spring-cron-job
  namespace: default
spec:
  schedule: "*/5 * * * *"  # Runs every 5 minutes
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: spring-application
            image: srigopinath/demo:latest
            imagePullPolicy: Always
            resources:
              limits:
                memory: "512Mi"
                cpu: "500m"
              requests:
                memory: "256Mi"
                cpu: "250m"
          restartPolicy: OnFailure
```

### Step 4: Apply the CronJob

Apply the `CronJob` configuration to your Kubernetes cluster:

```sh
kubectl apply -f Cronjob.yml
```

### Step 5: Verify the CronJob

Check if the `CronJob` has been created successfully and is running as expected:

```sh
kubectl get cronjobs -n default
kubectl get jobs -n default
kubectl get pods -n default
```

### Step 6: Check Logs

If needed, check the logs of the pods created by the jobs to ensure that the Ansible playbook is being triggered correctly:

```sh
kubectl logs <pod-name> -n default
```

Replace `<pod-name>` with the actual name of the pod.

By following these steps, you can set up a Spring application with scheduled tasks and configure a Kubernetes CronJob to run the Docker container periodically.
