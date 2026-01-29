# Performance Test Instructions - Vite Migration

## Purpose
Validate that the Vite migration delivers the expected performance improvements over Create React App.

## Performance Requirements
- **Development Server Startup**: <5 seconds (vs 30-60 seconds with CRA)
- **Hot Module Replacement**: <1 second for changes to appear
- **Production Build Time**: Faster than CRA build
- **Bundle Size**: Same or smaller than CRA output
- **Runtime Performance**: No degradation from CRA version

## Setup Performance Test Environment

### 1. Prepare Test Environment
```bash
# Ensure clean state
cd frontend
rm -rf node_modules dist/
yarn install
```

### 2. Baseline Measurements (if CRA version available)
```bash
# If you have CRA version for comparison
# Measure CRA performance first, then Vite
```

## Run Performance Tests

### 1. Development Server Performance

#### Startup Time Test
```bash
# Measure development server startup
time yarn dev
# Expected: <5 seconds total
```

#### Hot Reload Performance Test
```bash
# Start dev server
yarn dev

# In another terminal, measure hot reload
echo "console.log('test change');" >> src/App.tsx
# Observe time for change to appear in browser
# Expected: <1 second
```

### 2. Build Performance Tests

#### Production Build Time
```bash
# Measure build time
time yarn build
# Compare with previous CRA build times
```

#### Bundle Analysis
```bash
# Build and analyze bundle size
yarn build

# Check output size
ls -la dist/assets/
du -sh dist/

# Compare with CRA build/ output size
```

### 3. Runtime Performance Tests

#### Page Load Performance
```bash
# Build production version
yarn build
yarn preview

# Test page load times
# Use browser DevTools Network tab
# Measure:
# - Initial page load
# - Time to interactive
# - First contentful paint
```

#### Memory Usage
```bash
# Monitor memory usage during development
# Use browser DevTools Memory tab
# Compare with CRA version if available
```

## Performance Benchmarks

### Development Experience
- [ ] **Cold Start**: Dev server starts in <5 seconds
- [ ] **Hot Reload**: Changes appear in <1 second
- [ ] **TypeScript Compilation**: Faster than CRA
- [ ] **Error Recovery**: Fast error resolution

### Build Performance
- [ ] **Build Speed**: Faster than CRA `npm run build`
- [ ] **Bundle Size**: Same or smaller than CRA
- [ ] **Tree Shaking**: Unused code removed effectively
- [ ] **Code Splitting**: Automatic code splitting works

### Runtime Performance
- [ ] **Initial Load**: No slower than CRA version
- [ ] **Navigation**: Client-side routing performance maintained
- [ ] **API Calls**: No performance degradation
- [ ] **Memory Usage**: No memory leaks or excessive usage

## Analyze Performance Results

### Development Performance
```bash
# Measure and record:
echo "Development Server Startup: $(time yarn dev 2>&1 | grep real)"
echo "Hot Reload Time: <1 second (manual observation)"
```

### Build Performance
```bash
# Measure and record:
echo "Production Build Time: $(time yarn build 2>&1 | grep real)"
echo "Bundle Size: $(du -sh dist/)"
```

### Comparison with CRA (if available)
| Metric | CRA | Vite | Improvement |
|--------|-----|------|-------------|
| Dev Server Startup | 30-60s | <5s | 6-12x faster |
| Hot Reload | 2-5s | <1s | 2-5x faster |
| Build Time | Xs | Ys | Z% faster |
| Bundle Size | X MB | Y MB | Z% smaller |

## Performance Optimization

### If Performance Doesn't Meet Requirements

#### Slow Development Server
1. Check for large dependencies
2. Optimize Vite configuration
3. Use Vite's dependency pre-bundling
4. Check for file system issues

#### Slow Hot Reload
1. Verify React Fast Refresh is enabled
2. Check for circular dependencies
3. Optimize component structure
4. Review Vite HMR configuration

#### Large Bundle Size
1. Analyze bundle with `yarn build --analyze`
2. Implement code splitting
3. Remove unused dependencies
4. Optimize imports (tree shaking)

#### Slow Build Times
1. Check TypeScript configuration
2. Optimize Vite plugins
3. Use build caching
4. Parallelize build processes

## Performance Monitoring

### Continuous Performance Tracking
```bash
# Create performance benchmark script
#!/bin/bash
echo "=== Vite Performance Benchmark ==="
echo "Date: $(date)"
echo "Dev Server Startup: $(time yarn dev 2>&1 | grep real)"
echo "Build Time: $(time yarn build 2>&1 | grep real)"
echo "Bundle Size: $(du -sh dist/)"
```

### Performance Regression Detection
- Monitor build times in CI/CD
- Track bundle size changes
- Alert on performance degradation
- Regular performance audits

## Success Criteria
- [ ] **Development**: 6x+ faster startup than CRA
- [ ] **Hot Reload**: Sub-second change reflection
- [ ] **Build**: Faster or equal build times
- [ ] **Bundle**: Same or smaller output size
- [ ] **Runtime**: No performance regressions
- [ ] **Memory**: No memory leaks or excessive usage

## Performance Test Results Template
```
=== Vite Migration Performance Results ===
Date: [Date]
Environment: [OS, Node version, etc.]

Development Performance:
- Server Startup: [X] seconds (Target: <5s)
- Hot Reload: [X] seconds (Target: <1s)
- Status: [PASS/FAIL]

Build Performance:
- Build Time: [X] seconds
- Bundle Size: [X] MB
- Status: [PASS/FAIL]

Runtime Performance:
- Page Load: [X] seconds
- Memory Usage: [X] MB
- Status: [PASS/FAIL]

Overall Status: [PASS/FAIL]
```