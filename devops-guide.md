# DevOps Guide - Event Planning Application

## Overview

This guide provides comprehensive information for deploying, running, and maintaining the Event Planning Application in various environments.

## System Architecture

```mermaid
graph TB
    subgraph "Production Environment"
        LB[Load Balancer]
        subgraph "Application Tier"
            FE1[Frontend Instance 1]
            FE2[Frontend Instance 2]
            BE1[Backend Instance 1]
            BE2[Backend Instance 2]
        end
        subgraph "Database Tier"
            DB1[(MongoDB Primary)]
            DB2[(MongoDB Secondary)]
        end
    end
    
    subgraph "External"
        USERS[Users]
        MONITOR[Monitoring]
    end
    
    USERS --> LB
    LB --> FE1
    LB --> FE2
    FE1 --> BE1
    FE2 --> BE2
    BE1 --> DB1
    BE2 --> DB1
    DB1 --> DB2
    MONITOR --> BE1
    MONITOR --> BE2
```

## Deployment Environments

### Local Development
- **Purpose**: Developer workstations
- **Components**: Single backend, single frontend, local MongoDB
- **Access**: localhost:3000 (frontend), localhost:8080 (backend)

### Staging
- **Purpose**: Pre-production testing
- **Components**: Containerized services, shared MongoDB
- **Access**: staging.eventplanning.com

### Production
- **Purpose**: Live application
- **Components**: Load-balanced, clustered MongoDB
- **Access**: eventplanning.com

## Container Configuration

### Backend Dockerfile

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/event-planning-backend-1.0.0.jar"]
```

### Frontend Dockerfile

```dockerfile
FROM node:18-alpine as build

WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production

COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### Docker Compose Configuration

```yaml
version: '3.8'

services:
  mongodb:
    image: mongo:6.0
    container_name: eventplanning-db
    restart: always
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: password
      MONGO_INITDB_DATABASE: eventplanning
    volumes:
      - mongodb_data:/data/db

  backend:
    build: ./backend
    container_name: eventplanning-backend
    restart: always
    ports:
      - "8080:8080"
    environment:
      SPRING_DATA_MONGODB_URI: mongodb://admin:password@mongodb:27017/eventplanning?authSource=admin
      JWT_SECRET: your-secret-key-here
    depends_on:
      - mongodb

  frontend:
    build: ./frontend
    container_name: eventplanning-frontend
    restart: always
    ports:
      - "80:80"
    depends_on:
      - backend

volumes:
  mongodb_data:
```

## Deployment Workflows

### 1. Local Development Deployment

```mermaid
flowchart TD
    A[Developer Commits] --> B[Local Testing]
    B --> C[Start MongoDB]
    C --> D[Run Backend]
    D --> E[Run Frontend]
    E --> F[Verify Functionality]
    F --> G[Ready for Staging]
```

### 2. Staging Deployment

```mermaid
flowchart TD
    A[Code Merged to Main] --> B[CI/CD Pipeline Triggered]
    B --> C[Build Docker Images]
    C --> D[Run Automated Tests]
    D --> E[Deploy to Staging]
    E --> F[Health Checks]
    F --> G[Integration Tests]
    G --> H[Staging Ready]
    H --> I[Manual Testing]
    I --> J[Approve for Production]
```

### 3. Production Deployment

```mermaid
flowchart TD
    A[Staging Approved] --> B[Create Release Tag]
    B --> C[Build Production Images]
    C --> D[Database Migration]
    D --> E[Blue-Green Deployment]
    E --> F[Health Checks]
    F --> G[Switch Traffic]
    G --> H[Monitor Metrics]
    H --> I[Rollback if Issues]
    I --> J[Production Live]
```

## CI/CD Pipeline Configuration

### GitHub Actions Workflow

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    services:
      mongodb:
        image: mongo:6.0
        ports:
          - 27017:27017
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 21
      uses: actions/setup-java@v3
      with:
        java-version: '21'
        distribution: 'temurin'
    
    - name: Set up Node.js
      uses: actions/setup-node@v3
      with:
        node-version: '18'
    
    - name: Test Backend
      run: |
        cd backend
        mvn test
    
    - name: Test Frontend
      run: |
        cd frontend
        npm ci
        npm test
    
    - name: Build Docker Images
      run: |
        docker build -t eventplanning-backend ./backend
        docker build -t eventplanning-frontend ./frontend
    
    - name: Deploy to Staging
      if: github.ref == 'refs/heads/main'
      run: |
        # Deploy to staging environment
        echo "Deploying to staging..."
```

## Environment Configuration

### Environment Variables

#### Backend Configuration
```bash
# Database
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/eventplanning
SPRING_DATA_MONGODB_DATABASE=eventplanning

# Security
JWT_SECRET=your-256-bit-secret-key-here
JWT_EXPIRATION=86400000

# Server
SERVER_PORT=8080
CORS_ALLOWED_ORIGINS=http://localhost:3000,https://yourdomain.com

# Logging
LOGGING_LEVEL_COM_EVENTPLANNING=INFO
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=DEBUG
```

#### Frontend Configuration
```bash
# API Configuration
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_ENVIRONMENT=production

# Feature Flags
REACT_APP_ENABLE_ANALYTICS=true
REACT_APP_ENABLE_DEBUG=false
```

### Configuration Management

```mermaid
graph TD
    A[Environment Variables] --> B[Development]
    A --> C[Staging]
    A --> D[Production]
    
    B --> E[Local .env files]
    C --> F[Docker Compose env]
    D --> G[Kubernetes Secrets]
    
    H[Configuration Management] --> I[Vault/AWS Secrets]
    I --> G
    I --> F
```

## Database Management

### MongoDB Setup

#### Development
```bash
# Start MongoDB locally
mongod --dbpath /usr/local/var/mongodb

# Create database and user
mongo
use eventplanning
db.createUser({
  user: "eventplanning",
  pwd: "password",
  roles: ["readWrite"]
})
```

#### Production
```bash
# MongoDB Replica Set Configuration
rs.initiate({
  _id: "rs0",
  members: [
    { _id: 0, host: "mongo1:27017" },
    { _id: 1, host: "mongo2:27017" },
    { _id: 2, host: "mongo3:27017" }
  ]
})
```

### Database Migration Strategy

```mermaid
sequenceDiagram
    participant Dev as Developer
    participant CI as CI/CD Pipeline
    participant Staging as Staging DB
    participant Prod as Production DB
    
    Dev->>CI: Commit Migration Script
    CI->>Staging: Apply Migration
    CI->>CI: Run Integration Tests
    CI->>Prod: Apply Migration (if tests pass)
    Prod->>CI: Confirm Migration Success
```

## Monitoring and Logging

### Application Monitoring

```mermaid
graph TD
    A[Application Metrics] --> B[Spring Boot Actuator]
    A --> C[Custom Metrics]
    
    D[Infrastructure Metrics] --> E[CPU/Memory]
    D --> F[Network/Disk]
    
    G[Business Metrics] --> H[User Registrations]
    G --> I[Event Creations]
    G --> J[RSVP Responses]
    
    B --> K[Prometheus]
    C --> K
    E --> K
    F --> K
    H --> K
    I --> K
    J --> K
    
    K --> L[Grafana Dashboard]
    K --> M[Alerting]
```

### Logging Configuration

#### Backend Logging (logback-spring.xml)
```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/eventplanning.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/eventplanning.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

## Security Configuration

### SSL/TLS Setup

```nginx
server {
    listen 443 ssl http2;
    server_name eventplanning.com;
    
    ssl_certificate /etc/ssl/certs/eventplanning.crt;
    ssl_certificate_key /etc/ssl/private/eventplanning.key;
    
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers ECDHE-RSA-AES256-GCM-SHA512:DHE-RSA-AES256-GCM-SHA512;
    
    location / {
        proxy_pass http://frontend:80;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
    
    location /api {
        proxy_pass http://backend:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

### Firewall Configuration

```bash
# Allow HTTP/HTTPS
ufw allow 80/tcp
ufw allow 443/tcp

# Allow SSH (restrict to specific IPs)
ufw allow from 192.168.1.0/24 to any port 22

# Allow MongoDB (internal only)
ufw allow from 10.0.0.0/8 to any port 27017

# Enable firewall
ufw enable
```

## Backup and Recovery

### Database Backup Strategy

```mermaid
flowchart TD
    A[Daily Backup] --> B[mongodump]
    B --> C[Compress Archive]
    C --> D[Upload to S3]
    
    E[Weekly Backup] --> F[Full Database Export]
    F --> G[Long-term Storage]
    
    H[Point-in-time Recovery] --> I[MongoDB Oplog]
    I --> J[Restore to Specific Time]
```

### Backup Scripts

```bash
#!/bin/bash
# Daily backup script

DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/backups/mongodb"
DB_NAME="eventplanning"

# Create backup
mongodump --host localhost:27017 --db $DB_NAME --out $BACKUP_DIR/$DATE

# Compress
tar -czf $BACKUP_DIR/eventplanning_$DATE.tar.gz -C $BACKUP_DIR $DATE

# Upload to cloud storage
aws s3 cp $BACKUP_DIR/eventplanning_$DATE.tar.gz s3://eventplanning-backups/

# Cleanup local files older than 7 days
find $BACKUP_DIR -name "*.tar.gz" -mtime +7 -delete
```

## Scaling Strategies

### Horizontal Scaling

```mermaid
graph TD
    A[Load Balancer] --> B[Frontend Pod 1]
    A --> C[Frontend Pod 2]
    A --> D[Frontend Pod N]
    
    B --> E[Backend Pod 1]
    C --> F[Backend Pod 2]
    D --> G[Backend Pod N]
    
    E --> H[(MongoDB Cluster)]
    F --> H
    G --> H
```

### Auto-scaling Configuration

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: eventplanning-backend-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: eventplanning-backend
  minReplicas: 2
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
  - type: Resource
    resource:
      name: memory
      target:
        type: Utilization
        averageUtilization: 80
```

## Troubleshooting

### Common Issues and Solutions

#### Backend Issues
1. **Database Connection Failed**
   - Check MongoDB service status
   - Verify connection string
   - Check network connectivity

2. **JWT Token Errors**
   - Verify JWT secret configuration
   - Check token expiration settings
   - Validate token format

#### Frontend Issues
1. **API Connection Failed**
   - Check backend service status
   - Verify API base URL configuration
   - Check CORS settings

2. **Build Failures**
   - Clear npm cache: `npm cache clean --force`
   - Delete node_modules and reinstall
   - Check TypeScript compilation errors

### Health Check Endpoints

```bash
# Backend health check
curl http://localhost:8080/actuator/health

# Swagger UI (API documentation and testing)
curl http://localhost:8080/swagger-ui.html

# OpenAPI specification
curl http://localhost:8080/api-docs

# Frontend health check
curl http://localhost:3000/health

# Vite build output check
ls -la frontend/dist/

# Database health check
mongo --eval "db.adminCommand('ping')"
```

## Performance Optimization

### Database Optimization

```javascript
// Create indexes for frequently queried fields
db.events.createIndex({ "organizerId": 1 })
db.events.createIndex({ "dateTime": 1 })
db.rsvps.createIndex({ "eventId": 1, "guestEmail": 1 })
db.tasks.createIndex({ "eventId": 1, "status": 1 })
```

### Application Optimization

- **Connection Pooling**: Configure MongoDB connection pool size
- **Caching**: Implement Redis for session storage
- **CDN**: Use CloudFront for static assets
- **Compression**: Enable gzip compression

This guide provides comprehensive information for deploying and maintaining the Event Planning Application across different environments.