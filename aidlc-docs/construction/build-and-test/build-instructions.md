# Build Instructions - Vite Migration

## Prerequisites
- **Build Tool**: Yarn (package manager) and Vite (build tool)
- **Dependencies**: Node.js 18+, yarn installed globally
- **Environment Variables**: None required for basic build
- **System Requirements**: Windows/macOS/Linux, 4GB RAM, 1GB disk space

## Build Steps

### 1. Install Dependencies
```bash
cd frontend
yarn install
```

### 2. Configure Environment
```bash
# Ensure backend is running for proxy testing
cd backend
mvn spring-boot:run &

# Return to frontend
cd ../frontend
```

### 3. Build All Components

#### Development Build
```bash
# Start development server (should start in <5 seconds)
yarn dev
```

#### Production Build
```bash
# Build for production
yarn build
```

### 4. Verify Build Success

#### Development Server
- **Expected Output**: "Local: http://localhost:3000" in <5 seconds
- **Build Artifacts**: Vite dev server running with hot reload
- **Common Warnings**: None expected

#### Production Build
- **Expected Output**: "dist folder created successfully"
- **Build Artifacts**: 
  - `dist/index.html` - Main HTML file
  - `dist/assets/` - Optimized JS, CSS, and asset files
- **Common Warnings**: None expected

## Troubleshooting

### Build Fails with Dependency Errors
- **Cause**: Missing or corrupted node_modules
- **Solution**: 
  ```bash
  rm -rf node_modules yarn.lock
  yarn install
  ```

### Build Fails with TypeScript Errors
- **Cause**: TypeScript compilation issues
- **Solution**: 
  ```bash
  # Check TypeScript errors
  yarn tsc --noEmit
  # Fix reported errors and rebuild
  ```

### Development Server Won't Start
- **Cause**: Port 3000 already in use
- **Solution**: 
  ```bash
  # Use different port
  yarn dev --port 3001
  # Or kill process using port 3000
  ```

### Proxy Errors (API calls fail)
- **Cause**: Backend not running or proxy misconfigured
- **Solution**: 
  ```bash
  # Ensure backend is running on localhost:8080
  cd backend && mvn spring-boot:run
  # Check vite.config.ts proxy settings
  ```