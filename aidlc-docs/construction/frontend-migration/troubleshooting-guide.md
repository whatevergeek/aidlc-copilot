# Vite Migration Troubleshooting Guide

## Common Issues and Solutions

### Development Server Issues

#### Issue: `yarn dev` fails to start
**Symptoms**: Error messages when running `yarn dev`
**Solutions**:
```bash
# Clear node_modules and reinstall
rm -rf node_modules yarn.lock
yarn install

# Check for port conflicts
netstat -tulpn | grep 3000

# Try different port
yarn dev --port 3001
```

#### Issue: Slow development server startup
**Symptoms**: Takes longer than 5 seconds to start
**Solutions**:
```bash
# Clear Vite cache
rm -rf node_modules/.vite

# Check for large node_modules
du -sh node_modules/

# Restart with clean cache
yarn dev --force
```

### Hot Reload Issues

#### Issue: Changes not reflecting in browser
**Symptoms**: Code changes don't appear automatically
**Solutions**:
```bash
# Check Vite HMR configuration
# Ensure vite.config.ts has correct settings

# Clear browser cache
# Hard refresh: Ctrl+Shift+R

# Restart dev server
# Ctrl+C then yarn dev
```

#### Issue: Full page reload instead of hot reload
**Symptoms**: Page refreshes completely on changes
**Solutions**:
- Check React Fast Refresh is enabled
- Ensure components are properly exported
- Avoid anonymous default exports

### TypeScript Issues

#### Issue: TypeScript compilation errors
**Symptoms**: Red underlines in IDE, build failures
**Solutions**:
```bash
# Check TypeScript configuration
npx tsc --noEmit

# Update tsconfig.json for Vite
# Ensure moduleResolution: "bundler"

# Clear TypeScript cache
rm -rf node_modules/.cache
```

#### Issue: Import path errors
**Symptoms**: Cannot resolve module errors
**Solutions**:
```typescript
// Use explicit file extensions for imports
import Component from './Component.tsx'

// Check vite.config.ts resolve aliases
export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  }
})
```

### API Proxy Issues

#### Issue: API calls failing (404, CORS errors)
**Symptoms**: Backend API requests not working
**Solutions**:
```typescript
// Check vite.config.ts proxy configuration
export default defineConfig({
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
    },
  },
})
```

#### Issue: Proxy not forwarding requests
**Symptoms**: API calls go to frontend port instead of backend
**Solutions**:
- Ensure backend is running on localhost:8080
- Check proxy path matches API endpoints
- Verify changeOrigin: true in proxy config

### Build Issues

#### Issue: `yarn build` fails
**Symptoms**: Build process errors or warnings
**Solutions**:
```bash
# Clear build cache
rm -rf dist/

# Check for TypeScript errors
yarn tsc --noEmit

# Build with verbose output
yarn build --mode development
```

#### Issue: Build output missing files
**Symptoms**: dist/ directory incomplete
**Solutions**:
- Check index.html references correct entry point
- Ensure all assets are properly imported
- Verify public assets are in correct location

### Testing Issues

#### Issue: Vitest tests failing
**Symptoms**: Tests that worked with CRA now fail
**Solutions**:
```bash
# Check vitest.config.ts setup
# Ensure jsdom environment configured

# Update test imports
import { vi } from 'vitest'
// instead of jest

# Clear test cache
yarn test --run --reporter=verbose
```

#### Issue: Test environment setup errors
**Symptoms**: DOM not available in tests
**Solutions**:
```typescript
// Ensure vitest.config.ts has:
export default defineConfig({
  test: {
    environment: 'jsdom',
    setupFiles: ['./src/setupTests.ts'],
  },
})
```

### Performance Issues

#### Issue: Slow build times
**Symptoms**: `yarn build` takes too long
**Solutions**:
```bash
# Analyze bundle size
yarn build --analyze

# Check for large dependencies
npx vite-bundle-analyzer dist/

# Optimize imports (tree shaking)
import { specific } from 'library'
```

#### Issue: Large bundle size
**Symptoms**: dist/ files are too large
**Solutions**:
- Enable code splitting
- Use dynamic imports for routes
- Check for duplicate dependencies

### Environment Variable Issues

#### Issue: Environment variables not working
**Symptoms**: process.env.REACT_APP_* undefined
**Solutions**:
```typescript
// Vite uses import.meta.env instead
const apiUrl = import.meta.env.VITE_API_URL

// Update .env file format
VITE_API_URL=http://localhost:8080/api
```

### Import Issues

#### Issue: Absolute imports not working
**Symptoms**: Cannot resolve '@/components' paths
**Solutions**:
```typescript
// Update vite.config.ts
import path from 'path'

export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  }
})

// Update tsconfig.json
{
  "compilerOptions": {
    "baseUrl": ".",
    "paths": {
      "@/*": ["src/*"]
    }
  }
}
```

### CSS and Asset Issues

#### Issue: CSS imports not working
**Symptoms**: Styles not loading
**Solutions**:
```typescript
// Import CSS files directly
import './Component.css'

// For CSS modules
import styles from './Component.module.css'
```

#### Issue: Static assets not loading
**Symptoms**: Images, fonts not found
**Solutions**:
```typescript
// Use explicit imports for assets
import logo from './assets/logo.png'

// Or use public directory
// Place in public/ and reference as /logo.png
```

## Debugging Tools

### Vite Debug Mode
```bash
# Run with debug output
DEBUG=vite:* yarn dev

# Check Vite config
yarn vite --debug config
```

### Browser DevTools
- Check Network tab for failed requests
- Look for console errors
- Verify source maps are working

### VS Code Debugging
```json
// .vscode/launch.json
{
  "type": "node",
  "request": "launch",
  "name": "Debug Vite",
  "program": "${workspaceFolder}/node_modules/vite/bin/vite.js",
  "args": ["--debug"]
}
```

## Recovery Procedures

### Complete Reset
```bash
# Nuclear option - full reset
rm -rf node_modules yarn.lock dist/
yarn install
yarn dev
```

### Rollback to CRA (Emergency)
```bash
# Restore original package.json
git checkout HEAD~1 -- package.json

# Remove Vite files
rm vite.config.ts vitest.config.ts tsconfig.node.json index.html

# Reinstall CRA dependencies
npm install
npm start
```

## Getting Help

### Vite Documentation
- Official docs: https://vitejs.dev/
- Migration guide: https://vitejs.dev/guide/migration.html

### Community Resources
- Vite Discord: https://chat.vitejs.dev/
- GitHub issues: https://github.com/vitejs/vite/issues

### Internal Support
- Check validation checklist first
- Review testing procedures
- Consult development team