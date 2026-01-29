# Vite Migration Testing Procedures

## Overview
This document outlines the testing procedures to validate the successful migration from Create React App to Vite while ensuring identical functionality.

## Testing Environment Setup

### Prerequisites
- MongoDB running on localhost:27017
- Backend service running on localhost:8080
- Node.js 18+ and yarn installed
- All dependencies installed with `yarn install`

### Test Environment Commands
```bash
# Start backend
cd backend
mvn spring-boot:run

# Start frontend (new Vite setup)
cd frontend
yarn dev

# Run tests
yarn test

# Build and preview
yarn build
yarn preview
```

## Unit Testing with Vitest

### Running Tests
```bash
# Run all tests
yarn test

# Run tests in watch mode
yarn test --watch

# Run tests with coverage
yarn test --coverage

# Run specific test file
yarn test LoginForm.test.tsx
```

### Test Migration Validation
- [ ] All existing tests pass without modification
- [ ] Test runner starts faster than with CRA
- [ ] Hot reload works in test watch mode
- [ ] Coverage reports generate correctly

## Integration Testing

### API Integration Tests
1. **Authentication Flow**
   ```bash
   # Test user registration
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
   
   # Test user login
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"testuser","password":"password123"}'
   ```

2. **Event Management**
   - Create event through UI
   - Verify event appears in database
   - Test event editing and deletion

3. **RSVP System**
   - Submit RSVP responses
   - Verify guest tracking works
   - Test RSVP summary display

## Performance Testing

### Development Server Performance
```bash
# Measure startup time
time yarn dev

# Expected: <5 seconds (vs 30-60 seconds with CRA)
```

### Build Performance
```bash
# Measure build time
time yarn build

# Check bundle size
ls -la dist/assets/
```

### Hot Reload Testing
1. Start development server: `yarn dev`
2. Make changes to React components
3. Verify changes appear in <1 second
4. Test TypeScript error handling

## Functional Testing Checklist

### Core Application Features
- [ ] **User Registration**: New users can register successfully
- [ ] **User Login**: Existing users can log in
- [ ] **Event Creation**: Users can create new events
- [ ] **Event Viewing**: Events display correctly in lists and detail views
- [ ] **RSVP Submission**: Guests can submit RSVP responses
- [ ] **Task Management**: Tasks can be created, assigned, and updated
- [ ] **Budget Tracking**: Budget entries can be added and calculated
- [ ] **Dashboard**: Timeline and metrics display correctly

### Navigation and Routing
- [ ] **Page Navigation**: All routes work correctly
- [ ] **Browser Back/Forward**: Navigation history works
- [ ] **Direct URL Access**: Deep links work properly
- [ ] **404 Handling**: Invalid routes show appropriate errors

### Data Persistence
- [ ] **Form Submissions**: All forms save data correctly
- [ ] **Data Refresh**: Page refreshes maintain application state
- [ ] **API Calls**: All backend communication works through proxy

## Browser Compatibility Testing

### Supported Browsers
- [ ] **Chrome**: Latest version
- [ ] **Firefox**: Latest version  
- [ ] **Safari**: Latest version
- [ ] **Edge**: Latest version

### Mobile Testing
- [ ] **Responsive Design**: UI adapts to mobile screens
- [ ] **Touch Interactions**: Mobile gestures work correctly
- [ ] **Performance**: Acceptable performance on mobile devices

## Error Handling Testing

### Development Errors
- [ ] **TypeScript Errors**: Clear error messages in terminal
- [ ] **Runtime Errors**: Error overlay displays correctly
- [ ] **Console Errors**: No unexpected console errors

### Production Errors
- [ ] **Build Errors**: Build fails gracefully with clear messages
- [ ] **404 Errors**: Proper error pages display
- [ ] **API Errors**: Network errors handled gracefully

## Regression Testing

### Compare with Original CRA Version
1. **Functionality Comparison**
   - Test identical user workflows
   - Verify same API responses
   - Check identical UI behavior

2. **Performance Comparison**
   - Measure startup time improvement
   - Compare hot reload speed
   - Validate build output size

## Automated Testing Scripts

### Test Automation Commands
```bash
# Full test suite
yarn test:full

# Performance benchmarks
yarn test:performance

# Build validation
yarn test:build

# Integration tests
yarn test:integration
```

## Test Reporting

### Test Results Documentation
- Record test execution times
- Document any failures or issues
- Compare performance metrics with CRA baseline
- Note any behavioral differences

### Success Criteria
- [ ] All unit tests pass
- [ ] All integration tests pass
- [ ] Performance improvements verified
- [ ] No functional regressions detected
- [ ] Documentation reflects actual behavior

## Troubleshooting Common Issues

### Test Failures
- Check Vitest configuration
- Verify test environment setup
- Update test imports if needed

### Performance Issues
- Check for console errors
- Verify proxy configuration
- Test with production build

### Build Issues
- Clear dist/ directory
- Check TypeScript compilation
- Verify all imports are correct