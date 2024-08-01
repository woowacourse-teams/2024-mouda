/**
 * For a detailed explanation regarding each configuration property, visit:
 * https://jestjs.io/docs/configuration
 */

import type { Config } from 'jest';

const config: Config = {
  preset: 'ts-jest',
  clearMocks: true,
  collectCoverage: true,
  coverageDirectory: 'coverage',

  testEnvironment: 'jest-environment-jsdom',
  moduleNameMapper: {
    '^@_apis/(.*)$': '<rootDir>/src/apis/$1',
    '^@_constants/(.*)$': '<rootDir>/src/constants/$1',
    '^@_common/(.*)$': '<rootDir>/src/common/$1',
    '^@_components/(.*)$': '<rootDir>/src/components/$1',
    '^@_hooks/(.*)$': '<rootDir>/src/hooks/$1',
    '^@_layouts/(.*)$': '<rootDir>/src/layouts/$1',
    '^@_pages/(.*)$': '<rootDir>/src/pages/$1',
    '^@_types/(.*)$': '<rootDir>/src/types/$1',
    '^@_utils/(.*)$': '<rootDir>/src/utils/$1',
    '^@_routes/(.*)$': '<rootDir>/src/routes/$1',
  },
};

export default config;
