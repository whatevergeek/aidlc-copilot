# Local Development Setup

Complete guide to run the Event Planning Application locally from VS Code.

## Prerequisites

- **Java 21+** (you have Java 25, which works)
- **Node.js 18+** (you have Node.js 24, which works)
- **MongoDB** (local installation)
- **VS Code** with extensions:
  - Extension Pack for Java
  - ES7+ React/Redux/React-Native snippets
  - MongoDB for VS Code (optional)

## Quick Start (3 Steps)

### 1. Start MongoDB
```bash
# Windows
mongod --dbpath C:\data\db

# macOS/Linux
mongod --dbpath /usr/local/var/mongodb
```

### 2. Start Backend (Terminal 1)
```bash
cd backend
mvn spring-boot:run
```
✅ Backend runs on: `http://localhost:8080`

### 3. Start Frontend (Terminal 2)
```bash
cd frontend
npm install --legacy-peer-deps
npm start
```
✅ Frontend runs on: `http://localhost:3000`

## VS Code Setup

### 1. Open Project
```bash
code event-planning-app
```

### 2. VS Code Tasks (Ctrl+Shift+P → "Tasks: Run Task")

Create `.vscode/tasks.json`:
```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "Start Backend",
            "type": "shell",
            "command": "mvn",
            "args": ["spring-boot:run"],
            "options": {
                "cwd": "${workspaceFolder}/backend"
            },
            "group": "build",
            "presentation": {
                "echo": true,
                "reveal": "always",
                "panel": "new"
            }
        },
        {
            "label": "Start Frontend",
            "type": "shell",
            "command": "npm",
            "args": ["start"],
            "options": {
                "cwd": "${workspaceFolder}/frontend"
            },
            "group": "build",
            "presentation": {
                "echo": true,
                "reveal": "always",
                "panel": "new"
            }
        },
        {
            "label": "Install Frontend Dependencies",
            "type": "shell",
            "command": "npm",
            "args": ["install"],
            "options": {
                "cwd": "${workspaceFolder}/frontend"
            },
            "group": "build"
        }
    ]
}
```

### 3. VS Code Launch Configuration

Create `.vscode/launch.json`:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Debug Backend",
            "request": "launch",
            "mainClass": "com.eventplanning.EventPlanningApplication",
            "projectName": "event-planning-backend",
            "cwd": "${workspaceFolder}/backend"
        }
    ]
}
```

## MongoDB Setup

### Windows Installation
1. Download MongoDB Community Server
2. Install with default settings
3. Start MongoDB:
```bash
# Create data directory
mkdir C:\data\db

# Start MongoDB
mongod --dbpath C:\data\db
```

### macOS Installation
```bash
# Install via Homebrew
brew tap mongodb/brew
brew install mongodb-community

# Start MongoDB
brew services start mongodb/brew/mongodb-community
```

### Linux Installation
```bash
# Ubuntu/Debian
sudo apt-get install mongodb

# Start MongoDB
sudo systemctl start mongod
```

### Create Application User (Optional)

For better security, create dedicated credentials:

```javascript
// Connect to MongoDB
mongosh

// Switch to eventplanning database
use eventplanning

// Create user with read/write access
db.createUser({
  user: "eventapp",
  pwd: "eventapp123",
  roles: [
    { role: "readWrite", db: "eventplanning" }
  ]
})
```

Then update `backend/src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://eventapp:eventapp123@localhost:27017/eventplanning
```

And restart MongoDB with authentication:
```bash
mongod --auth --dbpath C:\data\db
```

**Note**: For local development, you can skip user creation and use the default connection without authentication.

## Development Workflow

### Option 1: VS Code Tasks
1. **Ctrl+Shift+P** → "Tasks: Run Task"
2. Select "Start Backend" (opens new terminal)
3. **Ctrl+Shift+P** → "Tasks: Run Task" 
4. Select "Start Frontend" (opens new terminal)

### Option 2: Integrated Terminals
1. **Ctrl+Shift+`** (open terminal)
2. **Ctrl+Shift+5** (split terminal)
3. Terminal 1: `cd backend && mvn spring-boot:run`
4. Terminal 2: `cd frontend && npm start`

### Option 3: Debug Mode
1. **F5** to debug backend (uses launch.json)
2. **Ctrl+Shift+`** for frontend terminal: `cd frontend && npm start`

## Verification Steps

### 1. Check Services
- MongoDB: `http://localhost:27017` (connection test)
- Backend: `http://localhost:8080/api/health` (should return 200)
- Frontend: `http://localhost:3000` (React app loads)

### 2. Test API
```bash
# Test backend health
curl http://localhost:8080/api/health

# Test user registration
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com","password":"password123"}'
```

### 3. Test Frontend
1. Open `http://localhost:3000`
2. Register new user
3. Create test event
4. Verify all features work

## Troubleshooting

### MongoDB Issues
```bash
# Check if MongoDB is running
ps aux | grep mongod

# Check MongoDB logs
tail -f /usr/local/var/log/mongodb/mongo.log
```

### Backend Issues
```bash
# Check Java version
java -version

# Clean and rebuild
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Issues
```bash
# If npm install is slow or hangs
npm cache clean --force
npm install --legacy-peer-deps

# For progress visibility
npm install --legacy-peer-deps --verbose

# Clear npm cache
npm cache clean --force

# Reinstall dependencies
cd frontend
rm -rf node_modules package-lock.json
npm install --legacy-peer-deps
npm start
```

### Port Conflicts
```bash
# Check what's using port 8080
netstat -tulpn | grep 8080

# Check what's using port 3000
netstat -tulpn | grep 3000
```

## VS Code Extensions (Recommended)

### Java Development
- Extension Pack for Java
- Spring Boot Extension Pack
- Maven for Java

### React Development
- ES7+ React/Redux/React-Native snippets
- Bracket Pair Colorizer
- Auto Rename Tag
- TypeScript Importer

### General Development
- GitLens
- MongoDB for VS Code
- REST Client (for API testing)

## Environment Variables

Create `.env` files if needed:

### Backend (`backend/.env`)
```properties
MONGODB_URI=mongodb://localhost:27017/eventplanning
# Or with authentication:
# MONGODB_URI=mongodb://eventapp:eventapp123@localhost:27017/eventplanning
JWT_SECRET=your-secret-key-here
SERVER_PORT=8080
```

### Frontend (`frontend/.env`)
```properties
REACT_APP_API_URL=http://localhost:8080/api
PORT=3000
```

## Hot Reload Setup

Both services support hot reload:
- **Backend**: Spring Boot DevTools (included in pom.xml)
- **Frontend**: React development server (automatic)

Changes are reflected immediately without restart.

## Database Management

### MongoDB Compass (GUI)
1. Download MongoDB Compass
2. Connect to `mongodb://localhost:27017`
3. View `eventplanning` database

### VS Code MongoDB Extension
1. Install "MongoDB for VS Code"
2. Connect to `mongodb://localhost:27017`
3. Browse collections directly in VS Code

## Ready to Code!

Once everything is running:
1. ✅ MongoDB on port 27017
2. ✅ Backend on port 8080
3. ✅ Frontend on port 3000
4. ✅ VS Code with proper extensions

You can now develop with full hot reload and debugging support!