# Unit Test Execution - Vitest Migration

## Run Unit Tests

### 1. Execute All Unit Tests
```bash
cd frontend
yarn test
```

### 2. Run Tests in Watch Mode
```bash
# For development - tests rerun on file changes
yarn test --watch
```

### 3. Run Tests with Coverage
```bash
# Generate coverage report
yarn test --coverage
```

### 4. Review Test Results
- **Expected**: All existing tests pass (same as with CRA)
- **Test Coverage**: Maintain existing coverage percentage
- **Test Report Location**: Terminal output and `coverage/` directory

### 5. Vitest-Specific Features
```bash
# Run tests with UI (if configured)
yarn test --ui

# Run specific test file
yarn test LoginForm.test.tsx

# Run tests matching pattern
yarn test --grep "authentication"
```

## Test Migration Validation

### Verify Vitest Setup
- [ ] Tests run faster than with CRA (jest)
- [ ] All existing test files work without modification
- [ ] Test watch mode works with hot reload
- [ ] Coverage reports generate correctly

### Common Test Issues

#### Tests Fail with Import Errors
- **Cause**: ES modules vs CommonJS issues
- **Solution**: Check vitest.config.ts configuration

#### DOM Not Available in Tests
- **Cause**: jsdom environment not configured
- **Solution**: Verify vitest.config.ts has `environment: 'jsdom'`

#### Mock Functions Not Working
- **Cause**: Jest mocks vs Vitest mocks
- **Solution**: Update mocks to use `vi` from vitest:
  ```typescript
  import { vi } from 'vitest'
  const mockFn = vi.fn()
  ```

### Fix Failing Tests
If tests fail:
1. Review test output in terminal
2. Check for Vitest vs Jest API differences
3. Update test setup if needed
4. Rerun tests until all pass

## Expected Test Results
- **Status**: All tests should pass (no regressions)
- **Performance**: Tests should run faster than with CRA
- **Coverage**: Should maintain or improve existing coverage