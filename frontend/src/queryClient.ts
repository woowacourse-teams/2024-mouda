import { QueryCache, QueryClient } from '@tanstack/react-query';
import { ApiError } from '@_utils/customError/ApiError';
import { removeToken } from '@_utils/tokenManager';
import * as Sentry from '@sentry/react';

const createQueryClient = () => {
  return new QueryClient({
    defaultOptions: {
      queries: {
        networkMode: 'always',
        retry: (failureCount, error) => {
          if (error instanceof ApiError && error.status === 401) {
            return false;
          }
          return failureCount < 3;
        },
      },
      mutations: {
        onError: (error) => handleApiError(error),
        networkMode: 'always',
      },
    },
    queryCache: new QueryCache({
      onError: (error) => handleApiError(error),
    }),
  });
};

const handleApiError = (error: Error) => {
  console.log(error);
  Sentry.captureException(error);
  if (error instanceof ApiError && error.status === 401) {
    removeToken();
    window.location.href = '/login';
  } else {
    alert(error instanceof Error ? error.message : 'An error occurred');
  }
};

export default createQueryClient;
